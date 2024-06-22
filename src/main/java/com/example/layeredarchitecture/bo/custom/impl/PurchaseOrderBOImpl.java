package com.example.layeredarchitecture.bo.custom.impl;


import com.example.layeredarchitecture.bo.custom.PurchaseOrderBO;
import com.example.layeredarchitecture.dao.DAOFactory;
import com.example.layeredarchitecture.dao.custom.CustomerDAO;
import com.example.layeredarchitecture.dao.custom.ItemDAO;
import com.example.layeredarchitecture.dao.custom.OrderDAO;
import com.example.layeredarchitecture.dao.custom.OrderDetailsDAO;
//import com.example.layeredarchitecture.dao.custom.impl.CustomerDAOImpl;
//import com.example.layeredarchitecture.dao.custom.impl.ItemDAOImpl;
//import com.example.layeredarchitecture.dao.custom.impl.OrderDAOImpl;
//import com.example.layeredarchitecture.dao.custom.impl.OrderDetailsDAOImpl;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.dto.CustomerDTO;
import com.example.layeredarchitecture.dto.ItemDTO;
import com.example.layeredarchitecture.dto.OrderDTO;
import com.example.layeredarchitecture.dto.OrderDetailDTO;
import com.example.layeredarchitecture.entity.Customer;
import com.example.layeredarchitecture.entity.Item;
import com.example.layeredarchitecture.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

//    ItemDAO itemDAO = new ItemDAOImpl();
//    CustomerDAO customerDAO = new CustomerDAOImpl();
//    OrderDAO orderDAO = new OrderDAOImpl();
//    OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAOImpl();

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ORDER);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ORDER_DETAIL);


    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
//        CustomerDAO customerDAO = new CustomerDAOImpl();
        Customer customer = customerDAO.search(id);
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress());
//        return customerDAO.search(id);
    }


    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
//        ItemDAO itemDAO = new ItemDAOImpl();
        Item item = itemDAO.search(code);
        return new ItemDTO(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand());
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
//        ItemDAO itemDAO = new ItemDAOImpl();
        return itemDAO.exist(code);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
//        CustomerDAO customerDAO = new CustomerDAOImpl();
        return customerDAO.exist(id);
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
//        OrderDAO orderDAO = new OrderDAOImpl();
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
//        CustomerDAO customerDAO = new CustomerDAOImpl();
//        return customerDAO.getAll();
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer c:customers){
            CustomerDTO customerDTO = new CustomerDTO(c.getId(), c.getName(), c.getAddress());
            customerDTOS.add(customerDTO);
        }
       return customerDTOS;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
//        ItemDAO itemDAO = new ItemDAOImpl();
//        return itemDAO.getAll();
        ArrayList<Item> items = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item i:items){
            ItemDTO itemDTO = new ItemDTO(i.getCode(), i.getDescription(), i.getUnitPrice(), i.getQtyOnHand());
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }


    @Override
    public boolean purchaseOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails){
        /*Transaction*/
        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            //Check order id already exist or not
//            OrderDAO orderDAO = new OrderDAOImpl();
            boolean b1 = orderDAO.exist(orderId);
            /*if order id already exist*/
            if (b1) {
                return false;
            }

            connection.setAutoCommit(false);
            //Save the Order to the order table
            boolean b2 = orderDAO.add(new Order(orderId, orderDate, customerId));

            if (!b2) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            // add data to the Order Details table
//            OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAOImpl();
            for (OrderDetailDTO detail : orderDetails) {
                boolean b3 = orderDetailsDAO.add(detail);
                if (!b3) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                //Search & Update Item
                ItemDTO item = findItem(detail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

                //update item
//                ItemDAO itemDAO = new ItemDAOImpl();
                boolean b = itemDAO.update(new Item(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));

                if (!b) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ItemDTO findItem(String code) {
        try {
            PurchaseOrderBOImpl purchaseOrderBO = new PurchaseOrderBOImpl();
            return purchaseOrderBO.searchItem(code);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
