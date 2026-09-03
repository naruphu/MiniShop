package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.OrderDAO;
import model.Order;
import model.User;
import util.RoleUtils;

/**
 * Servlet implementation class OrderDetailServlet
 */
@WebServlet("/OrderDetailServlet")
public class OrderDetailServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		OrderDAO orderDAO = new OrderDAO();
		
		User user = (User) session.getAttribute("loggedInUser");
		
		if(!RoleUtils.isLoggedIn(user)) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		String id = request.getParameter("orderId");
		int orderId = Integer.parseInt(id);
		
		Order order = orderDAO.selectById(orderId);
		
		if(RoleUtils.isCustomer(user)) {
			if(order.getUser().getId() != user.getId()) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
			return;
		}
		
		request.setAttribute("order", order);
		request.getRequestDispatcher("order-detail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
