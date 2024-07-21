package org.synrgy.setara.contact.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.synrgy.setara.contact.dto.SavedEwalletUserDTO;
import org.synrgy.setara.contact.model.SavedEwalletUser;
import org.synrgy.setara.contact.repository.SavedEwalletUserRepository;
import org.synrgy.setara.user.model.EwalletUser;
import org.synrgy.setara.user.model.User;
import org.synrgy.setara.user.repository.EwalletUserRepository;
import org.synrgy.setara.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavedEwalletUserServiceImpl implements SavedEwalletUserService {
    private final Logger log = LoggerFactory.getLogger(SavedEwalletUserServiceImpl.class);
    private final SavedEwalletUserRepository savedEwalletUserRepo;
    private final UserRepository userRepo;
    private final EwalletUserRepository ewalletUserRepo;

    @Override
    public void seedSavedEwalletUsers() {
        // Ambil semua e-wallet pengguna dari database
        List<EwalletUser> ewalletUsers = ewalletUserRepo.findAll();

        // Cek apakah ada e-wallet yang ada
        if (ewalletUsers.isEmpty()) {
            log.warn("No e-wallet users found in the database.");
            return;
        }

        // Temukan pengguna dengan nama "Kendrick Lamar"
        Optional<User> optionalOwner = userRepo.findByName("Kendrick Lamar");

        if (optionalOwner.isEmpty()) {
            log.warn("User with name 'Kendrick Lamar' not found.");
            return;
        }

        User owner = optionalOwner.get();

        // Buat dan simpan SavedEwalletUser
        for (EwalletUser ewalletUser : ewalletUsers) {
            // Cek apakah SavedEwalletUser sudah ada
            boolean exists = savedEwalletUserRepo.existsByOwnerAndEwalletUser(owner, ewalletUser);

            if (exists) {
                log.info("SavedEwalletUser with owner {} and ewalletUser {} already exists.", owner.getName(), ewalletUser.getName());
            } else {
                // Buat SavedEwalletUser
                SavedEwalletUser savedEwalletUser = SavedEwalletUser.builder()
                        .owner(owner)
                        .ewalletUser(ewalletUser)
                        .favorite(false) // Atur sebagai favorit jika diperlukan
                        .build();

                // Simpan ke database
                savedEwalletUserRepo.save(savedEwalletUser);
                log.info("SavedEwalletUser with owner {} and ewalletUser {} has been added to the database", owner.getName(), ewalletUser.getName());
            }
        }
    }


    @Override
    public List<SavedEwalletUserDTO> getSavedEwalletUsersForUser(User user) {
        List<SavedEwalletUser> savedEwalletUsers = savedEwalletUserRepo.findByOwnerId(user.getId());
        return savedEwalletUsers.stream()
                .map(saved -> new SavedEwalletUserDTO(
                        saved.getId(),
                        saved.getOwner().getId(),
                        saved.getEwalletUser().getId(),
                        saved.isFavorite()
                ))
                .collect(Collectors.toList());
    }
}
