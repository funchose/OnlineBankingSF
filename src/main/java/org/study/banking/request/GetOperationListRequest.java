package org.study.banking.request;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class GetOperationListRequest {
  ZonedDateTime fromDate;
  ZonedDateTime toDate;
}
