package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import databeans.Customer;
import databeans.Fund;
import databeans.Position;
import formbeans.BuyFundForm;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.PositionDAO;

public class BuyFund extends Action{
	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	
	public BuyFund(Model model) {
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO();
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "buyFund";
	}
	
	public String perform (HttpServletRequest request) {
		JsonObject obj = new JsonObject();
		BufferedReader bReader;
		HttpSession session = request.getSession();
		
		// check customer == null, customer is not logged in
		if (session.getAttribute("customer") == null && session.getAttribute("employee") == null) {
            obj.addProperty("message", "You are not currently logged in");
            return obj.toString();
        }
		
		// if someone is there but not customer.
		if (session.getAttribute("customer") == null ) {
			obj.addProperty("message", "You must be a customer to perform this action");
			return obj.toString();
		}
		
		try {
			bReader = request.getReader();
			String line;
			Gson gson = new Gson();
			line = bReader.readLine();
			BuyFundForm form = gson.fromJson(line, BuyFundForm.class);

			if (form.hasErrors()) {
				obj.addProperty("message", "The input you provided is not valid");
				return obj.toString();
			}
			
			Transaction.begin();
			
			String username = (String) session.getAttribute("customer"); 
			//use CustomeDAO to get customer bean
			Customer customer = customerDAO.read(username);

			if (customer.getCash() < Double.parseDouble(form.getCashValue())) {
				obj.addProperty("message", "You don't have enough cash in your account to make this purchase");
				Transaction.commit();
				return obj.toString();
			}
			
			Fund fund = fundDAO.read(form.getSymbol());
			if (fund == null) {
				obj.addProperty("message", "The input you provided is not valid");
				Transaction.commit();
				return obj.toString();
			}
			
			// shares will always be integer so recalculate the shares
			int noofBuyableShares = (int) (Double.parseDouble(form.getCashValue()) / fund.getInitial_value());
			// what if customer is not providing enough money
			if (noofBuyableShares < 1) {
			
				obj.addProperty("message", "You didn't provide enough cash to make this purchase");
				Transaction.commit();
				return obj.toString();
			}
			
			double actualFund = fund.getInitial_value() * noofBuyableShares;
			double newCash = customer.getCash() - actualFund;
			customer.setCash(newCash);
			customerDAO.update(customer);
			
			Position[] position = positionDAO.match(MatchArg.and(MatchArg.equals("username", customer.getUsername()), MatchArg.equals("symbol", fund.getSymbol())));
			
			if (position.length == 0) { //this means customer has never bought such fund
				// create new position for this customer to this this fund
				Position newPosition = new Position();
				newPosition.setUsername(customer.getUsername());
				newPosition.setSymbol(form.getSymbol());
				newPosition.setShares(noofBuyableShares);
				positionDAO.create(newPosition);
			
			} else {
				position[0].setShares(position[0].getShares() + noofBuyableShares);
				positionDAO.update(position[0]);
			}
			
			obj.addProperty("message", "The fund has been successfully purchased");
			Transaction.commit();
			
		} catch (IOException e) {
			obj.addProperty("message", "The input you provided is not valid");
		} catch (RollbackException e) {
			obj.addProperty("message", "The input you provided is not valid");
		} catch (NullPointerException e) {
			obj.addProperty("message", "The input you provided is not valid");
		} finally {
            if (Transaction.isActive()) Transaction.rollback(); 
        }
		return obj.toString();
	}
}
