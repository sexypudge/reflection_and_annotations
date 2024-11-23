package com.luongnv.practice.hibernate;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        TransactionHistory th = new TransactionHistory(1, "luongnv", "credit", 10000);
        th.setTransactionId(12345666);
        Hibernate<TransactionHistory> hibernate = Hibernate.getConnection();
        hibernate.write(th);
    }
}



