package chartconstellation.app.Controllers;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.util.CoordinatesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CoordinatesController {

    @Autowired
    Configuration configuration;

    @Autowired
    CoordinatesUtil coordinatesUtil;

    @RequestMapping(value="/getCoordinates", method= RequestMethod.GET)
    public ModelAndView login(@RequestParam("descWeight") Double descWeight,
                              @RequestParam("attrWeight") Double attrWeight,
                              @RequestParam("chartEncodingWeight") Double chartEncodingWeight) {

        coordinatesUtil.calculateCoordinates(descWeight, attrWeight, chartEncodingWeight);

        return null;
    }
}
