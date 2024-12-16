package org.study.banking.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.study.banking.dto.OperationDTO;
import org.study.banking.model.Operation;
import org.study.banking.repository.OperationRepository;

@Service
@RequiredArgsConstructor
public class OperationService {
  private final OperationRepository operationRepository;

  public void saveOperation(Operation operation) {
    operationRepository.save(operation);
  }

  public List<OperationDTO> getOperationList(Long userId,
                                             ZonedDateTime fromDate,
                                             ZonedDateTime toDate) {
    List<Operation> operationList = operationRepository.findAll();
    var resultList = filterOperationsByUserIdAndDate(userId, fromDate, toDate, operationList);
    var operationDTOS = convertToDTOS(resultList);
    return operationDTOS;
  }

  private static List<Operation> filterOperationsByUserIdAndDate(Long userId,
                                                                 ZonedDateTime fromDate,
                                                                 ZonedDateTime toDate,
                                                                 List<Operation> operationList) {
    if (fromDate == null && toDate == null) {
      return operationList.stream()
          .filter(operation -> operation.getUserId().equals(userId)).toList();
    } else if (fromDate == null) {
      return operationList.stream()
          .filter(operation -> operation.getUserId().equals(userId))
          .filter(operation -> operation.getDate().compareTo(toDate) <= 0)
          .toList();
    } else if (toDate == null) {
      return operationList.stream()
          .filter(operation -> operation.getUserId().equals(userId))
          .filter(operation -> operation.getDate().compareTo(fromDate) >= 0)
          .toList();
    } else {
      return operationList.stream()
          .filter(operation -> operation.getUserId().equals(userId))
          .filter(operation -> operation.getDate().compareTo(fromDate) >= 0)
          .filter(operation -> operation.getDate().compareTo(toDate) <= 0).toList();
    }
  }

  private static List<OperationDTO> convertToDTOS(List<Operation> resultList) {
    List<OperationDTO> operationDTOS = new ArrayList<>();
    for (Operation operation : resultList) {
      var operationDTO = new OperationDTO();
      operationDTO.setId(operation.getId());
      operationDTO.setType(operation.getType().getType());
      operationDTO.setMoneyAmount(operation.getMoneyAmount());
      operationDTO.setDate(operation.getDate());
      operationDTO.setUserId(operation.getUserId());
      operationDTOS.add(operationDTO);
    }
    return operationDTOS;
  }
}
