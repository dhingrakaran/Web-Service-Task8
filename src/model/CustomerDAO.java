 package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;


import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.Customer;

public class CustomerDAO extends GenericDAO<Customer>{
	public CustomerDAO(String tableName, ConnectionPool connectionPool) throws DAOException {
		super(Customer.class, tableName, connectionPool);
	}
	

	
	public Customer updateCash(String username, double amount) throws RollbackException {
	    try {
	        Transaction.begin();
	        Customer customer = read(username);
	        
	        if (customer == null) {
	            throw new RollbackException("Customer '" + username + "' no longer exists");
	        }
	        
	        customer.setCash(amount);
	        update(customer);
	        Transaction.commit();
	        
	        return customer;
	    } finally{
	        if (Transaction.isActive()) Transaction.rollback();
	    }
	}

}
