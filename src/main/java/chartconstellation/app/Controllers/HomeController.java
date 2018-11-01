package chartconstellation.app.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
	
	@RequestMapping("/")
	public ModelAndView loginRender() {
		return new ModelAndView("index.html");
	}
	
	
	@RequestMapping(value="/home")
	public ModelAndView home(){
		return new ModelAndView("index.html");
	}
	
}
