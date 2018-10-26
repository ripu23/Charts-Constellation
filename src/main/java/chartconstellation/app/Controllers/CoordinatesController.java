package chartconstellation.app.Controllers;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.entities.User;
import jdk.nashorn.internal.objects.annotations.Constructor;
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

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public ModelAndView login(@RequestParam("descWeight") Double descWeight,
                              @RequestParam("attrWeight") Double attrWeight,
                              @RequestParam("chartEncodingWeight") Double chartEncodingWeight) {



        return null;
    }
}
