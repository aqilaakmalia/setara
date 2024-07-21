package org.synrgy.setara.contact.service;

import org.synrgy.setara.contact.dto.SavedEwalletUserDTO;
import org.synrgy.setara.user.model.User;
import java.util.List;

public interface SavedEwalletUserService {
    void seedSavedEwalletUsers();

    List<SavedEwalletUserDTO> getSavedEwalletUsersForUser(User user);
}
