package org.study.banking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.study.banking.service.UserDataService;

@RestController
@RequiredArgsConstructor
public class UserDataController {
  private final UserDataService userDataService;
  @GetMapping(value = "/{userId}")
  public Long getBalance(@PathVariable Long userId) {
    return userDataService.getBalance(userId);
  }

  @PutMapping(value = "/{userId}/withdraw")
  public Long takeMoney(@PathVariable Long userId, @RequestBody Long moneyAmount) {
    return userDataService.takeMoney(userId, moneyAmount);
  }

  @PutMapping(value="/{userId}/deposit")
  public Long putMoney(@PathVariable Long userId, @RequestBody Long moneyAmount) {
    return userDataService.putMoney(userId, moneyAmount);
  }
}
