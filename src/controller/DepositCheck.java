package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import databeans.Customer;
import formbeans.DepositCheckForm;
import model.CustomerDAO;
import model.Model;

public class DepositCheck extends Action{
	private CustomerDAO customerDAO;
	
	public DepositCheck(Model model) {
		customerDAO = model.getCustomerDAO();
	}
	@Override
	public String getName() {
		return "depositCheck";
	}

	public String perform (HttpServletRequest request) {
		JsonObject obj = new JsonObject();
		BufferedReader br;
		String line;
		HttpSession session = request.getSession();
		
		if(session.getAttribute("employee") == null && session.getAttribute("customer") == null) {
			obj.addProperty("message", "You are not currently logged in");
			return obj.toString();
		}
		
		if(session.getAttribute("employee") == null) {
			obj.addProperty("message", "You must be an employee to perform this action");
			return obj.toString();
		}
		
		try {
			br = request.getReader();
			Gson gson = new Gson();
			line = br.readLine();
			DepositCheckForm form = gson.fromJson(line, DepositCheckForm.class);
			if (form.hasErrors()) {
				obj.addProperty("message", "The input you provided is not valid");
			} else {
				Transaction.begin();
				Customer customer = customerDAO.read(form.getUsername());
				if(customer != null) {
					customer.setCash(customer.getCash() + Double.parseDouble(form.getCash()));
					customerDAO.update(customer);
					obj.addProperty("message", "The check was successfully deposited");	
				}
				else {
					obj.addProperty("message", "The input you provided is not valid");
				}
				Transaction.commit();
			}
		} catch (IOException e) {
			obj.addProperty("message", "The input you provided is not valid");
		} catch (RollbackException e) {
			obj.addProperty("message", "The input you provided is not valid");
		} finally {
            if (Transaction.isActive()) Transaction.rollback(); 
        }
		return obj.toString();
	}
}	