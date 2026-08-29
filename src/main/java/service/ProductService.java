package service;

import java.math.BigDecimal;
import java.util.List;

import dao.ProductDAO;
import model.Product;

public class ProductService {
	private ProductDAO productDAO = new ProductDAO();
	
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
	        throw new IllegalArgumentException("Product not found");
	    }
		productDAO.delete(product);
	}
	
	private void validate(Product product) {
		if(product.getName() == null || product.getName().trim().isEmpty()) {
			throw new IllegalArgumentException("Product name can not be empty");
		}
		if(product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Product price must be greater than 0");
		}
		if(product.getQuantity() < 0) {
			throw new IllegalArgumentException("Quantity can not be negative");
		}
		if(product.getCategory() == null) {
			throw new IllegalArgumentException("Category is required");
		}
	}
	
	public List<Product> searchProducts(String keyword){
		if(keyword == null || keyword.trim().isEmpty()) {
			return productDAO.findAll();
		}
		
		return productDAO.searchByName(keyword);
	}
	
}
