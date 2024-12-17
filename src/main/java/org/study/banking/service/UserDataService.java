package org.study.banking.service;

import com.google.common.math.LongMath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.study.banking.dto.UserDataDTO;
import org.study.banking.model.Operation;
import org.study.banking.model.UserData;
import org.study.banking.repository.UserDataRepository;
import org.study.banking.utils.OperationStatus;
import org.study.banking.utils.OperationType;

@Service
@RequiredArgsConstructor
public class UserDataService {
  private final UserDataRepository userDataRepository;
  private final OperationService operationService;

  public UserDataDTO getBalance(Long userId) {
    try {
      return getUserDataDTOById(userId).setStatus(OperationStatus.SUCCESS);
    } catch (RuntimeException e) {
      return new UserDataDTO().setStatus(OperationStatus.USER_NOT_FOUND);
    }
  }

  @Transactional
  public UserDataDTO takeMoney(Long userId, Double moneyInRubles) {
    var userDataDTO = new UserDataDTO();
    try {
      var userData = getUserData(userId);
      if (moneyInRubles <= getMoneyInRubles(userData.getBalance())) {
        saveDecreasedBalance(userId, moneyInRubles, userData);
        var operation = new Operation.Builder(userData.getId())
            .setMoneyAmount(getMoneyInPennies(moneyInRubles))
            .setOperationType(OperationType.WITHDRAWAL)
            .build();
        operationService.saveOperation(operation);
        userDataDTO.setStatus(OperationStatus.SUCCESS);
      } else {
        userDataDTO.setStatus(OperationStatus.NOT_ENOUGH_MONEY);
      }
    } catch (RuntimeException e) {
      userDataDTO.setStatus(OperationStatus.USER_NOT_FOUND);
    }
    return userDataDTO;
  }

  private void saveDecreasedBalance(Long userId, Double moneyInRubles, UserData userData) {
    long updatedBalance = userData.getBalance() - getMoneyInPennies(moneyInRubles);
    var updatedUserData = new UserData(userId, updatedBalance);
    userDataRepository.save(updatedUserData);
  }

  @Transactional
  public UserDataDTO putMoney(Long userId, Double moneyInRubles) {
    var userDataDTO = new UserDataDTO();
    try {
      var userData = getUserData(userId);
      saveIncreasedBalance(userId, moneyInRubles, userData);
      var operation = new Operation.Builder(userData.getId())
          .setMoneyAmount(getMoneyInPennies(moneyInRubles))
          .setOperationType(OperationType.DEPOSIT)
          .build();
      operationService.saveOperation(operation);
      userDataDTO.setStatus(OperationStatus.SUCCESS);
    } catch (ArithmeticException e) {
      userDataDTO.setStatus(OperationStatus.FAILED);
    } catch (RuntimeException e) {
      userDataDTO.setStatus(OperationStatus.USER_NOT_FOUND);
    }
    return userDataDTO;
  }

  private void saveIncreasedBalance(Long userId, Double moneyInRubles, UserData userData) {
    var updatedBalance = LongMath.checkedAdd(userData.getBalance(),
        getMoneyInPennies(moneyInRubles));
    var updatedUserData = new UserData(userId, updatedBalance);
    userDataRepository.save(updatedUserData);
  }

  private UserData getUserData(Long userId) {
    return userDataRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

  private static double getMoneyInRubles(Long moneyAmount) {
    return moneyAmount / 100.0;
  }

  private static long getMoneyInPennies(Double moneyAmount) {
    return (long) (moneyAmount * 100);
  }

  public UserDataDTO getUserDataDTOById(Long userId) {
    UserData userData = userDataRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User is not found"));
    return new UserDataDTO().setBalance(getMoneyInRubles(userData.getBalance()));
  }

  @Transactional
  public UserDataDTO transferMoney(Long senderId, Long receiverId, Double moneyInRubles) {
    var senderDataDTO = new UserDataDTO();
    var receiverDataDTO = new UserDataDTO();
    try {
      var senderData = getUserData(senderId);
      var receiverData = getUserData(receiverId);
      if (moneyInRubles <= getMoneyInRubles(senderData.getBalance())) {
        saveDecreasedBalance(senderId, moneyInRubles, senderData);
        senderDataDTO.setStatus(OperationStatus.SUCCESS);
      } else {
        senderDataDTO.setStatus(OperationStatus.NOT_ENOUGH_MONEY);
        receiverDataDTO.setStatus(OperationStatus.FAILED);
      }
      if (senderDataDTO.getStatus().equals(OperationStatus.SUCCESS)) {
        saveIncreasedBalance(receiverId, moneyInRubles, receiverData);
        receiverDataDTO.setStatus(OperationStatus.SUCCESS);
        var operation = new Operation.Builder(senderId)
            .setMoneyAmount(getMoneyInPennies(moneyInRubles))
            .setOperationType(OperationType.TRANSFER)
            .setReceiverId(receiverId)
            .build();
        operationService.saveOperation(operation);
      }
    } catch (ArithmeticException e) {
      receiverDataDTO.setStatus(OperationStatus.FAILED);
    } catch (RuntimeException e) {
      senderDataDTO.setStatus(OperationStatus.USER_NOT_FOUND);
    }
    return senderDataDTO;
  }
}
