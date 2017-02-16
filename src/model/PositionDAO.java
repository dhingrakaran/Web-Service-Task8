package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.Position;

public class PositionDAO extends GenericDAO<Position> {
	public PositionDAO(String tableName, ConnectionPool connectionPool) throws DAOException {
        super(Position.class, tableName, connectionPool);
    }
	
	public Position updateShares(String username, double shares) throws RollbackException {
	    try {
	        Transaction.begin();
	        Position position = read(username);
	        
	        if (position == null) {
	            throw new RollbackException("Customer '" + username + " doesn'town this fund");
	        }
	        
	        position.setShares(shares);
	        update(position);
	        Transaction.commit();
	        
	        return position;
	    } finally{
	        if (Transaction.isActive()) Transaction.rollback();
	    }
	}
}
