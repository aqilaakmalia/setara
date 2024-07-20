package org.synrgy.setara.auth.dto;

import lombok.Builder;
import lombok.Data;
import org.synrgy.setara.user.dto.UserProfileResponse;
import org.synrgy.setara.user.model.User;

@Data
@Builder
public class AuthResponse {

  private UserProfileResponse user;

  private String token;

  public static AuthResponse of (User user, String token) {
    return AuthResponse.builder()
        .user(UserProfileResponse.from(user))
        .token(token)
        .build();
  }

}
