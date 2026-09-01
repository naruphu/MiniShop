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
</head>
<body>
<h1>Order Detail</h1>
<%
	Order order = (Order) request.getAttribute("order");
%>


<p>
Order ID:
<b><%= order.getId() %></b>
</p>


<p>
Status:
<b><%= order.getStatus() %></b>
</p>


<h2>Products</h2>


<%
	for(OrderItem item : order.getOrderItems()){
%>


<p>
	Product:
	<b><%= item.getProduct().getName() %></b>
</p>

<p>Date: <b><%= order.getOrderDate() %></b></p>

<p>
	Quantity:
	<b><%= item.getQuantity() %></b>
</p>


<p>
	Price:
	<b><fmt:formatNumber
		        value="<%= item.getPrice() %>"
		        type="number"
		    /> ₫</b>
</p>


<hr>


<%
}
%>


<h2>
	Total:
	<b><fmt:formatNumber
		        value="<%= order.getTotal() %>"
		        type="number"
		    /> ₫</b>
</h2>


</body>
</html>