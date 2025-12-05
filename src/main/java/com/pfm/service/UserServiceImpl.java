package com.pfm.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pfm.dto.RegisterRequest;
import com.pfm.dto.UserResponse;
import com.pfm.exceptions.EmailAlreadyExistException;
import com.pfm.model.User;
import com.pfm.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserResponse register(RegisterRequest request) {
		userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
			throw new EmailAlreadyExistException("Email already registered: " + request.getEmail());
		});

		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		BigDecimal monthlyIncome = request.getMonthlyIncome() == null ? BigDecimal.ZERO : request.getMonthlyIncome();
		user.setMonthlyIncome(monthlyIncome);
		user.setAvailableBalance(monthlyIncome);
		user.setCreatedAt(LocalDateTime.now());
		User saved = userRepository.save(user);
		return new UserResponse(saved.getUserId(), saved.getName(), saved.getEmail(), saved.getMonthlyIncome(),saved.getAvailableBalance());
	}

}
