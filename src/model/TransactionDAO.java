package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import databeans.TransactionBean;

public class TransactionDAO extends GenericDAO<TransactionBean>{
    public TransactionDAO(String tableName, ConnectionPool connectionPool) throws DAOException {
        super(TransactionBean.class, tableName, connectionPool);
    }
}
