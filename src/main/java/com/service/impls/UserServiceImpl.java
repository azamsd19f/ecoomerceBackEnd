package com.service.impls;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dto.LoginResponse;
import com.dto.UserDto;
import com.entity.User;
import com.exception.ResourceNotFound;
import com.repository.UserRepository;
import com.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public String addUsers(UserDto userDto) {
		User user = mapper.map(userDto, User.class);
		log.info("After mapping: user object looks like  {}", user);
		this.userRepository.save(user);
		return "User Added Successfully";
	}

	@Override
	public List<UserDto> fetchAllUser() {
		List<User> listOfUser = userRepository.findAll();
		List<UserDto> userDto = listOfUser.stream().map(product -> {
			UserDto productDto = mapper.map(product, UserDto.class);

			return productDto;
		}).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public LoginResponse userLogin(String email, String password) {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmailAndPassword(email, password);
		if (user == null)
			new ResourceNotFound("User Doesn't Exists For This Email");
		LoginResponse myResponse = this.mapper.map(user, LoginResponse.class);
		log.info("login response {}",myResponse);
		myResponse.setLoggedInStatus(true);
		return myResponse;
	}

	@Override
	public UserDto getUserByUserId(Integer userId) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public String updateUsers(UserDto dto, Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
