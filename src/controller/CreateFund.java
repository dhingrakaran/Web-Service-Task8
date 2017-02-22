package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.DuplicateKeyException;
import org.genericdao.RollbackException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import databeans.Fund;
import formbeans.CreateFundForm;
import model.FundDAO;
import model.Model;

public class CreateFund extends Action{
	private FundDAO fundDAO;
	
	public CreateFund(Model model) {
		fundDAO = model.getFundDAO();
	}
	@Override
	public String getName() {
		return "createFund";
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
			CreateFundForm form = gson.fromJson(line, CreateFundForm.class);
			if (form.hasErrors()) {
				obj.addProperty("message", "The input you provided is not valid");
			} else {
				Fund fund = new Fund();
	            fund.setName(form.getName());
	            fund.setSymbol(form.getSymbol());
	            fund.setInitial_value(Double.parseDouble(form.getInitial_value()));
	            Date dateobj = new Date(System.currentTimeMillis());
	            fund.setCreate_fund_date(dateobj);
	            fundDAO.create(fund);
				obj.addProperty("message", "The fund was successfully created");
			}
		} catch (IOException e) {
			obj.addProperty("message", "The input you provided is not valid");
		} catch(DuplicateKeyException e) {
			obj.addProperty("message", "The input you provided is not valid");
		} catch (RollbackException e) {
			obj.addProperty("message", "The input you provided is not valid");
		}
		return obj.toString();	
	}
}