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

public class RequestCheck extends Action {
	private CustomerDAO customerDAO;
	
	public RequestCheck(Model model) {
		customerDAO = model.getCustomerDAO();
	}
	
	public String getName() {
		return "requestCheck";
	}
	
	public String perform(HttpServletRequest request) {
		JsonObject obj = new JsonObject();
		BufferedReader br;
		
		try {
			String admin = (String)request.getSession().getAttribute("employee");
			String c_username = (String)request.getSession().getAttribute("customer"); //get username from authentication
			if (admin == null && c_username == null) {
				obj.addProperty("message", "You are not currently logged in");
				return obj.toString();
			} 
			if (c_username == null && admin != null) {
				obj.addProperty("message", "You must be a customer to perform this action");
				return obj.toString();
			}
			Customer[] customers = customerDAO.match(MatchArg.equals("username", c_username));
			//if (customers.length == 0) obj.addProperty("message", "You must be a customer to perform this action");
			Customer customer = customers[0];
			double balance = customer.getCash();
			br = request.getReader();
			String line = br.readLine();
			Gson gson = new Gson();
			RequestCheckForm form = gson.fromJson(line, RequestCheckForm.class);
			
			//just compare the form value with customer cash value and if the customer has enough cash then subtract
			if (form.hasErrors()) {
				obj.addProperty("message", "The input you provided is not valid");
				return obj.toString();
			} else if (form.getAmountAsDouble() > balance) {
				obj.addProperty("message", "You don't have sufficient funds in your account to cover the requested check");
				return obj.toString();
			} else {
				customer.setCash(balance - form.getAmountAsDouble());
				customerDAO.update(customer);
                obj.addProperty("message", "The check has been successfully requested");
                return obj.toString();
			}
		} catch (IOException e) {
			obj.addProperty("message", "The input you provided is not valid");
		} catch (RollbackException e) {
			obj.addProperty("message", "The input you provided is not valid");
		} catch (NullPointerException e) {
			obj.addProperty("message", "The input you provided is not valid");
		}
		return obj.toString();
	}
}
