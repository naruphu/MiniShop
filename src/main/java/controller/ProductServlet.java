package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.hibernate.Session;

import dao.CategoryDAO;
import dao.ProductDAO;
import exception.AppException;
import exception.ProductException;
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
@MultipartConfig
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
			

		    List<Category> categories = categoryDAO.findAll();

		    request.setAttribute("categories", categories);

		    request.getRequestDispatcher("/index.jsp")
		           .forward(request, response);
		}
		else if (idPr != null) {

			int id = Integer.parseInt(idPr);

		    Product product;


		    if(RoleUtils.isAdmin(loggedInUser)){
		        product = productService.getProductById(id);
		    }
		    else{
		        product = productService.getProductForCustomer(id);
		    }


		    request.setAttribute("product", product);

		    request.getRequestDispatcher("/product-detail.jsp")
		           .forward(request,response);

		}
		else {

		    String keyword = request.getParameter("keyword");
		    String pageParam = request.getParameter("page");
		    String categoryParam = request.getParameter("categoryId");

		    Integer categoryId = null;


		    if(categoryParam != null && !categoryParam.trim().isEmpty()) {
		        categoryId = Integer.parseInt(categoryParam);
		    }


		    if(keyword == null) {
		        keyword = "";
		    }


		    int page = 1;

		    if(pageParam != null) {
		        page = Integer.parseInt(pageParam);
		    }
		    
		    int totalPages = productService.getTotalPages(keyword, categoryId);



		    List<Product> products;


		    boolean isAdmin = RoleUtils.isAdmin(loggedInUser);


		    if(isAdmin) {

		        // ADMIN
		    	int pageSize = 8;
		        int offset = (page - 1) * pageSize;
		        products = productService.getProductsForAdmin(
		                keyword,
		                categoryId,
		                offset,
		                pageSize
		        );

		    } else {

		        // CUSTOMER
		        int pageSize = 8;
		        int offset = (page - 1) * pageSize;


		        products = productService.getProductsForCustomer(
		                keyword,
		                categoryId,
		                offset,
		                pageSize
		        );
		    }


		    request.setAttribute("products", products);
		    request.setAttribute("keyword", keyword);
		    request.setAttribute("categoryId", categoryId);
		    request.setAttribute("totalPages", totalPages);
		    request.setAttribute("currentPage", page);


		    List<Category> categories = categoryDAO.findAll();

		    request.setAttribute("categories", categories);


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
		String idPr = request.getParameter("id");
		HttpSession session = request.getSession();
		
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser"); 
		
		if("update".equals(action)) {
			
			if(!RoleUtils.isAdmin(loggedInUser)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
			int id = Integer.parseInt(request.getParameter("id"));

		    String name = request.getParameter("name");

			BigDecimal price = new BigDecimal(request.getParameter("price"));

			int quantity = Integer.parseInt(request.getParameter("quantity"));
			
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			
			Category category = categoryDAO.selectById(categoryId);

			Product product = productService.getProductById(id);

			product.setName(name);
			product.setPrice(price);
			product.setQuantity(quantity);
			product.setCategory(category);
			
			productService.updateProduct(product);
			session.setAttribute("message", "Update successfully!");
			response.sendRedirect("ProductServlet");
		    return;
			
		
			
		}
		else if ("status".equals(action)) {
				
				if(!RoleUtils.isAdmin(loggedInUser)) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					return;
				}
				int id = Integer.parseInt(idPr);
				String status = request.getParameter("status");
				Product product = productService.getProductById(id);
				if(product == null){
				    throw new ProductException("Product not found");
				}
				
				String pageParam = request.getParameter("page");
				int page = 1;
				
				if(pageParam != null) page = Integer.parseInt(pageParam);
				

				productService.updateStatus(id, status);
				session.setAttribute("message", "Save status successfully!");
				response.sendRedirect( "ProductServlet?page=" + page);
			    return;
	    }
		else {
			if(!RoleUtils.isAdmin(loggedInUser)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
			String name = request.getParameter("name");
			if(name != null){
			    name = name.trim();
			}
			BigDecimal price = new BigDecimal(request.getParameter("price"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			
			// image upload
			Part imagePart = request.getPart("image");
			String fileName = imagePart.getSubmittedFileName();
			String uploadPath = getServletContext().getRealPath("/image");
			imagePart.write(uploadPath + "/" + fileName);
			
			Category category = categoryDAO.selectById(categoryId);
			
			Product product = new Product(name, quantity, price);
			product.setCategory(category);
			product.setImageUrl(fileName);
			product.setStatus("ACTIVE");

			
			productService.createProduct(product);
			session.setAttribute("message", "Add a new product successfully!");
			response.sendRedirect( "ProductServlet");
		    return;
			
		}
	}

}
