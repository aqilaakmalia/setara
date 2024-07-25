package org.synrgy.setara.transaction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.synrgy.setara.common.utils.TransactionUtils;
import org.synrgy.setara.contact.model.SavedAccount;
import org.synrgy.setara.contact.repository.SavedAccountRepository;
import org.synrgy.setara.transaction.dto.TransferRequestDTO;
import org.synrgy.setara.transaction.dto.TransferResponseDTO;
import org.synrgy.setara.transaction.exception.TransactionExceptions;
import org.synrgy.setara.transaction.model.Transaction;
import org.synrgy.setara.transaction.model.TransactionType;
import org.synrgy.setara.transaction.repository.TransactionRepository;
import org.synrgy.setara.user.model.User;
import org.synrgy.setara.user.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BankTransferServiceImpl implements BankTransferService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final SavedAccountRepository savedAccountRepository;
    private final PasswordEncoder passwordEncoder;

    private static final BigDecimal BCA_FEE = BigDecimal.valueOf(1000);

    @Override
    public TransferResponseDTO transferWithinBCA(TransferRequestDTO request, String authToken) {
        String signature = SecurityContextHolder.getContext().getAuthentication().getName();
        User sourceUser = userRepository.findBySignature(signature)
                .orElseThrow(() -> new TransactionExceptions.UserNotFoundException("User with signature " + signature + " not found"));

        User destinationUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber())
                .orElseThrow(() -> new TransactionExceptions.DestinationAccountNotFoundException("Destination account not found " + request.getDestinationAccountNumber()));

        validateMpin2(request.getMpin(), sourceUser);

        BigDecimal totalAmount = request.getAmount().add(BigDecimal.valueOf(0));
        checkSufficientBalance2(sourceUser, totalAmount);

        String referenceNumber = TransactionUtils.generateReferenceNumber();
        String uniqueCode = TransactionUtils.generateUniqueCode(referenceNumber);
        Transaction transaction = Transaction.builder()
                .user(sourceUser)
                .bank(sourceUser.getBank())
                .type(TransactionType.TRANSFER)
                .destinationAccountNumber(request.getDestinationAccountNumber())
                .amount(request.getAmount())
                .adminFee(BigDecimal.valueOf(0))
                .totalamount(totalAmount)
                .uniqueCode(uniqueCode)
                .referenceNumber(referenceNumber)
                .note(request.getNote())
                .time(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction);

        sourceUser.setBalance(sourceUser.getBalance().subtract(totalAmount));
        destinationUser.setBalance(destinationUser.getBalance().add(request.getAmount()));
        userRepository.save(sourceUser);
        userRepository.save(destinationUser);

        if (request.isSavedAccount()) {
            SavedAccount savedAccount = SavedAccount.builder()
                    .owner(destinationUser)
                    .bank(destinationUser.getBank())
                    .name(destinationUser.getName())
                    .accountNumber(destinationUser.getAccountNumber())
                    .imagePath(destinationUser.getImagePath())
                    .favorite(false)
                    .build();
            savedAccountRepository.save(savedAccount);
        }

        return TransferResponseDTO.builder()
                .sourceUser(TransferResponseDTO.UserDTO.builder()
                        .name(sourceUser.getName())
                        .bank(sourceUser.getBank().getName())
                        .accountNumber(sourceUser.getAccountNumber())
                        .imagePath(sourceUser.getImagePath())
                        .build())
                .destinationUser(TransferResponseDTO.UserDTO.builder()
                        .name(destinationUser.getName())
                        .bank(destinationUser.getBank().getName())
                        .accountNumber(destinationUser.getAccountNumber())
                        .imagePath(destinationUser.getImagePath())
                        .build())
                .amount(transaction.getAmount())
                .adminFee(BigDecimal.valueOf(0))
                .totalAmount(totalAmount)
                .note(request.getNote())
                .build();
    }

    private void validateMpin2(String mpin, User user) {
        if (!passwordEncoder.matches(mpin, user.getMpin())) {
            throw new TransactionExceptions.InvalidMpinException("Invalid MPIN");
        }
    }

    private void checkSufficientBalance2(User user, BigDecimal totalAmount) {
        if (user.getBalance().compareTo(totalAmount) < 0) {
            throw new TransactionExceptions.InsufficientBalanceException("Insufficient balance");
        }
    }
}
