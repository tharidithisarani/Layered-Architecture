package com.example.layeredarchitecture.dav;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public interface CustomerDAO {
    public ArrayList<CustomerDTO> loadAllCustomer() throws SQLException, ClassNotFoundException;

    public void saveCustomer(String id, String name, String address) throws SQLException, ClassNotFoundException;

    public void updateCustomer(String id, String name, String address) throws SQLException, ClassNotFoundException;

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

    public void deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public ResultSet loadAllCustomerIDs() throws SQLException, ClassNotFoundException;

    public ResultSet lodeAllItemCode() throws SQLException, ClassNotFoundException;

    public CustomerDTO searchCustomer(String newValue) throws SQLException, ClassNotFoundException ;
}
