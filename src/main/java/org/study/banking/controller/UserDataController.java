package org.study.banking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.study.banking.dto.UserDataDTO;
import org.study.banking.request.DepositOrWithdrawRequest;
import org.study.banking.request.TransferMoneyRequest;
import org.study.banking.service.UserDataService;

@RestController
@RequiredArgsConstructor
@Tag(name = "User's operations")
public class UserDataController {
  private final UserDataService userDataService;

  @Operation(summary = "Get balance by user ID",
      description = "Returns a user's balance in rubles")
  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Double> getBalance(@PathVariable Long userId) {
    var userDataDTO = userDataService.getBalance(userId);
    switch (userDataDTO.getStatus()) {
      case SUCCESS -> {
        return ResponseEntity.ok().body(userDataDTO.getBalance());
      }
      case USER_NOT_FOUND -> {
        return ResponseEntity.notFound().build();
      }
    }
    return ResponseEntity.badRequest().build();
  }

  @Operation(summary = "Take money by user ID",
      description = "Withdraws amount of money in rubles from a user's balance")
  @PutMapping(value = "/{userId}/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<UserDataDTO> takeMoney(@PathVariable Long userId,
                                               @RequestBody DepositOrWithdrawRequest request) {
    UserDataDTO userDataDTO = userDataService.takeMoney(userId, request.getMoneyAmount());
    switch (userDataDTO.getStatus()) {
      case SUCCESS -> {
        return ResponseEntity.ok().build();
      }
      case NOT_ENOUGH_MONEY -> {
        return ResponseEntity.unprocessableEntity().build();
      }
      case USER_NOT_FOUND -> {
        return ResponseEntity.notFound().build();
      }
    }
    return ResponseEntity.badRequest().build();
  }

  @Operation(summary = "Put money by user ID",
      description = "Deposits amount of money in rubles to a user's balance")
  @PutMapping(value = "/{userId}/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> putMoney(@PathVariable Long userId,
                                         @RequestBody DepositOrWithdrawRequest request) {
    var userDataDTO = userDataService.putMoney(userId, request.getMoneyAmount());
    switch (userDataDTO.getStatus()) {
      case SUCCESS -> {
        return ResponseEntity.ok().build();
      }
      case USER_NOT_FOUND -> {
        return ResponseEntity.notFound().build();
      }
      case FAILED -> {
        return ResponseEntity.unprocessableEntity().build();
      }
    }
    return ResponseEntity.badRequest().build();
  }

  @Operation(summary = "Transfer money",
      description = "Transfers money from one user to another")
  @PutMapping(value = "/{userId}/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDataDTO> transferMoney(
      @PathVariable Long userId, @RequestBody TransferMoneyRequest request) {
    if (userId.equals(request.getReceiverId())) {
      return ResponseEntity.unprocessableEntity().build();
    } else {
      var userDataDTO = userDataService.transferMoney(userId,
          request.getReceiverId(), request.getMoneyAmount());
      switch (userDataDTO.getStatus()) {
        case SUCCESS -> {
          return ResponseEntity.ok().build();
        }
        case USER_NOT_FOUND -> {
          return ResponseEntity.notFound().build();
        }
        case NOT_ENOUGH_MONEY -> {
          return ResponseEntity.unprocessableEntity().build();
        }
      }
      return ResponseEntity.badRequest().build();
    }
  }
}
