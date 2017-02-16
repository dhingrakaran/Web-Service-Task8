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
		
		try {
			bReader = request.getReader();
			String line;
			Gson gson = new Gson();
			line = bReader.readLine();
			BuyFundForm form = gson.fromJson(line, BuyFundForm.class);
			System.out.println(form.getSymbol() + " " + form.getCashValue());
			if (session.getAttribute("customer") == null && session.getAttribute("customer") == null) {
                obj.addProperty("message", "You are not currently logged in");
                return obj.toString();
            }
			if (form.hasErrors()) {
				obj.addProperty("message", "The input you provided is not valid");
			}
			
			//get Customer fund cash.
			//Assuming here we already have customer ID.
			Customer customer = (Customer) session.getAttribute("customer");
			String username = customer.getUsername();
			
			//I need to check if customer exists.

			if (customer.getCash() < Integer.parseInt(form.getCashValue())) {
				obj.addProperty("message", "You don’t have enough cash in your account to make this purchase");
				return obj.toString();
			}
			
			Fund fund = fundDAO.readSymbol(form.getSymbol());
			if (fund == null) {
				obj.addProperty("message", "The input you provided is not valid");
				return obj.toString();
			}
			
			// we don't have transcation table now, so this is not required
			// subtract cash value from customer cash and and funds to fund table
//			TransactionBean tBean = new TransactionBean();
//			tBean.setUsername(username);
//			//have not set up function to check system time for transaction date.
//			tBean.setFund_id(fund.getFund_id());
//			tBean.setTransaction_type("buy");
//			tBean.setAmount(Double.parseDouble(form.getCashValue()));
//			transactionDAO.create(tBean);
			
			Position[] position = positionDAO.match(MatchArg.and(MatchArg.equals("username", customer.getUsername()), MatchArg.equals("symbol", fund.getSymbol())));
			if (position.length == 0 || position== null) {
				obj.addProperty("message", "The input you provided is not valid");
				return obj.toString();
			}
			
			double newCash = customer.getCash() - Double.parseDouble(form.getCashValue());
			customerDAO.updateCash(customer.getUsername(), newCash);
			double newShare = position[0].getShares() + Double.parseDouble(form.getCashValue()) / fund.getInitial_value();
			positionDAO.updateShares(username, newShare);
			
			obj.addProperty("message", "The fund has been successfully purchased");
			
		} catch (IOException e) {
			obj.addProperty("message", "The input you provided is not valid");
		} catch (RollbackException e) {
			obj.addProperty("message", "The input you provided is not valid");
		} catch (NullPointerException e) {
			obj.addProperty("message", "The input you provided is not valid");
		}
		System.out.println(obj.toString());
		return obj.toString();
	}
}
