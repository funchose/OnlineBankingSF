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
        long updatedBalance = userData.getBalance() - getMoneyInPennies(moneyInRubles);
        var updatedUserData = new UserData(userId, updatedBalance);
        userDataRepository.save(updatedUserData);
        var operation = new Operation.Builder(userData.getId())
            .setMoneyAmount(getMoneyInPennies(moneyInRubles))
            .setOperationType(OperationType.WITHDRAWAL)
            .build();
        operationService.saveOperation(operation);
        userDataDTO.setBalance(getMoneyInRubles(updatedBalance));
        userDataDTO.setStatus(OperationStatus.SUCCESS);
      } else {
        userDataDTO.setStatus(OperationStatus.NOT_ENOUGH_MONEY);
      }
    } catch (RuntimeException e) {
      userDataDTO.setStatus(OperationStatus.USER_NOT_FOUND);
    }
    return userDataDTO;
  }

  @Transactional
  public UserDataDTO putMoney(Long userId, Double moneyInRubles) {
    var userDataDTO = new UserDataDTO();
    try {
      var userData = getUserData(userId);
      var updatedBalance = LongMath.checkedAdd(userData.getBalance(),
                                               getMoneyInPennies(moneyInRubles));
      var updatedUserData = new UserData(userId, updatedBalance);
      userDataRepository.save(updatedUserData);
      var operation = new Operation.Builder(userData.getId())
          .setMoneyAmount(getMoneyInPennies(moneyInRubles))
          .setOperationType(OperationType.DEPOSIT)
          .build();
      operationService.saveOperation(operation);
      userDataDTO.setBalance(getMoneyInRubles(updatedBalance));
      userDataDTO.setStatus(OperationStatus.SUCCESS);
    } catch (ArithmeticException e) {
      userDataDTO.setStatus(OperationStatus.FAILED);
    } catch (RuntimeException e) {
      userDataDTO.setStatus(OperationStatus.USER_NOT_FOUND);
    }
    return userDataDTO;
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
}
