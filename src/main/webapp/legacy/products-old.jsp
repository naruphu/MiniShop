<%@page import="model.Category"%>
<%@page import="model.Role"%>
<%@page import="model.User"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product</title>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css?v=2">
</head>
<body>
	<h1>Product List</h1>
	
<%
	User loggedInUser = (User) session.getAttribute("loggedInUser");
%>

<div class="top-bar">

    <div class="left-side">
        <% if (loggedInUser != null) { %>
            <span>
                Welcome, <%= loggedInUser.getName() %>
            </span>
            <form action="ProductServlet" method="get">
            	<input 
            		type = "text"
            		name = "keyword"
            		value="${param.keyword}"
            		placeholder="Search product..."
            	>
            	
            	<select name = "categoryId">
            		<option value = "">All Categories</option>
            		<%
            			List<Category> categories = (List<Category>) request.getAttribute("categories");
            		
            			Integer selectedCategoryId =
            		        (Integer) request.getAttribute("categoryId");
            			
            			for(Category category : categories){
            		%>
            			<option 
            				value = "<%= category.getId()%>"
            				<%= selectedCategoryId != null && category.getId() == selectedCategoryId ? "selected" :  "" %>
            			>
            				<%= category.getName() %>
            			
            			</option>
            			
            		<% } %>
            	
            	</select>
            		
            	<button type = "submit">
            		Search
            	</button>
            </form>
        <% } %>
    </div>

    <div class="right-side">
        <% if (loggedInUser != null 
            && loggedInUser.getRole() == Role.CUSTOMER) { %>

            <a href="CartServlet">Cart</a>
            <a href="OrderServlet">My Orders</a>

        <% } %>

        <% if (loggedInUser != null) { %>

            <a href="LogoutServlet">Logout</a>

        <% } %>
        
        <% if (loggedInUser != null 
            && loggedInUser.getRole() == Role.ADMIN) { %>

            <a href="AdminOrderServlet">Customers' Orders</a>
            <a href="ProductServlet?action=create">
                Add new products
            </a>

        <% } %>
        
        
        
    </div>

</div>
    
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

</div>

<div class="product-list">

<%
    List<Product> products =
        (List<Product>) request.getAttribute("products");

    for(Product product : products) {
%>

    <div class="product-card">

        <h2><%= product.getName() %></h2>

        <p class="price">
	    	<fmt:formatNumber
		        value="<%= product.getPrice() %>"
		        type="number"
		    /> ₫
		</p>
		<p>
			<b>Category: <%= product.getCategory().getName() %></b>
		</p>


        <div class="actions">

            <a href="ProductServlet?id=<%= product.getId() %>">
                View Detail
            </a>
            
            <!-- Chỉ ADMIN -->
            <%
            	if(loggedInUser != null && loggedInUser.getRole() == Role.ADMIN){
            %>

            <a href="ProductServlet?action=edit&id=<%= product.getId() %>">
                Edit
            </a>

	            <form action="ProductServlet"
	                  method="post"
	                  style="display:inline;">
	
	                <input type="hidden"
	                       name="action"
	                       value="delete">
	
	                <input type="hidden"
	                       name="id"
	                       value="<%= product.getId() %>">
	                <button type="submit">Delete</button>
	          	</form>
           <%
            	}
           %>
           
           <!-- Chỉ CUSTOMER -->
		    <%
		        if (loggedInUser != null
		            && loggedInUser.getRole() == Role.CUSTOMER) {
		    %>
		
		        <form action="CartServlet" method="post">
		        	<input type="hidden"
				           name="action"
				           value="add">

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



        </div>

    </div>

<%
    }
%>

<!-- Pagination -->

<div class="pagination">


<%

Integer currentPage =
    (Integer) request.getAttribute("currentPage");


Integer totalPages =
    (Integer) request.getAttribute("totalPages");


String keyword =
    (String) request.getAttribute("keyword");

Integer categoryId =
	(Integer) request.getAttribute("categoryId");

if(currentPage == null){
    currentPage = 1;
}


if(totalPages == null){
    totalPages = 1;
}

if(keyword == null){
    keyword="";
}


for(int i = 1; i <= totalPages; i++){

%>


<a href="ProductServlet?page=<%= i %>&keyword=<%= keyword == null ? "" : keyword %>&categoryId=<%= categoryId == null ? "" : categoryId %>"
    <%= i %>


<%

if(i == currentPage){

%>

style="font-weight:bold;"

<%

}

%>

>

<%= i %>

</a>


<%

}

%>


</div>


<script>
    setTimeout(function() {
        const message = document.getElementById("success-message");

        if (message) {
            message.remove();
        }
    }, 3000);
</script>

</div>
</body>
</html>