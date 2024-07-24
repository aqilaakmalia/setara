package org.synrgy.setara.contact.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.synrgy.setara.contact.dto.SavedEwalletUserResponse;
import org.synrgy.setara.contact.model.SavedEwalletUser;
import org.synrgy.setara.contact.repository.SavedEwalletUserRepository;
import org.synrgy.setara.user.model.EwalletUser;
import org.synrgy.setara.user.model.User;
import org.synrgy.setara.user.repository.EwalletUserRepository;
import org.synrgy.setara.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavedEwalletUserServiceImpl implements SavedEwalletUserService {
    private final Logger log = LoggerFactory.getLogger(SavedEwalletUserServiceImpl.class);
    private final SavedEwalletUserRepository savedEwalletUserRepo;
    private final SavedEwalletUserRepository sewuRepo;
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
    public List<SavedEwalletUserResponse> getSavedEwalletUsersForUser(User user, Boolean favorite) {
        try {
            List<SavedEwalletUser> savedEwalletUsers;
            if (favorite != null) {
                savedEwalletUsers = savedEwalletUserRepo.findByOwnerIdAndFavorite(user.getId(), favorite);
            } else {
                savedEwalletUsers = savedEwalletUserRepo.findByOwnerId(user.getId());
            }
            return savedEwalletUsers.stream()
                    .map(saved -> new SavedEwalletUserResponse(
                            saved.getId(),
                            saved.getOwner().getId(),
                            saved.getEwalletUser().getId(),
                            saved.isFavorite(),
                            saved.getEwalletUser().getName(),
                            saved.getEwalletUser().getImagePath(),
                            saved.getEwalletUser().getPhoneNumber(),
                            saved.getEwalletUser().getEwallet().getName()
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching saved ewallet users for user {}: ", user.getId(), e);
            throw e; // Re-throw to be handled by controller
        }
    }

    @Override
    @Transactional
    public SavedEwalletUser putFavoriteEwalletUser(UUID idTersimpan, boolean isFavorite) {
        Optional<SavedEwalletUser> optionalSavedEwalletUser = savedEwalletUserRepo.findById(idTersimpan);
        if (optionalSavedEwalletUser.isPresent())
        {
            SavedEwalletUser savedEwalletUser = optionalSavedEwalletUser.get();
            savedEwalletUser.setFavorite(isFavorite);
            return savedEwalletUserRepo.save(savedEwalletUser);
        } else {
            throw new EntityNotFoundException("ewalletSaved with id " + idTersimpan + " not found");
        }
    }
}
