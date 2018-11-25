package chartconstellation.app.Controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.entities.Attributes;
import chartconstellation.app.entities.ChartType;
import chartconstellation.app.util.AttributeUtil;
import chartconstellation.app.util.ChartsUtil;

@RestController
@RequestMapping("/chartType")
public class ChartTypeController {

    @Autowired
    Configuration configuration;

    @Autowired
    ChartsUtil chartsUtil;
    
    @Autowired
    AttributeUtil attributeUtil;

    @RequestMapping(value="/getAllChartTypes", method= RequestMethod.GET)
    public ChartType getAttributes(@RequestParam("datasetId") String datasetId) {

        if(datasetId.equals("olympics")) {

            ChartType chartTypeObj = chartsUtil.getAllChartTypes(configuration.getMongoDatabase()
                    , configuration.getOlympicchartcollection());

            return chartTypeObj;
        } else {
            ChartType chartTypeObj = chartsUtil.getAllChartTypes(configuration.getDataset2mongoDatabase()
                    , configuration.getCrimechartcollection());
            return chartTypeObj;
        }

    }
    

    @RequestMapping(value="/getAllAttributes", method= RequestMethod.GET)
    public Attributes getAllAttributes(@RequestParam("datasetId") String datasetId) {

        if(datasetId.equals("olympics")) {

            Set<String> AttributeSet = attributeUtil.getAllAttributes(configuration.getMongoDatabase(), configuration.getOlympicchartcollection());

            Attributes attributes = new Attributes();
            attributes.setAttributesSet(AttributeSet);
            return attributes;
        } else {
            Set<String> AttributeSet = attributeUtil.getAllAttributes(configuration.getDataset2mongoDatabase()
                    , configuration.getCrimechartcollection());

            Attributes attributes = new Attributes();
            attributes.setAttributesSet(AttributeSet);
            return attributes;
        }

    }

}
