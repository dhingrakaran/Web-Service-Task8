package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.Fund;

public class FundDAO extends GenericDAO<Fund>{
    public FundDAO(String tableName, ConnectionPool connectionPool) throws DAOException {
        super(Fund.class, tableName, connectionPool);
    }
    
    public Fund readSymbol(String symbol) throws RollbackException {
    	Fund[] funds = match(MatchArg.equals("symbol", symbol.toUpperCase()));
    	if (funds.length == 0) {
    		throw new RollbackException("Fund with symbol: " + symbol.toUpperCase() + " doesn't exist");
    	}
    	return funds[0];
    }
}
