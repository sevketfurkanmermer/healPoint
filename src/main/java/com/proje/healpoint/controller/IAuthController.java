package com.proje.healpoint.controller;

import com.proje.healpoint.jwt.AuthRequest;
import com.proje.healpoint.jwt.AuthResponse;

public interface IAuthController {
    public AuthResponse authenticate(AuthRequest authRequest);
}
