package org.study.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.study.banking.service.UserDataService;

@SpringBootApplication
public class BankingApplication {

  public static void main(String[] args) {
    SpringApplication.run(BankingApplication.class, args);

//    Demo for hometask:

//    ApplicationContext applicationContext = SpringApplication.run(BankingApplication.class, args);
//    UserDataService userDataService = applicationContext.getBean(UserDataService.class);
//    System.out.println(userDataService.getBalance(1L));
//    System.out.println(userDataService.putMoney(1L, 250L));
//    System.out.println(userDataService.takeMoney(1L, 50L));
//    System.out.println(userDataService.getBalance(1L));
  }
}
