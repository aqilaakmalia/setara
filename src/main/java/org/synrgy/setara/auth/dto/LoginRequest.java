package org.synrgy.setara.auth.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

  @Size(
    min = 3,
    max = 32,
    message = "User ID must be {min}-{max} characters long"
  )
  @Pattern(
    regexp = "^[a-zA-Z0-9]+$",
    message = "User ID can only have letters and numbers"
  )
  private String uniqueId;

  @Size(
    min = 6,
    max = 6,
    message = "MPIN must be {max} characters long"
  )
  @Pattern(
    regexp = "^\\d{6}$",
    message = "MPIN can only have letters and numbers"
  )
  private String mpin;

}
