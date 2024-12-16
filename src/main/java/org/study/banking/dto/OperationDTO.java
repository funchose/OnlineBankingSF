package org.study.banking.dto;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class OperationDTO {
  private Long id;
  private Long userId;
  private int type;
  private Long moneyAmount;
  private ZonedDateTime date;
}
