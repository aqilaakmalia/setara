package org.synrgy.setara.contact.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.synrgy.setara.contact.dto.FavoriteResponse;
import org.synrgy.setara.contact.dto.SavedEwalletUserResponse;
import org.synrgy.setara.contact.service.SavedEwalletUserService;
import org.synrgy.setara.user.model.User;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/saved-ewallet-users")
@RequiredArgsConstructor
public class SavedEwalletUserController {
    private final Logger log = LoggerFactory.getLogger(SavedEwalletUserController.class);
    private final SavedEwalletUserService savedEwalletUserService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SavedEwalletUserResponse>> getSavedEwalletUsersForUser(
            @PathVariable UUID userId,
            @RequestParam(value = "favorite", required = false) Boolean favorite) {
        User user = new User();  // Replace with actual user fetching logic
        user.setId(userId); // Set user ID
        List<SavedEwalletUserResponse> responses = savedEwalletUserService.getSavedEwalletUsersForUser(user, favorite);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/favorite/{id}")
    public ResponseEntity<FavoriteResponse> putFavoriteEwalletUser(
            @PathVariable UUID id,
            @RequestParam boolean isFavorite) {
        FavoriteResponse response = savedEwalletUserService.putFavoriteEwalletUser(id, isFavorite);
        return ResponseEntity.ok(response);
    }
}
