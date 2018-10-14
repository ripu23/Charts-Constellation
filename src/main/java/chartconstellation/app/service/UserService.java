package chartconstellation.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chartconstellation.app.entities.User;
import chartconstellation.app.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User save (User user) {
		return userRepository.save(user);
	}
	public User getUser(User user) {
		return userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
	}
}
