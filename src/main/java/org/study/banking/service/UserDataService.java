package org.study.banking.service;

import com.google.common.math.LongMath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.study.banking.dto.UserDataDTO;
import org.study.banking.model.UserData;
import org.study.banking.repository.UserDataRepository;
import org.study.banking.utils.OperationStatus;

@Service
@RequiredArgsConstructor
public class UserDataService {
  private final UserDataRepository userDataRepository;

  public UserDataDTO getUserDataDTOById(Long userId) {
    UserData userData = userDataRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User is not found"));
    UserDataDTO userDataDTO = new UserDataDTO();
    userDataDTO.setBalance(userData.getBalance() / 100.0);
    return userDataDTO;
  }

  public Double getBalance(Long userId) {
    return getUserDataDTOById(userId).getBalance();
  }

  public UserDataDTO takeMoney(Long userId, Double moneyAmount) {
    var userDataDTO = new UserDataDTO();
    try {
      UserData userData = userDataRepository.findById(userId)
          .orElseThrow(() -> new RuntimeException("User not found"));
      if (moneyAmount <= userData.getBalance() / 100.0) {
        long newBalance = (long) (userData.getBalance() - moneyAmount);
        userDataRepository.save(new UserData(userId, newBalance));
        userDataDTO.setBalance(newBalance / 100.0);
        userDataDTO.setStatus(OperationStatus.SUCCESS);
      } else {
        userDataDTO.setStatus(OperationStatus.NOT_ENOUGH_MONEY);
      }
    } catch (RuntimeException e) {
      userDataDTO.setStatus(OperationStatus.USER_NOT_FOUND);
    }
    return userDataDTO;
  }

  public UserDataDTO putMoney(Long userId, Double moneyAmount) {
    var userDataDTO = new UserDataDTO();
    try {
      UserData userData = userDataRepository.findById(userId)
          .orElseThrow(() -> new RuntimeException("User not found"));
      userDataRepository.save(new UserData(userId,
          LongMath.checkedAdd(userData.getBalance(), (long) (moneyAmount * 100))));
      userDataDTO.setStatus(OperationStatus.SUCCESS);
    } catch (ArithmeticException e) {
      userDataDTO.setStatus(OperationStatus.FAILED);
    } catch (RuntimeException e) {
      userDataDTO.setStatus(OperationStatus.USER_NOT_FOUND);
    }
    return userDataDTO;
  }
}
