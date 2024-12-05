package com.proje.healpoint.service;

import com.proje.healpoint.jwt.AuthRequest;
import com.proje.healpoint.jwt.AuthResponse;

public interface IAuthService {
    public AuthResponse authenticate(AuthRequest authRequest);
}
