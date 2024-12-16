package org.study.banking.model;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.study.banking.utils.OperationType;


@Data
@Entity
@Table(name = "operations_list")
@RequiredArgsConstructor
@AllArgsConstructor
public class Operation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  Long id;
  @Column(name = "user_id")
  Long userId;
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "type")
  OperationType type;
  @Column(name = "money_amount")
  Long moneyAmount;
  @Column(name = "date")
  ZonedDateTime date;

  public static class Builder {
    private final Long userId;
    private OperationType type;
    private Long moneyAmount;

    public Builder(Long userId) {
      this.userId = userId;
    }

    public Builder setMoneyAmount(Long moneyAmount) {
      this.moneyAmount = moneyAmount;
      return this;
    }

    public Builder setOperationType(OperationType operationType) {
      this.type = operationType;
      return this;
    }

    public Operation build() {
      return new Operation(this);
    }
  }

  public Operation(Builder builder) {
    this.id = null;
    this.userId = builder.userId;
    this.type = builder.type;
    this.moneyAmount = builder.moneyAmount;
    this.date = ZonedDateTime.now();
  }
}
