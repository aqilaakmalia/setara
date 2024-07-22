package org.synrgy.setara.contact.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.synrgy.setara.common.dto.BaseResponse;
import org.synrgy.setara.contact.dto.SavedEwalletUserResponse;
import org.synrgy.setara.contact.service.SavedEwalletUserService;
import org.synrgy.setara.user.model.User;
import org.synrgy.setara.user.repository.UserRepository;
import org.synrgy.setara.user.service.UserService;

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

        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String signature = authentication.getName(); // Assume getName() returns the signature in your case

        // Find user by signature
        Optional<User> optionalUser = userRepo.findBySignature(signature);

        if (optionalUser.isEmpty()) {
            // Handle user not found case
            BaseResponse<List<SavedEwalletUserResponse>> response = BaseResponse.failure(404, "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        User user = optionalUser.get();

        // Get saved ewallets for the user with optional favorite filter
        List<SavedEwalletUserResponse> savedEwallets = savedEwalletUserService.getSavedEwalletUsersForUser(user, favorite);
        BaseResponse<List<SavedEwalletUserResponse>> response = BaseResponse.success(savedEwallets, "Success Get Saved E-Wallets");

        return ResponseEntity.ok(response);
    }
}


