package chartconstellation.app.Controllers;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.clustering.Clustering;
import chartconstellation.app.entities.response.IdCoordinates;
import chartconstellation.app.util.CoordinatesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value="/getCoordinates", method= RequestMethod.GET)
    public Collection<List<IdCoordinates>> coordinates(@RequestParam("descWeight") Double descWeight,
                                                      @RequestParam("attrWeight") Double attrWeight,
                                                      @RequestParam("chartEncodingWeight") Double chartEncodingWeight) {

        List<IdCoordinates> coordinatesList = coordinatesUtil.calculateCoordinates(descWeight, attrWeight, chartEncodingWeight);

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
