package org.study.banking.utils;

import lombok.Getter;

@Getter
public enum OperationType {
  WITHDRAWAL(0),
  DEPOSIT(1),
  TRANSFER(2);
  private final int type;

  OperationType(int type) {
    this.type = type;
  }
}
