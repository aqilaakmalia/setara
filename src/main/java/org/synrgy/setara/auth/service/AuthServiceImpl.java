package org.synrgy.setara.auth.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.synrgy.setara.auth.dto.AuthResponse;
import org.synrgy.setara.auth.dto.LoginRequest;
import org.synrgy.setara.security.service.JwtService;
import org.synrgy.setara.user.model.User;
import org.synrgy.setara.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

  private final UserRepository userRepo;

  private final JwtService jwtService;

  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public AuthResponse authenticate(LoginRequest request) {
    User user = userRepo.findByUniqueId(request.getUniqueId()).orElse(null);
    if (user == null) {
      log.error("Unique ID {} not found", request.getUniqueId());
      throw new EntityNotFoundException("User not found");
    }

    String token = jwtService.generateToken(user);

    return AuthResponse.of(user, token);
  }

}
