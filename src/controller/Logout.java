package controller;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonObject;

public class Logout extends Action {
	
	public String getName() {
		return "logout";
	}

	public String perform(HttpServletRequest request) {
		JsonObject obj = new JsonObject();
		
		if(request.getSession().getAttribute("employee")== null && request.getSession().getAttribute("customer") == null){
			obj.addProperty("message", "You are not currently logged in");
		} else {
			request.getSession().setAttribute("employee", null);
			request.getSession().setAttribute("customer", null);
			obj.addProperty("message", "You have been successfully logged out");
		}
		
		return obj.toString();
	}

}
