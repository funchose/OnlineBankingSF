package org.study.banking.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.study.banking.model.UserData;
import org.study.banking.repository.UserDataRepository;

@Service
@RequiredArgsConstructor
public class UserDataService {
  private final UserDataRepository userDataRepository;

  public Long getBalance(Long userId) {
    Optional<UserData> userData = userDataRepository.findById(userId);
    if (userData.isPresent()) {
      return userData.get().getBalance();
    } else {
      return -1L;
    }
  }

  public Long takeMoney(Long userId, Long moneyAmount) {
    Optional<UserData> userData = userDataRepository.findById(userId);
    if (userData.isPresent()) {
      if (userData.get().getBalance() >= moneyAmount) {
        UserData editedUserData = new UserData(userId);
        editedUserData.setBalance(userData.get().getBalance() - moneyAmount);
        userDataRepository.save(editedUserData);
        return 1L;
      } else return 0L;
    } else return -1L;
  }

  public Long putMoney(Long userId, Long moneyAmount) {
    Optional<UserData> userData = userDataRepository.findById(userId);
    if (userData.isPresent()) {
      UserData editedUserData = new UserData(userId);
      editedUserData.setBalance(userData.get().getBalance() + moneyAmount);
      userDataRepository.save(editedUserData);
      return 1L;
    } else return 0L;
  }
}
