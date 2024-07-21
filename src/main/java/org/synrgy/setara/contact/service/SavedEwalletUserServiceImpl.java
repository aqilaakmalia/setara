package org.synrgy.setara.contact.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.synrgy.setara.contact.model.SavedEwalletUser;
import org.synrgy.setara.contact.repository.SavedEwalletUserRepository;
import org.synrgy.setara.user.model.EwalletUser;
import org.synrgy.setara.user.model.User;
import org.synrgy.setara.user.repository.EwalletUserRepository;
import org.synrgy.setara.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedEwalletUserServiceImpl implements SavedEwalletUserService {
    private final Logger log = LoggerFactory.getLogger(SavedEwalletUserServiceImpl.class);
    private final SavedEwalletUserRepository savedEwalletUserRepo;
    private final UserRepository userRepo;
    private final EwalletUserRepository ewalletUserRepo;

    @Override
    public void seedSavedEwalletUsers() {
        // Ambil semua pengguna dan e-wallet pengguna dari database
        List<User> users = userRepo.findAll();
        List<EwalletUser> ewalletUsers = ewalletUserRepo.findAll();

        // Cek apakah ada pengguna dan e-wallet yang ada
        if (users.isEmpty() || ewalletUsers.isEmpty()) {
            log.warn("No users or e-wallet users found in the database.");
            return;
        }

        // Buat dan simpan SavedEwalletUser
        for (User user : users) {
            for (EwalletUser ewalletUser : ewalletUsers) {
                // Cek apakah SavedEwalletUser sudah ada
                boolean exists = savedEwalletUserRepo.existsByOwnerAndEwalletUser(user, ewalletUser);

                if (exists) {
                    log.info("SavedEwalletUser with owner {} and ewalletUser {} already exists.", user.getName(), ewalletUser.getName());
                } else {
                    // Buat SavedEwalletUser
                    SavedEwalletUser savedEwalletUser = SavedEwalletUser.builder()
                            .owner(user)
                            .ewalletUser(ewalletUser)
                            .favorite(false) // Atur sebagai favorit jika diperlukan
                            .build();

                    // Simpan ke database
                    savedEwalletUserRepo.save(savedEwalletUser);
                    log.info("SavedEwalletUser with owner {} and ewalletUser {} has been added to the database", user.getName(), ewalletUser.getName());
                }
            }
        }
    }
}
