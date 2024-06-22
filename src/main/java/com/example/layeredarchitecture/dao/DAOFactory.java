package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.dao.custom.Impl.*;

public class DAOFactory {
    //singleton
    private static DAOFactory daoFactory;
    public DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        if (daoFactory == null){
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }


    //enum
    public enum DAOType{
        CUSTOMER, ITEM, ORDER, ORDER_DETAIL, QUERY
    }

    //method(enum)
    public SuperDAO getDAO(DAOType daoType){
        switch (daoType){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailsDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }

    //CustomerDAImpl,......
}
