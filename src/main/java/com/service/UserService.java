package com.service;

import java.util.List;

import com.dto.LoginResponse;
import com.dto.UserDto;

public interface UserService {

	public String addUsers(UserDto userDto);

	public List<UserDto> fetchAllUser();

	public LoginResponse userLogin(String email,String password);

	public UserDto getUserByUserId(Integer userId);

	public String updateUsers(UserDto dto, Integer userId);

	public String deleteUser(Integer userId);

}
