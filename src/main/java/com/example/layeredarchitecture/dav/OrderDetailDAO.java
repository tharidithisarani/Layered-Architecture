package com.example.layeredarchitecture.dav;

import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.SQLException;

public interface OrderDetailDAO {
    boolean saveOrderDetail(OrderDetailDTO detail, String orderId) throws SQLException, ClassNotFoundException;
}
