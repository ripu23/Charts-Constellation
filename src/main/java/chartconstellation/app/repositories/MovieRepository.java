package chartconstellation.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import chartconstellation.app.entities.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long>{

}
