package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CartItemDAO;
import dao.ProductDAO;
import model.CartItem;
import model.Product;
import model.Role;
import model.User;
import service.CartService;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CartService cartService = new CartService();
	private CartItemDAO cartItemDAO = new CartItemDAO();
	private ProductDAO productDAO = new ProductDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		
		if(loggedInUser == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		CartItemDAO cartItemDAO = new CartItemDAO();
		List<CartItem> cartItems = cartItemDAO.findByUser(loggedInUser.getId());
		
		request.setAttribute("cartItems", cartItems);
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser"); 
		// getAttribute() trả về kiểu Object, trong khi biến loggedInUser của bạn là kiểu User.
		
		if(loggedInUser == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		String action = request.getParameter("action");
		// if(action.equals("update")) là sai vì nếu ko truyền vô action thì nó là NULL
		// mà null đi so với cái chuỗi thì suy ra nullpointer, phải làm ngược lại
		// ví dụ update so với null thì nó chỉ trả false thôi
		
		if("update".equals(action)) {
			if(loggedInUser == null || loggedInUser.getRole() != Role.CUSTOMER) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
			try {
				int id = Integer.parseInt(request.getParameter("cartItemId"));
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				
				cartService.updateQuantity(id, quantity);
				
				request.getSession().setAttribute("message", "Update to cart successfully!");
				
			} catch (IllegalArgumentException | IllegalStateException e) {
				request.getSession().setAttribute("message", e.getMessage());
			}
			response.sendRedirect("CartServlet");
			return;
			
		}else if("delete".equals(action)) {
			if(loggedInUser == null || loggedInUser.getRole() != Role.CUSTOMER) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
			try {
				int id = Integer.parseInt(request.getParameter("cartItemId"));
				cartService.removeItem(id);
				request.getSession().setAttribute("message", "Delete successfully!");
				
			} catch (IllegalArgumentException e) {
				request.getSession().setAttribute("message", e.getMessage());
			}
			response.sendRedirect("CartServlet");
			return;
			
		}else if("add".equals(action)) {
			if(loggedInUser == null || loggedInUser.getRole() != Role.CUSTOMER) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			try {
				int productId = Integer.parseInt(request.getParameter("productId"));
				Product product = productDAO.selectById(productId);
				
				if(product == null) throw new IllegalArgumentException("Product not found");
				
				cartService.addToCart(loggedInUser, product, 1);
				
				request.getSession().setAttribute("message", "Add to cart successfully");
				
			} catch (IllegalArgumentException | IllegalStateException e) {
				request.getSession().setAttribute("message", e.getMessage());
			}
			response.sendRedirect("ProductServlet");
			return;
		}else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid cart action");
		}
		
		
		
	}

}
