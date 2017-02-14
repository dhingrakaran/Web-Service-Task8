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
		try {
			br = request.getReader();
			String line;
			Gson gson = new Gson();
			line = br.readLine();
			LoginForm form = gson.fromJson(line, LoginForm.class);
			System.out.println(form.getUsername() + " " + form.getPassword());
			if (form.hasErrors()) {
				obj.addProperty("message", "There seems to be an issue with the username/password combination that you entered");
			} else if(form.getUsername().equals(admin.getUsername())) {
				obj.addProperty("message", "Welcome " + admin.getFname());
			} else {
				Customer customer = customerDAO.read(form.getUsername());
				obj.addProperty("message", "Welcome " + customer.getFname());
			}
		} catch (IOException e) {
			obj.addProperty("message", "There seems to be an issue with the username/password combination that you entered");
		} catch (RollbackException e) {
			obj.addProperty("message", "There seems to be an issue with the username/password combination that you entered");
		} catch (NullPointerException e) {
			obj.addProperty("message", "There seems to be an issue with the username/password combination that you entered");
		}
		System.out.println(obj.toString());
		return obj.toString();
	}
}
