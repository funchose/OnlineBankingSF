package org.study.banking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.study.banking.service.UserDataService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Banking API")
public class UserDataController {
  private final UserDataService userDataService;

  @Operation(summary = "Get balance by user ID",
      description = "Returns a user's balance in rubles")
  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Double> getBalance(@PathVariable Long userId) {
    Double response = userDataService.getBalance(userId);
    if (response == -1.0) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok().body(response);
    }
  }

  @Operation(summary = "Take money by user ID",
      description = "Withdraws amount of money in rubles from a user's balance")
  @PutMapping(value = "/{userId}/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<String> takeMoney(@PathVariable Long userId,
                                          @RequestBody Double moneyAmount) {
    int response = userDataService.takeMoney(userId, moneyAmount);
    if (response == 1) {
      return ResponseEntity.ok().build();
    } else if (response == 0) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(summary = "Put money by user ID",
      description = "Deposits amount of money in rubles to a user's balance")
  @PutMapping(value = "/{userId}/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> putMoney(@PathVariable Long userId,
                                         @RequestBody Double moneyAmount) {
    int response = userDataService.putMoney(userId, moneyAmount);
    if (response == 1) {
      return ResponseEntity.ok().build();
    } else if (response == 0) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }
  }
}
