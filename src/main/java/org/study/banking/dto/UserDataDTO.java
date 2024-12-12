package org.study.banking.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.study.banking.utils.OperationStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDTO {
  private Double balance;

  private OperationStatus status;

  public UserDataDTO(Double balance) {
    this.balance = balance;
  }

  public UserDataDTO(OperationStatus status) {
    this.status = status;
  }
}
