package org.study.banking.model;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.study.banking.utils.OperationType;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "operations_list")
public class Operation {
  @Id
  @Column(name = "id")
  Long id;
  @ManyToOne
  @JoinColumn(name = "user_id")
  UserData userData;
  @Column(name = "operation_type")
  OperationType type;
  @Column(name = "money_amount")
  Long moneyAmount;
  @Column(name = "date")
  ZonedDateTime date;
}
