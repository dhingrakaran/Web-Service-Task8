package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import databeans.Customer;
import formbeans.RequestCheckForm;
import model.CustomerDAO;
import model.Model;

public class RequestCheck {
	private CustomerDAO customerDAO;
	
	public RequestCheck(Model model) {
		customerDAO = model.getCustomerDAO();
	}
	
	public String getName() {
		return "RequestCheck";
	}
	
	public String Perform(HttpServletRequest request) {
		JsonObject obj = new JsonObject();
		BufferedReader br;
		
		try {
			String username = "";
			if (username == null) {
				obj.addProperty("message", "You are not currently logged in");
			}
			Customer[] customers = customerDAO.match(MatchArg.equals("username", username));
			if (customers.length == 0) obj.addProperty("message", "You must be a customer to perform this action");
			Customer customer = customers[0];
			
			//we don't have pending transactions now so this is not required.
//			double balance = customer.getCash();
//			TransactionBean[] transactions = transactionDAO.match(MatchArg.and(MatchArg.equals("username", username), MatchArg.equals("execute_date", null)));
//            for(int i = 0; i < transactions.length; ++i) {
//                if(transactions[i].getTransaction_type().toLowerCase().contains("deposit")) {
//                    balance += transactions[i].getAmount();
//                } else if(transactions[i].getTransaction_type().toLowerCase().contains("buy")) {
//                    balance -= transactions[i].getAmount();
//                } else if(transactions[i].getTransaction_type().toLowerCase().contains("request")) {
//                    balance -= transactions[i].getAmount();
//                }
//            }
            
			br = request.getReader();
			String line = br.readLine();
			Gson gson = new Gson();
			RequestCheckForm form = gson.fromJson(line, RequestCheckForm.class);
			
			//just compare the form value with customer cash value and if the customer has enough cash then subtract
			
			//System.out.println();
//			if (form.hasErrors()) {
//				obj.addProperty("message", "The input you provided is not valid");
//			} else if (form.getAmountAsDouble() > balance) {
//				obj.addProperty("message", "You don't have sufficient funds in your account to cover the requested check");
//			} else {
//				TransactionBean transaction = new TransactionBean();
//				transaction.setUsername(username);
//				transaction.setFund_id(0);
//				transaction.setExecute_date(null);
//				transaction.setShares(0);
//                transaction.setTransaction_type("request");
//                transaction.setAmount(form.getAmountAsDouble());
//                transactionDAO.create(transaction);
//                obj.addProperty("message", "The check has been successfully requested");
//			}
		} catch (IOException e) {
			obj.addProperty("message", "The input you provided is not valid");
		} catch (RollbackException e) {
			obj.addProperty("message", "The input you provided is not valid");
		} catch (NullPointerException e) {
			//obj.addProperty("message", "The input you provided is not valid");
		}
		return obj.toString();
	}
}
