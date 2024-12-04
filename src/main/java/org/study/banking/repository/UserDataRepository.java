package org.study.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.study.banking.model.UserData;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
}
