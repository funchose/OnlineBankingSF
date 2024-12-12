package org.study.banking.utils;

import lombok.Getter;

@Getter
public enum OperationType {
  BALANCE_CHECK(1),
  WITHDRAWAL(2),
  DEPOSIT(3),
  TRANSFER(4);

  private final int type;

  OperationType(int type) {
    this.type = type;
  }
}
