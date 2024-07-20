package org.synrgy.setara.user.dto;

import lombok.Builder;
import lombok.Data;
import org.synrgy.setara.user.model.User;

import java.math.BigDecimal;

@Data
@Builder
public class UserProfileResponse {

  private String name;

  private BigDecimal balance;

  private String accountNumber;

  // avatar

  public static UserProfileResponse from(User user) {
    return UserProfileResponse.builder()
        .name(user.getName())
        .balance(user.getBalance())
        .accountNumber(user.getAccountNumber())
        .build();
  }

}
