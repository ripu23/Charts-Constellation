package chartconstellation.app.Controllers;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.entities.UserCharts;
import chartconstellation.app.util.ChartsUtil;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    Configuration configuration;

    @Autowired
    ChartsUtil chartsUtil;

    @RequestMapping(value="/getUserCharts", method= RequestMethod.GET)
    public Collection<UserCharts> getUsers() {

        HashMap<String, UserCharts> map = chartsUtil
                .getAllUserCharts(configuration.getMongoDatabase()
                        , configuration.getOlympicchartcollection());

        System.out.println(map.size());
        System.out.println(map);

        return map.values();

    }
}
