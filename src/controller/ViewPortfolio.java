package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import databeans.CustomerAcc;
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
        JsonObject obj = new JsonObject();
        HttpSession session = request.getSession();
        List<CustomerAcc> list = new ArrayList<CustomerAcc>();
        
        try {
            Gson gson = new Gson();
            if (session.getAttribute("employee") == null && session.getAttribute("customer") == null) {
                obj.addProperty("message", "You are not currently logged in");
                return obj.toString();
            }
            if (session.getAttribute("employee") != null && session.getAttribute("customer") == null) {
                obj.addProperty("message", "You must be a customer to perform this action");
                return obj.toString();
            }
            Position[] positions = positionDAO.match(MatchArg.equals("username", session.getAttribute("customer")));
            if (positions.length == 0) {
                obj.addProperty("message", "You don¡¯t have any funds in your Portfolio");
                return obj.toString();
            }
            for (Position p: positions) {
                CustomerAcc customerAcc = new CustomerAcc();
                String fundName = fundDAO.read(p.getSymbol()).getName();
                double fundPrice = fundDAO.read(p.getSymbol()).getInitial_value();
                customerAcc.setName(fundName);
                customerAcc.setShares(p.getShares());
                customerAcc.setPrice(fundPrice);
                list.add(customerAcc);
            }
            double cash = customerDAO.read(session.getAttribute("customer")).getCash();
            obj.addProperty("cash", cash);
            obj.add("funds", gson.toJsonTree(list));
            System.out.println(obj.toString());
            return obj.toString();
            
        } catch (RollbackException e) {
            e.printStackTrace();
            return obj.toString(); 
        }
        
    }

}
