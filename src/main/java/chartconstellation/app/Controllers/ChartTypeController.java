package chartconstellation.app.Controllers;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.entities.ChartType;
import chartconstellation.app.util.ChartsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/attributes")
public class ChartTypeController {

    @Autowired
    Configuration configuration;

    @Autowired
    ChartsUtil chartsUtil;

    @RequestMapping(value="/getAttributes", method= RequestMethod.GET)
    public ChartType getAttributes() {

        ChartType chartTypeObj = chartsUtil.getAllChartTypes(configuration.getMongoDatabase()
                , configuration.getOlympicchartcollection());

        return chartTypeObj;

    }

}
