package controller;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.genericdao.MatchArg;
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
			} 
			else {	
	            Fund[] funds = fundDAO.match(MatchArg.equals("symbol", form.getSymbol().toUpperCase()));
				if(funds.length == 0) {
	            fund.setName(form.getName().toUpperCase());
	            fund.setSymbol(form.getSymbol().toUpperCase());
	            fund.setInitial_value(Double.parseDouble(form.getInitial_value()));
	            fundDAO.create(fund);
				obj.addProperty("message", "The fund was successfully created");//this is not mentioned in the spec so not sure 
				//if this message should be given as a json object
				} else {
					obj.addProperty("message", "The input you provided is not valid");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		return obj.toString();	
	}
}