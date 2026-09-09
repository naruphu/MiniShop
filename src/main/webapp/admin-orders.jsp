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
	
	<jsp:include page="/WEB-INF/include/head.jsp"/>
	
	
	<title>Admin - Orders</title>

</head>
<body>

<jsp:include page="/WEB-INF/include/navbar.jsp"/>

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


	<div class="admin-order-container">
	
	
	<h1>
	Admin - Orders
	</h1>
	
	
	
	<%
	
	List<Order> orders =
	(List<Order>) request.getAttribute("orders");
	
	
	if(orders == null || orders.isEmpty()){
	
	%>
	
	
	<h2 class="empty">
	No Orders
	</h2>
	
	
	<%
	
	}else{
	
	
	for(Order order : orders){
	
	%>
	
	
	
	<div class="admin-order-card">
	
	
	<div class="admin-order-header">
	
	
	<h2>
	Order #<%= order.getId() %>
	</h2>
	
	
	<span class="status">
	<%= order.getStatus() %>
	</span>
	
	
	</div>
	
	
	
	
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
	
	
	
	
	<p class="total">
	
	Total:
	
	<b>
	
	<fmt:formatNumber
	value="<%= order.getTotal() %>"
	type="number"
	/>
	
	₫
	
	</b>
	
	</p>
	
	<a 
	href="AdminOrderServlet?action=detail&id=<%= order.getId() %>"
	class="detail-btn">
	
	    View Detail
	
	</a>
	
	
	
	
	<form action="AdminOrderServlet" method="post"
	class="status-form">
	
	
	<input
	type="hidden"
	name="orderId"
	value="<%= order.getId()%>"
	>
	
	
	<label>
	Status
	</label>
	
	
	
	<select name="status">
	
	
	<%
	
	for(OrderStatus status : OrderStatus.values()){
	
	
	%>
	
	
	<option
	value="<%= status %>"
	<%= order.getStatus()==status ? "selected":"" %>
	>
	
	<%= status %>
	
	
	</option>
	
	
	<%
	
	}
	
	%>
	
	
	</select>
	
	
	
	<button type="submit">
	
	Update
	
	</button>
	
	
	</form>
	
	
	
	</div>
	
	
	
	<%
	
	}
	
	}
	
	%>
	
	
	</div>


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