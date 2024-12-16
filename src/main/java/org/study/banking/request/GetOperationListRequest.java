package org.study.banking.request;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetOperationListRequest {
  ZonedDateTime fromDate;
  ZonedDateTime toDate;
}
