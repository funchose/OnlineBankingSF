package org.study.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.study.banking.model.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
}
