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
<title>My Cart</title>
</head>
<body>
<h1>My Cart</h1>
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
    



<%
	BigDecimal total = BigDecimal.ZERO;
	List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
	if(cartItems != null && !cartItems.isEmpty()) {
		for(CartItem item : cartItems){
			BigDecimal subtotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
			
			total = total.add(subtotal);
%>

	<h2>
		<%= item.getProduct().getName() %>
	</h2>
	
	<p class="price">
	    	<fmt:formatNumber
		        value="<%= item.getProduct().getPrice() %>"
		        type="number"
		    /> ₫
	</p>
	
	<p>
		Quantity:
		<%= item.getQuantity() %>
	</p>
	
	<p>
        Subtotal:
        <fmt:formatNumber
            value="<%= subtotal %>"
            type="number"
        /> ₫
    </p>
    
    <form action="CartServlet" method="post">

    <input type="hidden"
           name="action"
           value="update">

    <input type="hidden"
           name="cartItemId"
           value="<%= item.getId() %>">

    <input type="number"
           name="quantity"
           value="<%= item.getQuantity() %>"
           min="1">

    <button type="submit">
        Update
    </button>

	</form>
	
	<form action="CartServlet" method="post">
		<input type = "hidden" name = "action" value = "delete">
		<input type = "hidden" name = "cartItemId" value = "<%= item.getId()%>">
		<button type = "submit">Remove</button>
	</form>	
	<hr>

<%
		}
	}
	else {
%>
<h2>Your cart is empty</h2>

<%
}
%>

<h2>
    Total:
    <fmt:formatNumber
        value="<%= total %>"
        type="number"
    /> ₫
</h2>

<hr>
<%
if(cartItems != null && !cartItems.isEmpty()){
%>

<form action="CheckoutServlet" method="post">
    <button type="submit">Checkout</button>
</form>

<%
}
%>
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