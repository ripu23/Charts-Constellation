package chartconstellation.app.Controllers;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.entities.UserCharts;
import chartconstellation.app.util.ChartsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/users")
public class usersController {

    @Autowired
    Configuration configuration;

    @Autowired
    ChartsUtil chartsUtil;

    @RequestMapping(value="/getUserCharts", method= RequestMethod.GET)
    public HashMap<String, UserCharts> getUsers() {

        HashMap<String, UserCharts> map = chartsUtil
                .getAllUserCharts(configuration.getMongoDatabase()
                        , configuration.getOlympicchartcollection());

        System.out.println(map.size());
        System.out.println(map);

        return map;

    }
}
