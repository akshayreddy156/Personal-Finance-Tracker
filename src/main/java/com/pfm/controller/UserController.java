package com.pfm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfm.dto.LoginRequest;
import com.pfm.dto.RegisterRequest;
import com.pfm.dto.UserResponse;
import com.pfm.model.User;
import com.pfm.repository.UserRepository;
import com.pfm.security.CustomUserDetails;
import com.pfm.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {

	private UserService userService;
	private AuthenticationManager authManager;

	private UserRepository userRepository;

	public UserController(AuthenticationManager authManager, UserService userService) {
		this.authManager = authManager;
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
		UserResponse resp = userService.register(request);
		return ResponseEntity.status(201).body(resp);
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponse> login(@RequestBody LoginRequest req, HttpServletRequest http) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(req.getEmail(),
				req.getPassword());
		Authentication auth = authManager.authenticate(token); // throws if bad
		SecurityContextHolder.getContext().setAuthentication(auth);
		HttpSession session = http.getSession(true);
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

		CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
		User u = cud.getUser();
		UserResponse resp = new UserResponse(u.getUserId(), u.getName(), u.getEmail(), u.getMonthlyIncome(),
				u.getAvailableBalance());

		return ResponseEntity.ok(resp);
	}

	@GetMapping("/me")
	public ResponseEntity<UserResponse> me(@AuthenticationPrincipal CustomUserDetails cud) {
		if (cud == null)
			return ResponseEntity.status(401).build();
		User u = userRepository.findById(cud.getUser().getUserId())
				.orElseThrow(() -> new UsernameNotFoundException("User not found with" + cud.getUser().getUserId()));
		return ResponseEntity.ok(new UserResponse(u.getUserId(), u.getName(), u.getEmail(), u.getMonthlyIncome(),
				u.getAvailableBalance()));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
		return ResponseEntity.ok().build();
	}
}