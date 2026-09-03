package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDAO;
import dao.ProductDAO;
import exception.AppException;
import model.Category;
import model.Product;
import model.Role;
import model.User;
import service.ProductService;
import util.AppContext;
import util.RoleUtils;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;
	private CategoryDAO categoryDAO = new CategoryDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
        productService = AppContext.getProductService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

		String idPr = request.getParameter("id");
		String action = request.getParameter("action");
		
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser"); 
		
		if ("edit".equals(action)) {
			
			if(!RoleUtils.isAdmin(loggedInUser)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

		    int id = Integer.parseInt(idPr);

		    Product product = productService.getProductById(id);
		    
		    CategoryDAO categoryDAO = new CategoryDAO();
		    List<Category> categories = categoryDAO.findAll();

		    request.setAttribute("product", product);
		    request.setAttribute("categories", categories);

		    request.getRequestDispatcher("/edit-product.jsp")
		           .forward(request, response);
		
		}
		else if ("create".equals(action)) {
			
			if(!RoleUtils.isAdmin(loggedInUser)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
		    CategoryDAO categoryDAO = new CategoryDAO();

		    List<Category> categories = categoryDAO.findAll();

		    request.setAttribute("categories", categories);

		    request.getRequestDispatcher("/index.jsp")
		           .forward(request, response);
		}
		else if (idPr != null) {

		    int id = Integer.parseInt(idPr);

		    Product product = productService.getProductById(id);

		    request.setAttribute("product", product);

		    request.getRequestDispatcher("/product-detail.jsp")
		           .forward(request, response);

		}
		else {
			String keyword = request.getParameter("keyword");
			String pageParam = request.getParameter("page");
			String categoryParam =
			        request.getParameter("categoryId");
			Integer categoryId = null;

			if (categoryParam != null &&
			    !categoryParam.trim().isEmpty()) {

			    categoryId =
			        Integer.parseInt(categoryParam);
			}
			
			if(keyword == null) {
				keyword = "";
			}
			
			int page = 1;
			
			if(pageParam != null) page = Integer.parseInt(pageParam);
			
			List<Product> products = productService.searchProducts(keyword, categoryId, page);
			
			
			int totalPages = productService.getTotalPages(keyword, categoryId);
			List<Category> categories = categoryDAO.findAll();

			request.setAttribute("categories", categories);

			request.setAttribute("totalPages", totalPages);

		    request.setAttribute("products", products);
			request.setAttribute("currentPage", page);
			request.setAttribute("keyword", keyword);
			request.setAttribute("categoryId", categoryId);
			
		    request.getRequestDispatcher("/products.jsp")
		           .forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

		String action = request.getParameter("action");
		ProductDAO productDAO = new ProductDAO();
		String idPr = request.getParameter("id");
		
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser"); 
		
		if("update".equals(action)) {
			
			if(!RoleUtils.isAdmin(loggedInUser)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
			try {
				int id = Integer.parseInt(request.getParameter("id"));

			    String name = request.getParameter("name");

				BigDecimal price = new BigDecimal(request.getParameter("price"));

				int quantity = Integer.parseInt(request.getParameter("quantity"));
				
				int categoryId = Integer.parseInt(request.getParameter("categoryId"));
				
				CategoryDAO categoryDAO = new CategoryDAO();
				
				Category category = categoryDAO.selectById(categoryId);

				Product product = productDAO.selectById(id);

				product.setName(name);
				product.setPrice(price);
				product.setQuantity(quantity);
				product.setCategory(category);
				
				productService.updateProduct(product);
			} catch (AppException e) {
				request.getSession().setAttribute("message", e.getMessage());
			}
			
			response.sendRedirect("ProductServlet");
		    return;
			
		
			
		}
		else if ("delete".equals(action)) {
			
			if(!RoleUtils.isAdmin(loggedInUser)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
			try {
				int id = Integer.parseInt(idPr);
				productService.deleteProduct(id);
				
			} catch (AppException e) {
				request.getSession().setAttribute("message", e.getMessage());
			}
		    response.sendRedirect("ProductServlet");
		    return;
	    }
		else {
			if(!RoleUtils.isAdmin(loggedInUser)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			try {
				String name = request.getParameter("name").trim();
				BigDecimal price = new BigDecimal(request.getParameter("price"));
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				
				int categoryId = Integer.parseInt(request.getParameter("categoryId"));
				CategoryDAO categoryDAO = new CategoryDAO();
				Category category = categoryDAO.selectById(categoryId);
				
				Product product = new Product(name, quantity, price);
				product.setCategory(category);
				
				productService.createProduct(product);
			} catch (AppException e) {
				request.getSession().setAttribute("message", e.getMessage());
			}
			
			response.sendRedirect("ProductServlet");
		    return;
		}
	}

}
