package org.synrgy.setara.contact.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.synrgy.setara.common.dto.BaseResponse;
import org.synrgy.setara.contact.dto.FavoriteRequest;
import org.synrgy.setara.contact.dto.FavoriteResponse;
import org.synrgy.setara.contact.dto.SavedEwalletUserResponse;
import org.synrgy.setara.contact.exception.SavedEwalletExceptions;
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

    @GetMapping("/saved-ewallets")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<BaseResponse<List<SavedEwalletUserResponse>>> getSavedEwallets(
            @RequestParam(required = false) Boolean favorite) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String signature = authentication.getName();

        Optional<User> optionalUser = userRepo.findBySignature(signature);

        if (optionalUser.isEmpty()) {
            throw new SavedEwalletExceptions.UserNotFoundException("User not found");
        }

        User user = optionalUser.get();
        List<SavedEwalletUserResponse> savedEwallets = savedEwalletUserService.getSavedEwalletUsersForUser(user, favorite);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, savedEwallets, "Success Get Saved E-Wallets"));
    }

    @PutMapping(
            value = "/favorite-ewallet",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BaseResponse<FavoriteResponse>> putFavoriteEwallet(@RequestBody FavoriteRequest request) {
        FavoriteResponse favoriteResponse = savedEwalletUserService.putFavoriteEwalletUser(request.getIdTersimpan(), request.isFavorite());
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, favoriteResponse, "Success update is favorite ewallet"));
    }
}
