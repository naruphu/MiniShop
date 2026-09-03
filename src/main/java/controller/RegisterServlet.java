package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import dao.UserDAO;
import model.Role;
import model.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		
		if (!password.equals(confirmPassword)) {
		    response.getWriter().println("Passwords do not match");
		    return;
		}
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		String address = request.getParameter("address");
		
		UserDAO userDAO = new UserDAO();
		
		User existingEmail = userDAO.findByEmail(email);
		User existingUsername = userDAO.findByUsername(username);
		
		if(existingEmail != null) {
			response.getWriter().println("Email already exists");
			return;
		}
		
		if (existingUsername != null) {
		    response.getWriter().println("Username already exists");
		    return;
		}
		User user = new User();
		user.setUsername(username);
		user.setName(name);
		user.setEmail(email);
		user.setPassword(hashedPassword);
		user.setAddress(address);
		user.setRole(Role.CUSTOMER);
		
		userDAO.save(user);
		response.getWriter().println("Register successfully");
		
		
	}

}
