package org.synrgy.setara.contact.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.synrgy.setara.common.dto.BaseResponse;
import org.synrgy.setara.contact.dto.PutFavoriteRequest;
import org.synrgy.setara.contact.dto.SavedEwalletUserResponse;
import org.synrgy.setara.contact.model.SavedAccount;
import org.synrgy.setara.contact.model.SavedEwalletUser;
import org.synrgy.setara.contact.service.SavedEwalletUserService;
import org.synrgy.setara.user.model.User;
import org.synrgy.setara.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/contact")
@RequiredArgsConstructor
public class SavedEwalletUserController {

    private final SavedEwalletUserService savedEwalletUserService;
    private final UserRepository userRepo;
    private final Logger log = LoggerFactory.getLogger(SavedEwalletUserController.class);

    @GetMapping("/saved-ewallets")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<BaseResponse<List<SavedEwalletUserResponse>>> getSavedEwallets(
            @RequestParam(required = false) Boolean favorite) {

        try {
            // Get the currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String signature = authentication.getName(); // Assume getName() returns the signature in your case

            // Find user by signature
            Optional<User> optionalUser = userRepo.findBySignature(signature);

            if (optionalUser.isEmpty()) {
                // Handle user not found case
                BaseResponse<List<SavedEwalletUserResponse>> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            User user = optionalUser.get();

            // Get saved ewallets for the user with optional favorite filter
            List<SavedEwalletUserResponse> savedEwallets = savedEwalletUserService.getSavedEwalletUsersForUser(user, favorite);
            BaseResponse<List<SavedEwalletUserResponse>> response = BaseResponse.success(HttpStatus.OK, savedEwallets, "Success Get Saved E-Wallets");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error occurred while fetching saved ewallets: ", e);
            BaseResponse<List<SavedEwalletUserResponse>> response = BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping(
            value = "/favorite-ewallet",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity putFavoriteEwallet(@RequestBody PutFavoriteRequest request) {
        try {
            SavedEwalletUser savedEwallet = savedEwalletUserService.putFavoriteEwalletUser(request.getIdTersimpan(), request.isFavorite());
            BaseResponse<SavedEwalletUser> response = BaseResponse.success(savedEwallet, "Success update is favorite ewallet");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(BaseResponse.failure(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

}
