package org.synrgy.setara.contact.service;

import org.synrgy.setara.contact.dto.FavoriteResponse;
import org.synrgy.setara.contact.dto.SavedEwalletUserResponse;
import org.synrgy.setara.user.model.User;
import java.util.List;
import java.util.UUID;

public interface SavedEwalletUserService {
    void seedSavedEwalletUsers();

    List<SavedEwalletUserResponse> getSavedEwalletUsersForUser(User user, Boolean favorite);

    FavoriteResponse putFavoriteEwalletUser(UUID idTersimpan, boolean isFavorite);
}
