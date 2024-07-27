package org.synrgy.setara.contact.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({"total_favorites", "total_saved", "favorites", "saved"})
public class SavedEwalletSummaryResponse {
    private long totalFavorites;
    private long totalSaved;
    private List<SavedEwalletUserResponse> favorites;
    private List<SavedEwalletUserResponse> saved;
}
