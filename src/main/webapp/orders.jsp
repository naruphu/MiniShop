<%@page import="model.Order"%>
<%@page import="java.util.List"%>
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