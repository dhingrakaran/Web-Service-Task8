package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import databeans.Customer;
import databeans.Fund;
import databeans.Position;
import formbeans.SellFundForm;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.PositionDAO;

public class SellFund extends Action{
	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	
	public SellFund(Model model) {
		customerDAO = model.getCustomerDAO();
		fundDAO = model.getFundDAO();
		positionDAO = model.getPositionDAO();
	}
	
	public String getName() {
		return "sellFund";
	}
	
	public String perform (HttpServletRequest request) {
		JsonObject obj = new JsonObject();
		BufferedReader bReader;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("customer") == null && session.getAttribute("employee") == null) {
            obj.addProperty("message", "You are not currently logged in");
            System.out.println("not own fund: " + obj.toString());
            return obj.toString();
        }
		//if someone is there but not customer.
		if (session.getAttribute("customer") == null) {
			obj.addProperty("message", "You must be a customer to perform this action");
			System.out.println("not own fund: " + obj.toString());
			return obj.toString();
		}
		
		try {
			bReader = request.getReader();
			String line;
			Gson gson = new Gson();
			line = bReader.readLine();
			SellFundForm form = gson.fromJson(line, SellFundForm.class);

			if (form.hasErrors()) {
				obj.addProperty("message", "The input you provided is not valid");
				System.out.println("form errors: " + obj.toString());
				return obj.toString();
				
			}
			
			String username = (String) session.getAttribute("customer");  
			//use CustomeDAO to get customer bean
			Customer customer = customerDAO.read(username);
			
			Position[] position = positionDAO.match(MatchArg.and(MatchArg.equals("username", username), MatchArg.equals("symbol", form.getSymbol())));
			if (position.length == 0) {
				// customer doesn't own this fund.
				
				obj.addProperty("message", "The input you provided is not valid");
				System.out.println("not own fund: " + obj.toString());
				return obj.toString();
			}
			
			int noofSellableFund = Integer.parseInt(form.getNumShares());
			
			if (position[0].getShares() < noofSellableFund) {
				System.out.println("do not have enough shares");
				obj.addProperty("message", "You don't have that many shares in your portfolio");
				System.out.println(obj.toString());
				return obj.toString();
			}
			
			Fund fund = fundDAO.read(form.getSymbol());
			double newCash = customer.getCash() + noofSellableFund * fund.getInitial_value();
			customer.setCash(newCash);
			customerDAO.update(customer); 
			
			if (noofSellableFund == position[0].getShares()) {
				System.out.println("sell all shares");
				positionDAO.delete(position[0].getUsername(), position[0].getSymbol());
			} else {
				int newShare = position[0].getShares() - noofSellableFund;
				position[0].setShares(newShare);
				positionDAO.update(position[0]);
			}
			
			obj.addProperty("message", "The fund has been successfully sold");
			
		} catch (IOException e) {
			obj.addProperty("message", "IOE: The input you provided is not valid");
		} catch (RollbackException e) {
			obj.addProperty("message", "RollBack: The input you provided is not valid");
		}
		System.out.println(obj.toString());
		return obj.toString();
	}
}

