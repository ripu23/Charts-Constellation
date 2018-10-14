package chartconstellation.app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import chartconstellation.app.entities.User;
import chartconstellation.app.service.UserService;

@RestController
public class HomeController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public ModelAndView loginRender() {
		return new ModelAndView("login.html");
	}
	
	@RequestMapping("/register")
	public @ResponseBody User register(@RequestBody User user) {
		return userService.save(user); 
	}
	
	@RequestMapping(value="/home")
	public ModelAndView home(){
		return new ModelAndView("index.html");
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
		
		User fetchedUser = userService.getUser(new User(userName, password));
		
		if(fetchedUser != null) {
			return new ModelAndView("redirect:/home");
		}
		return null;
	}
}
