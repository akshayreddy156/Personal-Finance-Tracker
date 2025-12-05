package com.pfm.service;

import org.springframework.stereotype.Service;

import com.pfm.dto.RegisterRequest;
import com.pfm.dto.UserResponse;

@Service
public interface UserService {
	
    UserResponse register(RegisterRequest request);
    
}
