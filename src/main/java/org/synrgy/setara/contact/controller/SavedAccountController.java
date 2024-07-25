package org.synrgy.setara.contact.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.synrgy.setara.common.dto.BaseResponse;
import org.synrgy.setara.contact.dto.FavoriteRequest;
import org.synrgy.setara.contact.dto.FavoriteResponse;
import org.synrgy.setara.contact.dto.SavedAccountResponse;
import org.synrgy.setara.contact.service.SavedAccountService;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SavedAccountController {

  private final Logger log = LoggerFactory.getLogger(SavedAccountController.class);
  private final SavedAccountService saService;

  @GetMapping(
          value = "/saved-accounts",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<List<SavedAccountResponse>> getSavedAccounts(
          @RequestParam(value = "fav-only", defaultValue = "false") boolean favOnly) {
    // dummy ownerId, in future use interceptor to get from token
    UUID ownerId = UUID.randomUUID();

    log.info("Fetching saved accounts, owner_id=[{}], fav_only=[{}]", ownerId, favOnly);

    List<SavedAccountResponse> responses = saService.getSavedAccounts(ownerId, favOnly);
    return ResponseEntity.ok(responses);
  }

  @PutMapping("/favorite-account")
  public ResponseEntity<BaseResponse<FavoriteResponse>> putFavoriteAccount(@RequestBody FavoriteRequest request) {
    FavoriteResponse favoriteResponse = saService.putFavoriteAccount(request.getIdTersimpan(), request.isFavorite());
    BaseResponse<FavoriteResponse> response = BaseResponse.success(HttpStatus.OK, favoriteResponse, "Success update is favorite account");
    return ResponseEntity.ok(response);
  }
}
