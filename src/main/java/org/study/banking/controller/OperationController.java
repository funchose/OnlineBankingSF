package org.study.banking.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.study.banking.dto.OperationDTO;
import org.study.banking.request.GetOperationListRequest;
import org.study.banking.service.OperationService;

@RestController
@RequiredArgsConstructor
public class OperationController {
  private final OperationService operationService;

  @PostMapping(value = "/{userId}/operations", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<OperationDTO>> getOperationList(
      @PathVariable Long userId, @RequestBody GetOperationListRequest request) {
    return ResponseEntity.ok(operationService.getOperationList(userId,
        request.getFromDate(), request.getToDate()));
  }
}
