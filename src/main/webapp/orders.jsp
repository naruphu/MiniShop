<%@page import="model.Order"%>
<%@page import="java.util.List"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Orders</title>
</head>
<body>
<h1>My Orders</h1>
<%
	List<Order> orders = (List<Order>) request.getAttribute("orders");
	if(orders == null || orders.isEmpty()){
%>
<h2>You don't have any orders yet</h2>
<%
}else{

for(Order order : orders){

%>


<form action="OrderDetailServlet" method="get">
	
    <input 
        type="hidden"
        name="orderId"
        value="<%= order.getId() %>"
    >
    
    <h3>Order #<%= order.getId()%></h3>
    
    <p>Date: <b><%= order.getOrderDate() %></b></p>
    
    <p>
	Total:
	<b><fmt:formatNumber
		        value="<%= order.getTotal() %>"
		        type="number"
		    /> ₫</b>
	</p>
	
	<p>
		Status:
		<b><%= order.getStatus() %></b>
	</p>

    <button type="submit">
        View Detail
    </button>

</form>


<hr>



<%

}

}

%>

</body>
</html>