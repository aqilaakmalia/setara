package org.synrgy.setara.user.service;

import org.synrgy.setara.user.dto.UserBalanceResponse;

public interface UserService {

  void seedUser();
  UserBalanceResponse getBalance();

}
