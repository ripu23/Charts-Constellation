package chartconstellation.app.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clusters")
public class ClusterController {

    @RequestMapping(value="/getCoordinates", method= RequestMethod.GET)
    public void getClusterData() {

    }
}
