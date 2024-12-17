package org.study.banking.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.study.banking.utils.OperationStatus;

@Data
@NoArgsConstructor
public class UserDataDTO {
  private Double balance;

  private OperationStatus status;

  public UserDataDTO(Double balance) {
    this.balance = balance;
  }

  public UserDataDTO(OperationStatus status) {
    this.status = status;
  }

  public UserDataDTO setBalance(Double balance) {
    this.balance = balance;
    return this;
  }

  public UserDataDTO setStatus(OperationStatus status) {
    this.status = status;
    return this;
  }
}
