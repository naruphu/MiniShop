<%@page import="model.Order"%>
<%@page import="java.util.List"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

	<jsp:include page="/WEB-INF/include/head.jsp"/>
	
	<meta charset="UTF-8">
	<title>My Orders</title>
	
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp"/>
	
	<div class="order-container">
	
	
	<h1>My Orders</h1>
	
	
	<%
	
	List<Order> orders = 
	(List<Order>) request.getAttribute("orders");
	
	
	if(orders == null || orders.isEmpty()){
	
	%>
	
	
	<h2 class="empty">
	You don't have any orders yet
	</h2>
	
	
	<%
	
	}else{
	
	
	for(Order order : orders){
	
	%>
	
	
	
	<div class="order-card">
	
	
	<div class="order-header">
	
	
	<h2>
	Order #<%= order.getId()%>
	</h2>
	
	
	<span class="status">
	<%= order.getStatus()%>
	</span>
	
	
	</div>
	
	
	
	<p>
	Date:
	<b>
	<%= order.getOrderDate()%>
	</b>
	</p>
	
	
	
	<p class="total">
	
	
	Total:
	
	<b>
	
	<fmt:formatNumber
	value="<%= order.getTotal()%>"
	type="number"
	/>
	
	₫
	
	</b>
	
	
	</p>
	
	
	
	
	<form action="OrderDetailServlet" method="get">
	
	
	<input 
	type="hidden"
	name="orderId"
	value="<%= order.getId()%>"
	>
	
	
	
	<button class="detail-btn">
	
	View Detail
	
	</button>
	
	
	
	</form>
	
	
	
	</div>
	
	
	
	
	<%
	
	}
	
	}
	
	%>
	
	
	</div>


</body>
</html>