
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.DuplicateKeyException;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import controller.Action;
import databeans.Customer;
import formbeans.CreateCustomerAccountForm;
import formbeans.LoginForm;
import model.CustomerDAO;
import model.Model;

public class CreateCustomerAccountAction extends Action {
    private CustomerDAO customerDAO;
    
    public CreateCustomerAccountAction(Model model) {
        customerDAO = model.getCustomerDAO();
    }
    
    @Override
    public String getName() {
        return "createCustomerAccount";
    }

    @Override
    public String perform(HttpServletRequest request) {
        JsonObject obj = new JsonObject();
        BufferedReader br;
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        CreateCustomerAccountForm form = null;
        HttpSession session = request.getSession();
        try {
            br = request.getReader();
            String line;
            line = br.readLine();
            Gson gson = new Gson();
            form = gson.fromJson(line, CreateCustomerAccountForm.class);
            if (session.getAttribute("employee") == null && session.getAttribute("customer") == null) {
                obj.addProperty("message", "You are not currently logged in");
                return obj.toString();
            }
            if (session.getAttribute("employee") == null && session.getAttribute("customer") != null) {
                obj.addProperty("message", "You must be an employee to perform this action");
            }
            if (form.hasErrors()) {
                obj.addProperty("message", "The input you provided is not valid");
                return obj.toString();
            }
            
            Customer customer= new Customer();
            customer.setUsername(form.getUsername());
            customer.setFname(form.getFname());
            customer.setLname(form.getLname());
            customer.setAddress(form.getAddress());
            customer.setCity(form.getCity());
            customer.setState(form.getState());
            customer.setZip(form.getZip());
            customer.setEmail(form.getEmail());
            customer.setCash(form.getCashAsDouble());
            customer.encodePassword(form.getPassword());
            customerDAO.create(customer);
            obj.addProperty("message", "fname was registered successfully");
            
            
        } catch(DuplicateKeyException e) {
            obj.addProperty("message", "The input you provided is not valid");
        } catch (RollbackException e) {
            obj.addProperty("message", "The input you provided is not valid");
        } catch (IOException e) {
            obj.addProperty("message", "The input you provided is not valid");
        }
        
        return obj.toString();
    }
}
