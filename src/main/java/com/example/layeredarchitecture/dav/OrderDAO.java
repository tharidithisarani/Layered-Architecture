package com.example.layeredarchitecture.dav;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public interface OrderDAO {
    boolean existOrderId(String orderId) throws SQLException, ClassNotFoundException ;
    boolean saveOrder(String orderId, LocalDate orderDate, String customerId) throws SQLException, ClassNotFoundException ;

//    public boolean  updateItem(String description, BigDecimal unitPrice, Integer qtyOnHand, String code) throws SQLException, ClassNotFoundException ;

    String generateNewOrderID() throws SQLException, ClassNotFoundException;

    void commit() throws SQLException, ClassNotFoundException;
}
