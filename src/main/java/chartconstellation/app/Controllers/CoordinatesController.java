package chartconstellation.app.Controllers;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.appconfiguration.ScalingConfig;
import chartconstellation.app.clustering.Clustering;
import chartconstellation.app.entities.response.IdCoordinates;
import chartconstellation.app.util.CoordinatesScalingUtil;
import chartconstellation.app.util.CoordinatesUtil;
import chartconstellation.app.util.ScalingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
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
                                                      @RequestParam("chartEncodingWeight") Double chartEncodingWeight) {

        List<IdCoordinates> coordinatesList = coordinatesUtil.calculateCoordinates(descWeight, attrWeight, chartEncodingWeight);

        coordinatesScalingUtil.setCoordinatesList(coordinatesList);

        coordinatesList = coordinatesScalingUtil.getScaledCoordinates(configuration.getMdsScalingConfig());

        List<IdCoordinates> clusteredCoordinates = clustering.getClusteredCoordinates(5, 15, coordinatesList);


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

        return coordinatesHashMap.values();
    }
}
