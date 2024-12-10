package org.study.banking.service;

import com.google.common.math.LongMath;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.study.banking.model.UserData;
import org.study.banking.repository.UserDataRepository;

@Service
@RequiredArgsConstructor
public class UserDataService {
  private final UserDataRepository userDataRepository;

  public Double getBalance(Long userId) {
    Optional<UserData> userData = userDataRepository.findById(userId);
    return userData.map(data -> data.getBalance() / 100.0).orElse(-1.0);
  }

  public int takeMoney(Long userId, Double moneyAmount) {
    Optional<UserData> userData = userDataRepository.findById(userId);
    if (userData.isPresent()) {
      double moneyInCents = moneyAmount * 100;
      Long balance = userData.get().getBalance();
      if (balance >= moneyInCents) {
        UserData editedUserData = new UserData(userId);
        editedUserData.setBalance(balance - (long) moneyInCents);
        userDataRepository.save(editedUserData);
        return 1;
      } else return 0;
    } else return -1;
  }

  public int putMoney(Long userId, Double moneyAmount) {
    Optional<UserData> userData = userDataRepository.findById(userId);
    if (userData.isPresent()) {
      UserData editedUserData = new UserData(userId);
      double moneyInCents = moneyAmount * 100;
      try {
        editedUserData.setBalance(LongMath.checkedAdd(userData.get().getBalance(),
            (long) moneyInCents));
      } catch (ArithmeticException e) {
        return 2;
      }
      userDataRepository.save(editedUserData);
      return 1;
    } else return 0;
  }
}
