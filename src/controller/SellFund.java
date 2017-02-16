package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
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
		
		try {
			bReader = request.getReader();
			String line;
			Gson gson = new Gson();
			line = bReader.readLine();
			SellFundForm form = gson.fromJson(line, SellFundForm.class);
			System.out.println(form.getSymbol() + " " + form.getNumShares());
			if (form.hasErrors()) {
				obj.addProperty("message", "The input you provided is not valid");
			}
			
			//get Customer fund cash.
			//Assuming here we already have customer ID.
			String username = "team4";
			Customer customer = customerDAO.read(username);
			
			//get number of shares owned by this customer.
			Fund fund = fundDAO.readSymbol(form.getSymbol());
			if (fund == null) {
				obj.addProperty("message", "The input you provided is not valid");
				return obj.toString();
			}
			
			Position[] position = positionDAO.match(MatchArg.and(MatchArg.equals("username", username), 
					MatchArg.equals("fund_id", fundDAO.readSymbol(form.getSymbol()))));
			if (position.length == 0 || position == null) {
				obj.addProperty("message", "The input you provided is not valid");
				return obj.toString();
			}
			
			if (position[0].getShares() < Double.parseDouble(form.getNumShares())) {
				obj.addProperty("message", "You don’t have enough cash in your account to make this purchase");
			}
			
			//we don't have transaction table now
			//subtract number of shares from position table and add money to customer account

//			TransactionBean tBean = new TransactionBean();
//			tBean.setUsername(username);
//			//have not set up function to check system time for transaction date.
//			tBean.setFund_id(fund.getFund_id());
//			tBean.setTransaction_type("sell");
//			tBean.setAmount(Double.parseDouble(form.getNumShares()));
//			transactionDAO.create(tBean);
//			
			obj.addProperty("message", "The fund has been successfully sold");
			
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

