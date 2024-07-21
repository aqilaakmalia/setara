package org.synrgy.setara.contact.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.synrgy.setara.contact.repository.SavedEwalletUserRepository;

@Service
@RequiredArgsConstructor
public class SavedEwalletUserServiceImpl implements SavedEwalletUserService {

  private final SavedEwalletUserRepository sewuRepo;

  // override methods here

}
