package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

public class Model {
    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;
    private FundDAO fundDAO;
    private Fund_Price_HistoryDAO fundPriceHistoryDAO;
    private PositionDAO positionDAO;

    public Model(ServletConfig config) throws ServletException {
        try {
            String jdbcDriver = config.getInitParameter("jdbcDriverName");
            String jdbcURL    = config.getInitParameter("jdbcURL");
            
            ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);
            customerDAO = new CustomerDAO("Customer", pool);
            transactionDAO = new TransactionDAO("Transaction",pool);
            fundDAO = new FundDAO("Fund", pool);
            positionDAO = new PositionDAO("Position", pool);
            fundPriceHistoryDAO = new Fund_Price_HistoryDAO("Fund_Price_History", pool);
        } catch (DAOException e) {
            throw new ServletException(e);
        }
    }
    
    public CustomerDAO getCustomerDAO() {
     	return customerDAO;
    }
    public TransactionDAO getTransactionDAO() {
        return transactionDAO;
    }
    public FundDAO getFundDAO() {
        return fundDAO;
    }
    public Fund_Price_HistoryDAO getFundPriceHistoryDAO() {
		return fundPriceHistoryDAO;
	}
	public PositionDAO getPositionDAO() {
		return positionDAO;
	}
}