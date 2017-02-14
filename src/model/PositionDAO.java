package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import databeans.Position;

public class PositionDAO extends GenericDAO<Position> {
	public PositionDAO(String tableName, ConnectionPool connectionPool) throws DAOException {
        super(Position.class, tableName, connectionPool);
    }
	
}
