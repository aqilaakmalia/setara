package org.synrgy.setara.contact.dto;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SavedEwalletUserResponse {
    private UUID id;
    private UUID ownerId;
    private UUID ewalletUserId;
    private boolean favorite;
    private String ewalletUserName;
    private String ewalletUserImagePath;
    private String ewalletUserPhoneNumber;
    private String ewalletName;
}
