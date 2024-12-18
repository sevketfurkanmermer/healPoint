package com.proje.healpoint.controller;

import com.proje.healpoint.jwt.AuthRequest;
import com.proje.healpoint.jwt.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthController {
    public ResponseEntity<AuthResponse> authenticate(AuthRequest authRequest);
    public ResponseEntity<?> login(AuthRequest authRequest);
}
