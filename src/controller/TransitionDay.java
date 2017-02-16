package controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import com.google.gson.JsonObject;

import databeans.Fund;
import model.FundDAO;
import model.Model;

public class TransitionDay extends Action {
	private FundDAO fundDAO;
	
	public TransitionDay(Model model) {
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "transitionDay";
	}

	public String perform(HttpServletRequest request) {
		JsonObject obj = new JsonObject();
		Random random = new Random();
		
		if(request.getSession().getAttribute("employee") == null && request.getSession().getAttribute("customer") == null) {
			obj.addProperty("message", "You are not currently logged in");
			return obj.toString();
		}
		
		if(request.getSession().getAttribute("employee") == null) {
			obj.addProperty("message", "You must be an employee to perform this action");
			return obj.toString();
		}
		
		try {
			Transaction.begin();
			
			Fund funds[] = fundDAO.match();
			for(int i = 0; i < funds.length; ++i) {
				int randomValue = random.nextInt(21) - 10;
				double change = 100.0 + randomValue; 
				change /= 100;
				double newValue = funds[i].getInitial_value() * change;
				double roundOff = Math.round(newValue * 100) / 100.0;
				if(roundOff == 0) {
					++roundOff;
				}
				funds[i].setInitial_value(roundOff);
				fundDAO.update(funds[i]);
			}
			obj.addProperty("message", "The fund prices have been successfully recalculated");
			
			Transaction.commit();
		} catch (RollbackException e) {
			e.printStackTrace();
		} finally {
            if (Transaction.isActive()) Transaction.rollback(); 
        }
		
		return obj.toString();
	}

}
