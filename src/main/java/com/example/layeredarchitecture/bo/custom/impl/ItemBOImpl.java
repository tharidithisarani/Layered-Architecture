package com.example.layeredarchitecture.bo.custom.impl;

import com.example.layeredarchitecture.bo.custom.ItemBO;
import com.example.layeredarchitecture.dao.DAOFactory;
import com.example.layeredarchitecture.dao.custom.ItemDAO;
import com.example.layeredarchitecture.dto.ItemDTO;
import com.example.layeredarchitecture.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

//    ItemDAO itemDAO = new ItemDAOImpl();    property injectrion

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
//        ItemDAO itemDAO = new ItemDAOImpl();
        ArrayList<Item> items = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item i:items){
            ItemDTO itemDTO = new ItemDTO(i.getCode(), i.getDescription(), i.getUnitPrice(), i.getQtyOnHand());
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
//        ItemDAO itemDAO = new ItemDAOImpl();
        return itemDAO.delete(code);
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
//        ItemDAO itemDAO = new ItemDAOImpl();
        return itemDAO.add(new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
//        ItemDAO itemDAO = new ItemDAOImpl();
        return itemDAO.update(new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand()));
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
//        ItemDAO itemDAO = new ItemDAOImpl();
        return itemDAO.exist(code);
    }

    @Override
    public String generateNewCode() throws SQLException, ClassNotFoundException {
//        ItemDAO itemDAO = new ItemDAOImpl();
        return itemDAO.generateNewID();
    }

    /*@Override
    public ItemDTO search(String code) throws SQLException, ClassNotFoundException {
//        ItemDAO itemDAO = new ItemDAOImpl();
        return itemDAO.search(code);
    }*/
}
