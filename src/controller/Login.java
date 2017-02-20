package controller;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.genericdao.RollbackException;
import formbeans.LoginForm;
import model.CustomerDAO;
import model.Model;
import com.google.gson.*;

import databeans.Customer;

public class Login extends Action{
	private CustomerDAO customerDAO;
	private Customer admin;
	
	public Login(Model model, Customer admin){
		customerDAO = model.getCustomerDAO();
		this.admin = admin;
	}
	
	public String getName() {
		return "login";
	}
	
	public String perform (HttpServletRequest request) {
		JsonObject obj = new JsonObject();
		BufferedReader br;
		String line;
		
		try {
			br = request.getReader();
			Gson gson = new Gson();
			line = br.readLine();
			LoginForm form = gson.fromJson(line, LoginForm.class);
			if (form.hasErrors()) {
				obj.addProperty("message", "There seems to be an issue with the username/password combination that you entered");
			} else if(form.getUsername().equals(admin.getUsername())) {
				if(admin.checkPassword(form.getPassword())) {
					obj.addProperty("message", "Welcome " + admin.getFname());
					request.getSession().setAttribute("employee", admin.getUsername());
				} else {
					obj.addProperty("message", "There seems to be an issue with the username/password combination that you entered");
				}
			} else {
				Customer customer = customerDAO.read(form.getUsername());
				if(customer == null) {
					obj.addProperty("message", "There seems to be an issue with the username/password combination that you entered");
				} else if(customer.checkPassword(form.getPassword())){
					request.getSession().setAttribute("customer", customer.getUsername());
					obj.addProperty("message", "Welcome " + customer.getFname());
				} else {
					obj.addProperty("message", "There seems to be an issue with the username/password combination that you entered");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		//System.out.println(obj.toString());
		return obj.toString();
		
	}
}
