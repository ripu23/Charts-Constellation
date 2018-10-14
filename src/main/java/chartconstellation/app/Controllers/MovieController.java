package chartconstellation.app.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chartconstellation.app.entities.Movie;
import chartconstellation.app.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@RequestMapping("/getAll")
	public List<Movie> getAllMovies() {
		return movieService.getAll();
	}
}
