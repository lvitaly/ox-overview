package com.lvitaly.loom.service;

public interface Service {

  default String findUser() throws InterruptedException {
    Thread.sleep(500);
    System.out.println("<< findUser");
//    throw new RuntimeException();
    return "Bob";
  }

  default int fetchOrder() throws InterruptedException {
    Thread.sleep(1000);
    System.out.println("<< fetchOrder");
    return 10;
  }

}
