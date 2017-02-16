package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import databeans.Customer;
import databeans.Fund;
import formbeans.BuyFundForm;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;

public class BuyFund extends Action{
	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	
	public BuyFund(Model model) {
		customerDAO = model.getCustomerDAO();
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "buyFund";
	}
	
	public String perform (HttpServletRequest request) {
		JsonObject obj = new JsonObject();
		BufferedReader bReader;
		
		try {
			bReader = request.getReader();
			String line;
			Gson gson = new Gson();
			line = bReader.readLine();
			BuyFundForm form = gson.fromJson(line, BuyFundForm.class);
			System.out.println(form.getSymbol() + " " + form.getCashValue());
			if (form.hasErrors()) {
				obj.addProperty("message", "The input you provided is not valid");
			}
			
			//get Customer fund cash.
			//Assuming here we already have customer ID.
			String username = "team4";
			Customer customer = customerDAO.read(username);
			if (customer.getCash() < Integer.parseInt(form.getCashValue())) {
				obj.addProperty("message", "You don’t have enough cash in your account to make this purchase");
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
