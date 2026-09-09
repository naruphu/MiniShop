package service;

import java.math.BigDecimal;
import java.util.List;

import dao.ProductDAO;
import exception.ProductException;
import model.Product;

public class ProductService {
	private ProductDAO productDAO;
	
	public ProductService(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	public void updateProduct(Product product) {
		validate(product);
		productDAO.update(product);
	}
	
	public void createProduct(Product product) {
		validate(product);
        productDAO.save(product);
    }
	
	public void deleteProduct(int id) {
		Product product = productDAO.selectById(id);
		if(product == null){
	        throw new ProductException("Product not found");
	    }
		productDAO.delete(product);
	}
	
	public List<Product> getProductsForAdmin(String keyword, Integer categoryId, int offset, int limit){

	    return productDAO.findProductsForAdmin(keyword, categoryId, offset, limit);

	}
	
	public List<Product> getProductsForCustomer(String keyword, Integer categoryId, int offset, int limit){
		return productDAO.findActiveProducts(keyword, categoryId, offset, limit);
	}
	
	public Product getProductForCustomer(int id){

	    Product p = productDAO.selectById(id);

	    if(p == null || 
	    	!"ACTIVE".equals(p.getStatus())){
	        throw new ProductException("Product not available");
	    }

	    return p;
	}
	
	
	public void updateStatus(int id,String status){

	    productDAO.updateStatus(id,status);

	}
	
	private void validate(Product product) {
		if(product.getName() == null || product.getName().trim().isEmpty()) {
			throw new ProductException("Product name can not be empty");
		}
		if(product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new ProductException("Product price must be greater than 0");
		}
		if(product.getQuantity() < 0) {
			throw new ProductException("Quantity can not be negative");
		}
		if(product.getCategory() == null) {
			throw new ProductException("Category is required");
		}
	}
	
	public List<Product> searchProducts(String keyword, Integer id,  int page){
		int pageSize = 8;
		int offset = (page - 1) * pageSize;
				
		return productDAO.findActiveProducts(keyword, id, offset, pageSize);
	}
	
	public int getTotalPages(String keyword, Integer categoryId){

	    int pageSize = 8;


	    long totalProducts =
	            productDAO.countActiveProducts(keyword, categoryId);


	    return (int)Math.ceil(
	            (double) totalProducts / pageSize
	    );

	}
	
	public Product getProductById(int id){
		return productDAO.selectById(id);

	}

	
}
