package org.study.banking.model;

import jakarta.persistence.*;
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

  public UserData(Long userId) {
    this.id = userId;
  }
}
