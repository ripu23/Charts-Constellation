package chartconstellation.app.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class usersController {

    @RequestMapping(value="/getUsers", method= RequestMethod.GET)
    @ResponseBody
    public void getUsers() {


    }
}
