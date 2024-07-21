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
    // Data untuk pengguna pertama
    createUserIfNotExists(
            "kdot@tde.com",
            "KDOT604T",
            "1122334455",
            "1272051706870001",
            "+6281234567890",
            "Kendrick Lamar",
            "itsjustbigme",
            "kendrick.jpg",
            "Compton, CA",
            BigDecimal.ZERO,
            "170687"
    );

    // Data untuk pengguna kedua
    createUserIfNotExists(
            "jane.doe@example.com",
            "JANE1234",
            "2233445566",
            "1272051706870002",
            "+6289876543210",
            "Jane Doe",
            "jane123",
            "jane.jpg",
            "Los Angeles, CA",
            BigDecimal.valueOf(5000),
            "987654"
    );

    // Data untuk pengguna ketiga
    createUserIfNotExists(
            "john.smith@example.com",
            "JOHN5678",
            "3344556677",
            "1272051706870003",
            "+6281230987654",
            "John Smith",
            "john123",
            "john.jpg",
            "New York, NY",
            BigDecimal.valueOf(10000),
            "123456"
    );
  }

  private void createUserIfNotExists(String email, String signature, String accountNumber, String nik,
                                     String phoneNumber, String name, String password, String imagePath,
                                     String address, BigDecimal balance, String mpin) {
    boolean userExists = userRepo.existsByEmail(email) ||
            userRepo.existsBySignature(signature) ||
            userRepo.existsByAccountNumber(accountNumber) ||
            userRepo.existsByNik(nik) ||
            userRepo.existsByPhoneNumber(phoneNumber);

    if (userExists) {
      log.info("{} already exists in the system", email);
      return;
    }

    User user = User.builder()
            .email(email)
            .signature(signature)
            .accountNumber(accountNumber)
            .name(name)
            .password(passwordEncoder.encode(password))
            .imagePath(imagePath)
            .nik(nik)
            .phoneNumber(phoneNumber)
            .address(address)
            .balance(balance)
            .mpin(mpin)
            .build();

    userRepo.save(user);
    log.info("{} has been added to the system", email);
  }
}
