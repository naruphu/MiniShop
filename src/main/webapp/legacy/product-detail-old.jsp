<%@page import="model.Role"%>
<%@page import="model.User"%>
<%@page import="model.Product"%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product Detail</title>
</head>
<body>
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
		Product product = (Product) request.getAttribute("product");
	%>
	<h1>Product Detail</h1>
	<p>ID: <%= product.getId() %></p>
	<p>Name: <%= product.getName()%></p>
	<p>
	    Price:
	    <fmt:formatNumber
	        value="<%= product.getPrice() %>"
	        type="number"
	    /> ₫
	</p>
	<p>
		Category: <%= product.getCategory().getName() %>
	</p>
	<p>Quantity: <%= product.getQuantity()%></p>
	<%
	User loggedInUser = (User) session.getAttribute("loggedInUser");
	%>
	<!-- Chỉ CUSTOMER -->
		    <%
		        if (loggedInUser != null
		            && loggedInUser.getRole() == Role.CUSTOMER) {
		    %>
		
		        <form action="CartServlet" method="post">

				    <input type="hidden"
				           name="productId"
				           value="<%= product.getId() %>">
				
				    <button type="submit">
				        Add to Cart
				    </button>
				
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