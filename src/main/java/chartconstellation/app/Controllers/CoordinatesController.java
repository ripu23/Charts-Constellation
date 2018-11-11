package chartconstellation.app.Controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.clustering.Clustering;
import chartconstellation.app.entities.response.IdCoordinates;
import chartconstellation.app.util.CoordinatesScalingUtil;
import chartconstellation.app.util.CoordinatesUtil;


@RestController
@RequestMapping("/coordinates")
public class CoordinatesController {

    @Autowired
    Configuration configuration;

    @Autowired
    CoordinatesUtil coordinatesUtil;

    @Autowired
    Clustering clustering;

    @Autowired
    CoordinatesScalingUtil coordinatesScalingUtil;

    @RequestMapping(value="/getCoordinates", method= RequestMethod.GET)
    @ResponseBody
    public Collection<List<IdCoordinates>> coordinates(@RequestParam("descWeight") Double descWeight,
                                                       @RequestParam("attrWeight") Double attrWeight,
                                                       @RequestParam("chartEncodingWeight") Double chartEncodingWeight,
                                                       @RequestParam("colorMap") Object colorMap) {


        List<IdCoordinates> coordinatesList = coordinatesUtil.calculateCoordinates(descWeight, attrWeight, chartEncodingWeight);

        List<IdCoordinates> clusteredCoordinates = clustering.getClusteredCoordinates(4, 20, coordinatesList);

//        coordinatesScalingUtil.setCoordinatesList(clusteredCoordinates);
//
//        clusteredCoordinates = coordinatesScalingUtil.getScaledCoordinates(configuration.getMdsScalingConfig());


        HashMap<Integer, List<IdCoordinates>> coordinatesHashMap = new HashMap<>();

        for(IdCoordinates idCoordinate : clusteredCoordinates) {

            if(coordinatesHashMap.containsKey(idCoordinate.getClusterId())) {

                List<IdCoordinates> coordinates = coordinatesHashMap.get(idCoordinate.getClusterId());
                coordinates.add(idCoordinate);
                coordinatesHashMap.put(idCoordinate.getClusterId(), coordinates);

            } else {

                List<IdCoordinates> coordinates = new ArrayList<>();
                coordinates.add(idCoordinate);
                coordinatesHashMap.put(idCoordinate.getClusterId(), coordinates);

            }
        }

        for(Map.Entry<Integer, List<IdCoordinates>> entry : coordinatesHashMap.entrySet()) {

            coordinatesScalingUtil.setCoordinatesList(entry.getValue());

            List<IdCoordinates> newIdCoordinatesList = coordinatesScalingUtil.getScaledCoordinates(configuration.getMdsScalingConfig());

            coordinatesHashMap.put(entry.getKey(), newIdCoordinatesList);
        }
        return coordinatesHashMap.values();
    }
}
