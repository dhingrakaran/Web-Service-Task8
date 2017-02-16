package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import databeans.Customer;
import databeans.Fund;
import formbeans.CreateFundForm;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;

public class CreateFund extends Action{
	private FundDAO fundDAO;
	private Customer admin;
	private CustomerDAO customerDAO;
	
	public CreateFund(Model model) {
		customerDAO = model.getCustomerDAO();
		fundDAO = model.getFundDAO();
	}
	@Override
	public String getName() {
		return "createFund";
	}

	public String perform (HttpServletRequest request) {
		JsonObject obj = new JsonObject();
		Fund fund = new Fund();
		BufferedReader br;
		String line;
		
		try {
			br = request.getReader();
			Gson gson = new Gson();
			line = br.readLine();
			CreateFundForm form = gson.fromJson(line, CreateFundForm.class);
			if (form.hasErrors()) {
				obj.addProperty("message", "The input you provided is not valid");
			}
			if(request.getSession().getAttribute("employee")== null && request.getSession().getAttribute("customer") == null){
				obj.addProperty("message", "You are not currently logged in");
				return obj.toString();
			}
			if(request.getSession().getAttribute("employee") == null) {
				obj.addProperty("message", "You must be an employee to perform this action");
				return obj.toString();
			} else {
	            fund.setName(form.getName().toUpperCase());
	            fund.setSymbol(form.getSymbol().toUpperCase());
	            fundDAO.create(fund);
				obj.addProperty("message", "The fund was successfully created");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		return obj.toString();	
	}
}