<%@page import="model.OrderItem"%>
<%@page import="model.Order"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	
	<meta charset="UTF-8">
	<title>Order Detail</title>
	
	<jsp:include page="/WEB-INF/include/head.jsp"/>
	
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp"/>

	<div class="order-detail-container">
	
	
	<%
	
	Order order = (Order) request.getAttribute("order");
	
	%>
	
	
	<div class="order-detail-card">
	
	
	<h1>
	Order #<%= order.getId() %>
	</h1>
	
	
	<p class="order-status">
	
	Status:
	
	<span>
	<%= order.getStatus() %>
	</span>
	
	</p>
	
	
	<p>
	Date:
	
	<b>
	<%= order.getOrderDate() %>
	</b>
	
	</p>
	
	
	
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
	
	<b>
	<%= item.getQuantity() %>
	</b>
	
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
	
	<a href="OrderServlet"
	   class="detail-btn">
	
	Back
	
	</a>
	
	
	</div>
	
	
	</div>
	


</body>
</html>