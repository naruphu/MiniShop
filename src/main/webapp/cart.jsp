<%@page import="java.math.BigDecimal"%>
<%@page import="model.CartItem"%>
<%@page import="java.util.List"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

	<meta charset="UTF-8">
	<title>Cart</title>
	
	<jsp:include page="/WEB-INF/include/head.jsp"/>
	
</head>
<body>

<jsp:include page="/WEB-INF/include/navbar.jsp"/>

<!-- MESSAGE Ở ĐÂY -->
    <%
        String message =
            (String) session.getAttribute("message");

        if (message != null) {
    %>

        <p id="success-message">
        <%= message %>
    	</p>

    <%
            session.removeAttribute("message");
        }
    %>
    


	<div class="cart-container">
	
	<h1 class="cart-title">
	Your Shopping Cart
	</h1>
	
	
	<%
	
	BigDecimal total = BigDecimal.ZERO;
	
	List<CartItem> cartItems =
	(List<CartItem>) request.getAttribute("cartItems");
	
	
	if(cartItems != null && !cartItems.isEmpty()){
	
	
	for(CartItem item : cartItems){
	
	
	BigDecimal subtotal =
	item.getProduct()
	.getPrice()
	.multiply(
	BigDecimal.valueOf(item.getQuantity())
	);
	
	
	total = total.add(subtotal);
	
	%>
	
	
	
	<div class="cart-card">
	
	
	<img 
	src="${pageContext.request.contextPath}/image/<%=item.getProduct().getImageUrl()%>"
	class="cart-image">
	
	
	
	<div class="cart-info">
	
	
	<h2 class="cart-name">
	
	<%=item.getProduct().getName()%>
	
	</h2>
	
	
	
	<p class="cart-price">
	
	<fmt:formatNumber
	value="<%=item.getProduct().getPrice()%>"
	type="number"
	/> ₫
	
	</p>
	
	
	
	<p class="cart-quantity">
	
	Quantity:
	<%=item.getQuantity()%>
	
	</p>
	
	
	
	<p>
	
	Subtotal:
	
	<fmt:formatNumber
	value="<%=subtotal%>"
	type="number"
	/> ₫
	
	
	</p>
	
	
	<div class="cart-actions">
	
	
	<form action="CartServlet" method="post">
	
	
	<input type="hidden"
	name="action"
	value="update">
	
	
	<input type="hidden"
	name="cartItemId"
	value="<%=item.getId()%>">
	
	
	
	<input 
	class="quantity-input"
	type="number"
	name="quantity"
	value="<%=item.getQuantity()%>"
	min="1">
	
	
	<button class="update-btn">
	
	Update
	
	</button>
	
	
	</form>
	
	
	
	
	<form action="CartServlet" method="post">
	
	
	<input type="hidden"
	name="action"
	value="delete">
	
	
	<input type="hidden"
	name="cartItemId"
	value="<%=item.getId()%>">
	
	
	<button class="remove-btn">
	
	Remove
	
	</button>
	
	
	</form>
	
	
	</div>
	
	
	</div>
	
	
	</div>
	
	
	
	<%
	
	
	}
	
	}
	
	else {
	
	
	%>
	
	
	<div class="empty-cart">
	
	Your cart is empty
	
	</div>
	
	
	<%
	
	}
	
	%>
	
	
	
	<div class="cart-total">
	
	Total:
	
	<span>
	
	<fmt:formatNumber
	value="<%=total%>"
	type="number"
	/> ₫
	
	</span>
	
	
	</div>
	
	
	
	<%
	
	if(cartItems != null && !cartItems.isEmpty()){
	
	%>
	
	
	<form action="CheckoutServlet" method="post">
	
	
	<button class="checkout-btn">
	
	Checkout
	
	</button>
	
	
	</form>
	
	
	<%
	
	}
	
	%>
	
	
	</div>


   <script>
    setTimeout(function() {
        const message = document.getElementById("success-message");

        if (message) {
            message.remove();
        }
    }, 3000);
	</script>

</body>
</html>