package chartconstellation.app.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chartconstellation.app.AppConfiguration.Configuration;
import chartconstellation.app.clustering.Clustering;
import chartconstellation.app.entities.response.IdCoordinates;
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

    @RequestMapping(value="/getCoordinates", method= RequestMethod.GET)
    public List<IdCoordinates> coordinates(@RequestParam("descWeight") Double descWeight,
                              @RequestParam("attrWeight") Double attrWeight,
                              @RequestParam("chartEncodingWeight") Double chartEncodingWeight) {

        List<IdCoordinates> coordinatesList = coordinatesUtil.calculateCoordinates(descWeight, attrWeight, chartEncodingWeight);

        List<IdCoordinates> clusteredCoordinates = clustering.getClusteredCoordinates(5, 15, coordinatesList);

        return clusteredCoordinates;
    }
}
