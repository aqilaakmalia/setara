package org.synrgy.setara.user.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.synrgy.setara.user.model.User;
import org.synrgy.setara.user.repository.UserRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

  private final UserRepository userRepo;

  private final PasswordEncoder passwordEncoder;

  @Override
  public void seedUser() {
    final String email = "kdot@tde.com";
    final String signature = "KDOT604T";
    final String accountNumber = "1122334455";
    final String nik = "1272051706870001";
    final String phoneNumber = "+6281234567890";

    boolean userExists = userRepo.existsByEmail(email) ||
        userRepo.existsBySignature(signature) ||
        userRepo.existsByAccountNumber(accountNumber) ||
        userRepo.existsByNik(nik) ||
        userRepo.existsByPhoneNumber(phoneNumber);

    if (userExists) {
      log.info("kdot already exists in the system");
      return;
    }

    User user = User.builder()
        .email(email)
        .signature(signature)
        .accountNumber(accountNumber)
        .name("Kendrick Lamar")
        .password(passwordEncoder.encode("itsjustbigme"))
        .imagePath("kendrick.jpg")
        .nik(nik)
        .phoneNumber(phoneNumber)
        .address("Compton, CA")
        .balance(BigDecimal.ZERO)
        .mpin("170687")
        .build();

    userRepo.save(user);
    log.info("kdot has been added to the system");
  }

}
