package controller;


import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import databeans.Fund;
import databeans.Position;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.PositionDAO;

public class ViewPortfolio extends Action{
    private PositionDAO positionDAO;
    private FundDAO fundDAO;
    private CustomerDAO customerDAO;

    @Override
    public String getName() {
        return "viewPortfolio";
    }
    
    public ViewPortfolio(Model model) {
        positionDAO = model.getPositionDAO();
        fundDAO = model.getFundDAO();
        customerDAO = model.getCustomerDAO();
    }

    @Override
    public String perform(HttpServletRequest request) {
        JsonObject mainObj = new JsonObject();
        JsonArray jArray = new JsonArray();
        HttpSession session = request.getSession();
        if (session.getAttribute("employee") == null && session.getAttribute("customer") == null) {
            mainObj.addProperty("message", "You are not currently logged in");
            return mainObj.toString();
        }
        
        try {
            if (session.getAttribute("employee") != null && session.getAttribute("customer") == null) {
                mainObj.addProperty("message", "You must be a customer to perform this action");
                return mainObj.toString();
            }
            Position[] positions = positionDAO.match(MatchArg.equals("username", session.getAttribute("customer")));
            if (positions.length == 0) {
                mainObj.addProperty("message", "You don't have any funds in your Portfolio");
                return mainObj.toString();
            }
            for (Position p: positions) {
                JsonObject obj = new JsonObject();
                Fund fund = fundDAO.read(p.getSymbol());
                String fundName = fund.getName();
                double fundPrice = fund.getInitial_value();
                obj.addProperty("name", fundName);
                obj.addProperty("shares", p.getShares());
                obj.addProperty("price", fundPrice);
                jArray.add(obj);
            }
            double cash = customerDAO.read(session.getAttribute("customer")).getCash();
            DecimalFormat formatter = new DecimalFormat("#0.00");
            mainObj.addProperty("message", "The action was successful");
            mainObj.addProperty("cash", formatter.format(cash));
            mainObj.add("funds", jArray);
            
        } catch (RollbackException e) {
            e.printStackTrace(); 
        }
        
        return mainObj.toString();
    }

}
