<%@page import="model.OrderItem"%>
<%@page import="model.Order"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

	<jsp:include page="/WEB-INF/include/head.jsp"/>
	<meta charset="UTF-8">
	<title>Admin Order Detail</title>
	
</head>
<body>

	<jsp:include page="/WEB-INF/include/navbar.jsp"/>
	
	
	<%
	
	Order order = (Order) request.getAttribute("order");
	
	%>
	
	
	<div class="order-detail-container">
	
	
	<div class="order-detail-card">
	
	
	<h1>
	Order #<%= order.getId() %>
	</h1>
	
	
	<div class="order-status">
	
	<p>
	Customer:
	
	<b>
	<%= order.getUser().getUsername() %>
	</b>
	
	</p>
	
	
	<p>
	Date:
	
	<%= order.getOrderDate() %>
	
	</p>
	
	
	<p>
	Status:
	
	<span>
	<%= order.getStatus() %>
	</span>
	
	</p>
	
	</div>
	
	
	
	<h2>
	Products
	</h2>
	
	
	
	<%
	
	for(OrderItem item : order.getOrderItems()){
	
	%>
	
	
	
	<div class="order-product">
	
	
	<div>
	
	
	<h3>
	<%= item.getProduct().getName() %>
	</h3>
	
	
	<p>
	Quantity:
	
	<%= item.getQuantity() %>
	
	</p>
	
	
	</div>
	
	
	
	<div class="product-price">
	
	
	<fmt:formatNumber
	    value="<%= item.getPrice() %>"
	    type="number"
	/>
	
	₫
	
	
	</div>
	
	
	
	</div>
	
	
	
	<%
	
	}
	
	%>
	
	
	
	
	<div class="order-total">
	
	
	Total:
	
	<strong>
	
	<fmt:formatNumber
	    value="<%= order.getTotal() %>"
	    type="number"
	/>
	
	₫
	
	
	</strong>
	
	
	</div>
	
	
	
	<br>
	
	
	<a href="AdminOrderServlet"
	   class="detail-btn">
	
	Back
	
	</a>
	
	
	
	</div>
	
	
	</div>

</body>
</html>