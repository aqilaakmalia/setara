package org.synrgy.setara.auth.service;

import org.synrgy.setara.auth.dto.AuthResponse;
import org.synrgy.setara.auth.dto.LoginRequest;

public interface AuthService {

  AuthResponse authenticate(LoginRequest request);

}
