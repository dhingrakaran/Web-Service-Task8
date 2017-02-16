package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import databeans.Customer;
import model.Model;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Customer admin;
	Model model;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
    }
    
    public void init() throws ServletException {
        model = new Model(getServletConfig());
        
        admin = new Customer();
        admin.setFname("Jane");
        admin.setLname("Admin");
        admin.setUsername("jadmin");
        admin.encodePassword("admin");
        admin.setAddress("123 Main street");
        admin.setCity("Pittsburgh");
        admin.setState("Pa");
        admin.setZip("15143");
        
        Action.add(new Login(model, admin)); // do not send admin as parameter in other action classes
        Action.add(new TransitionDay(model));
        Action.add(new DepositCheck(model));
        Action.add(new CreateFund(model));
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = performTheAction(request);
        if(message != null) {
	        response.setContentType("application/json");
	    	response.getWriter().write(message);
        }
    }
    
    private String performTheAction(HttpServletRequest request) {
        String servletPath = request.getPathInfo();
        String action = getActionName(servletPath);
    	return Action.perform(action, request);
    }
    
    private String getActionName(String path) {
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }
}