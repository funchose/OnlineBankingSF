package org.study.banking.request;

import lombok.Data;

@Data
public class TransferMoneyRequest {
  private Long receiverId;
  private Double moneyAmount;
}
