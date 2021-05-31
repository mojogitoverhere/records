package com.david.records;

public class RecordsApplication {

  public static void main(String[] args) {
    Server server = new Server(new RecordsService());
    server.start(7000);
  }

}
