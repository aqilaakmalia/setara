package org.synrgy.setara.app.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.synrgy.setara.user.service.UserService;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private final UserService userService;

  @Override
  public void run(String... args) throws Exception {
    userService.seedUser();
  }

}
