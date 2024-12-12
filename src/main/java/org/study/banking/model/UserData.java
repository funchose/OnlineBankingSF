package org.study.banking.model;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "user_data")
@RequiredArgsConstructor
public class UserData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "balance")
  private Long balance;
  @OneToMany(mappedBy = "userData")
  private Set<Operation> operations;

  public UserData(Long userId) {
    this.id = userId;
  }

  public UserData(Long userId, Long balance) {
    this.id = userId;
    this.balance = balance;
  }
}
