package util;

import dao.CartItemDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;
import dao.ProductDAO;
import service.CartService;
import service.CheckoutService;
import service.OrderService;
import service.ProductService;


public class AppContext {

	// Product
    private static ProductService productService;


    static {

        ProductDAO productDAO = new ProductDAO();

        productService =
                new ProductService(productDAO);

    }


    public static ProductService getProductService(){

        return productService;

    }
    
    
    // Cart
    private static CartService cartService;


    static {

        CartItemDAO cartDAO = new CartItemDAO();

        cartService =
            new CartService(cartDAO);

    }


    public static CartService getCartService(){

        return cartService;

    }
    
    // Order
    private static OrderService orderService;


    static {

        OrderDAO orderDAO = new OrderDAO();

        orderService =
            new OrderService(orderDAO);

    }
    
    public static OrderService getOrderService(){

        return orderService;

    }
    
    // Check Out
    private static CheckoutService checkoutService;
    
    static {
    	CartItemDAO cartItemDAO = new CartItemDAO();
        OrderDAO orderDAO = new OrderDAO();
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        
        checkoutService = new CheckoutService(cartItemDAO, orderDAO, orderItemDAO);
    }
    
    public static CheckoutService getCheckOutService() {
    	return checkoutService;
    }


}