package chartconstellation.app.Controllers;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.entities.*;
import chartconstellation.app.entities.response.*;
import chartconstellation.app.recommendations.AttributeRecommendations;
import chartconstellation.app.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/coordinates")
public class CoordinatesController {

    @Autowired
    Configuration configuration;

    @Autowired
    DbUtil dbUtil;

    @Autowired
    CoordinatesUtil coordinatesUtil;

    @Autowired
    ClusterUtil clusterUtil;

    @Autowired
    AttributeUtil attributeUtil;

    @Autowired
    AttributeRecommendations attributeRecommendations;

    @RequestMapping(value="/getCoordinates", method= RequestMethod.GET)
    @ResponseBody
    public OutputResponse coordinates(@RequestParam("descWeight") Double descWeight,
                                                       @RequestParam("attrWeight") Double attrWeight,
                                                       @RequestParam("chartEncodingWeight") Double chartEncodingWeight,
                                                       @RequestParam("colorMap") Object colorMap,
                                      @RequestParam("datasetId") String datasetId) {

        System.out.println("datasetId "+datasetId);

        List<FeatureVector> featurevectors = new ArrayList<>();
        List<Chart> chartObjs = new ArrayList<>();

        if(datasetId.equals("olympics")) {

            featurevectors = dbUtil.getFeaturesFromCollection(configuration.getMongoDatabase(),
                    configuration.getTotalFeatureCollection());

            chartObjs = dbUtil.getAttributesFromCollection(configuration.getMongoDatabase(), configuration.getOlympicchartcollection());
        } else {
            featurevectors = dbUtil.getFeaturesFromCollection(configuration.getDataset2mongoDatabase(),
                    configuration.getDataset2totalFeatureCollection());

            chartObjs = dbUtil.getAttributesFromCollection(configuration.getDataset2mongoDatabase(),
                    configuration.getCrimechartcollection());
        }

        HashMap<Integer, List<IdCoordinates>> coordinatesMap = coordinatesUtil.getCoordinates(datasetId, chartObjs, 3, featurevectors, descWeight, attrWeight, chartEncodingWeight, colorMap);

        List<Cluster> clusterList = clusterUtil.generateClusterInfo(coordinatesMap, chartObjs);

        HashMap<String, Integer> attributesMap = attributeUtil.getAttributesList(chartObjs);
        HashMap<String, Integer> attributesFullMap = new HashMap<>();
        if(datasetId.equals("olympics")) {
            attributesFullMap = attributeUtil.getFullAttributesMap(attributesMap, configuration.getDataset1Attributes());
        } else {
            attributesFullMap = attributeUtil.getFullAttributesMap(attributesMap, configuration.getDataset2Attributes());
        }

        HashMap<String, Set<String>> attributeCoOccurenceMap = attributeUtil.getAttributeCooccurenceMap(chartObjs);

        DataCoverageResponse dataCoverageResponse = new DataCoverageResponse();
        dataCoverageResponse.setAttributesMap(attributesFullMap);
        dataCoverageResponse.setAttributeOccurenceMap(attributeCoOccurenceMap);
        //dataCoverageResponse.setAttributeKeys(attributesMap.keySet());

        OutputResponse outputResponse = new OutputResponse();
        outputResponse.setCoordinatesList(coordinatesMap.values());
        outputResponse.setClusters(clusterList);
        outputResponse.setDataCoverage(dataCoverageResponse);

        HashMap<String, Set<String>> userExploringAttributes = new HashMap<>();

        if(datasetId.equals("olympics")) {
            userExploringAttributes = attributeRecommendations.getAttributeRecommendationsForAllUsers(outputResponse.getCoordinatesList(), configuration.getDataset1Attributes());
        } else {
            userExploringAttributes = attributeRecommendations.getAttributeRecommendationsForAllUsers(outputResponse.getCoordinatesList(), configuration.getDataset2Attributes());
        }

        System.out.println(userExploringAttributes);

        AttributeSuggestions attributeSuggestions = new AttributeSuggestions();
        attributeSuggestions.setUserExploringAttributes(userExploringAttributes);

        outputResponse.setAttributeSuggestions(attributeSuggestions);

        System.out.println(outputResponse.toString());

        return outputResponse;
    }

    @RequestMapping(value="/updateFilter", method= RequestMethod.GET)
    public OutputResponse filterCoordinates(@RequestParam("filter") String filterMap,
                                            @RequestParam("colorMap") Object colorMap,
                                            @RequestParam("datasetId") String datasetId) {
    	try {

			Filters filters =  new ObjectMapper().readValue(filterMap, Filters.class);
			List<Filter> filtersList = filters.getFilterList();
			List<String> users = null;
			List<String> charts = null;
			List<String> weights = null;
			for(Filter filter : filtersList) {
                Map<String, List<String>> map = filter.getMap();
                if(map.containsKey("users")) {
                    users = map.get("users");
                }
                if(map.containsKey("charts")){
                    charts = map.get("charts");
                }
                if(map.containsKey("weights")) {
                    weights = map.get("weights");
                }
            }

            Double descWeight = 1.0;
			Double attrWeight = 1.0;
			Double chartEncodingWeight = 1.0;

			try {

                descWeight = Double.parseDouble(weights.get(2));
                attrWeight = Double.parseDouble(weights.get(0));
                chartEncodingWeight = Double.parseDouble(weights.get(1));
            } catch (Exception e) {

            }

            List<FeatureVector> featurevectors = new ArrayList<>();
            List<Chart> chartObjs = new ArrayList<>();

            if(datasetId.equals("olympics")) {
                featurevectors = dbUtil.getFeaturesFromCollection(configuration.getMongoDatabase(),
                        configuration.getTotalFeatureCollection());

                chartObjs = dbUtil.searchDocsInaCollection(configuration.getMongoDatabase(), configuration.getOlympicchartcollection(), users, charts);
            } else {
                featurevectors = dbUtil.getFeaturesFromCollection(configuration.getDataset2mongoDatabase(),
                        configuration.getDataset2totalFeatureCollection());

                chartObjs = dbUtil.searchDocsInaCollection(configuration.getDataset2mongoDatabase(),
                        configuration.getCrimechartcollection(), users, charts);
            }

            Set<String> filteredIds = new HashSet<>();

            for(Chart chartObj : chartObjs) {
                filteredIds.add(chartObj.getId());
            }

            System.out.println("filtrerd IDs "+ filteredIds.size());
            System.out.println("charts "+ chartObjs.size());

            List<FeatureVector> filteredFeatureVectors = new ArrayList<>();
            for(FeatureVector featureVector : featurevectors) {
                if(filteredIds.contains(featureVector.getId())) {
                    List<IdFeatures> idfeatures = featureVector.getFeatures();
                    List<IdFeatures> filteredidfeatures = new ArrayList<>();
                    for(IdFeatures idFeature : idfeatures) {
                        if(filteredIds.contains(idFeature.getId())) {
                            filteredidfeatures.add(idFeature);
                        }
                    }
                    featureVector.setFeatures(filteredidfeatures);
                    filteredFeatureVectors.add(featureVector);
                }
            }

//            System.out.println("Filterd feature vectors "+filteredFeatureVectors.size());
//            System.out.println(filteredFeatureVectors);

            HashMap<Integer, List<IdCoordinates>> coordinatesMap = coordinatesUtil.getCoordinates(datasetId, chartObjs,2, filteredFeatureVectors, descWeight, attrWeight,chartEncodingWeight, colorMap);
//            System.out.println("Filtered coordinates map "+coordinatesMap);
//            System.out.println(coordinatesMap);
            List<Cluster> clusterList = clusterUtil.generateClusterInfo(coordinatesMap, chartObjs);

            //System.out.println(clusterList);
            //System.out.println(coordinatesMap.values());

            HashMap<String, Integer> attributesMap = attributeUtil.getAttributesList(chartObjs);
            HashMap<String, Set<String>> attributeCoOccurenceMap = attributeUtil.getAttributeCooccurenceMap(chartObjs);

            HashMap<String, Integer> attributesFullMap = new HashMap<>();
            if(datasetId.equals("olympics")) {
                attributesFullMap = attributeUtil.getFullAttributesMap(attributesMap, configuration.getDataset1Attributes());
            } else {
                attributesFullMap = attributeUtil.getFullAttributesMap(attributesMap, configuration.getDataset2Attributes());
            }

            DataCoverageResponse dataCoverageResponse = new DataCoverageResponse();
            dataCoverageResponse.setAttributesMap(attributesFullMap);
            dataCoverageResponse.setAttributeOccurenceMap(attributeCoOccurenceMap);
            //dataCoverageResponse.setAttributeKeys(attributesMap.keySet());

            OutputResponse outputResponse = new OutputResponse();
            outputResponse.setFilterMap(filterMap);
            outputResponse.setCoordinatesList(coordinatesMap.values());
            outputResponse.setClusters(clusterList);
            outputResponse.setDataCoverage(dataCoverageResponse);

            System.out.println(outputResponse.toString());
            return outputResponse;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
    }
}
