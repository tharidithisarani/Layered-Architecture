package com.example.layeredarchitecture.bo.impl;

import com.example.layeredarchitecture.bo.custom.OrderBO;
import com.example.layeredarchitecture.dao.custom.OrderDAO;
import com.example.layeredarchitecture.dao.Impl.OrderDAOImpl;
import com.example.layeredarchitecture.model.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
//        OrderDAO orderDAO = new OrderDAOImpl();
        return orderDAO.generateNewID();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
//        OrderDAO orderDAO = new OrderDAOImpl();
        return orderDAO.delete(id);
    }

    @Override
    public OrderDTO search(String id) throws SQLException, ClassNotFoundException {
//        OrderDAO orderDAO = new OrderDAOImpl();
        return orderDAO.search(id);
    }

    @Override
    public boolean exist(String orderId) throws SQLException, ClassNotFoundException {
//        OrderDAO orderDAO = new OrderDAOImpl();
        return orderDAO.exist(orderId);
    }

    @Override
    public ArrayList<OrderDTO> getAll() throws SQLException, ClassNotFoundException {
//        OrderDAO orderDAO = new OrderDAOImpl();
        return orderDAO.getAll();
    }

    @Override
    public boolean add(OrderDTO dto) throws SQLException, ClassNotFoundException {
//        OrderDAO orderDAO = new OrderDAOImpl();
        return orderDAO.add(dto);
    }

    @Override
    public boolean update(OrderDTO dto) throws SQLException, ClassNotFoundException {
//        OrderDAO orderDAO = new OrderDAOImpl();
        return orderDAO.update(dto);
    }

}