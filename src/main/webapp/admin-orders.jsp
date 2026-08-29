<%@page import="model.OrderStatus"%>
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
<title>Admin - Orders</title>
</head>
<body>
<!-- MESSAGE Ở ĐÂY -->
    <%
        String message =
            (String) session.getAttribute("message");

        if (message != null) {
    %>

        <p id="message">
        <%= message %>
    	</p>

    <%
            session.removeAttribute("message");
        }
    %>


<%
	List<Order> orders = (List<Order>) request.getAttribute("orders");
%>
<h1>Admin - Orders</h1>

<%
	if(orders == null || orders.isEmpty()){
%>
	<h2>No Orders</h2>
<%
	}else{
		for(Order order : orders){
%>
	<h2>Order #<%= order.getId() %></h2>
	
	<p>
		Customer:
		<%= order.getUser().getUsername() %>
	</p>
	
	<p>
		Date:
		<%= order.getOrderDate() %>
	</p>
	
	<p>
		Total:
		<b><fmt:formatNumber
		        value="<%= order.getTotal() %>"
		        type="number"
		    /> ₫</b>
	</p>
	
	<form action="AdminOrderServlet" method="post">
		<input
			type = "hidden"
			name = "orderId"
			value = "<%= order.getId() %>"
		>
		<label>Status</label>
		<select name = "status">
			<%
				for(OrderStatus status : OrderStatus.values()){
			%>
					<option value = "<%= status%>"
						<%= order.getStatus() == status ? "selected" : "" %>>
						<%= status %>
					
					</option>
			<%
				} 
			%>
		
		</select>
		
		<button type = "submit">
			Update
		</button>
	</form>	
	<hr>
	
<%
		}
	}
%>
<script>
    setTimeout(function() {
        const message = document.getElementById("message");

        if (message) {
            message.remove();
        }
    }, 3000);
</script>
</body>
</html>