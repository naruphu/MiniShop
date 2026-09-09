<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.Product" %>


<!DOCTYPE html>

<html>

<head>

	<jsp:include page="/WEB-INF/include/head.jsp"/>
	
	<meta charset="UTF-8">
	<title>Edit Product</title>


</head>

<body>

<jsp:include page="/WEB-INF/include/navbar.jsp"/>

<%

Product product =
(Product) request.getAttribute("product");

%>


	
	<div class="product-form-container">
	
	<h1>Edit Product</h1>
	
	
	<form class="product-form"
	      action="ProductServlet?action=update"
	      method="post">
	
	
	<input type="hidden"
	       name="id"
	       value="<%= product.getId()%>">
	
	
	<div class="form-group">
	
	<label>Name</label>
	
	<input type="text"
	       name="name"
	       value="<%= product.getName()%>">
	
	</div>
	
	
	
	<div class="form-group">
	
	<label>Price</label>
	
	<input type="text"
       name="price"
       value="<%= product.getPrice().toPlainString()%>">
	
	</div>
	
	
	
	<div class="form-group">
	
	<label>Category</label>
	
	<select name="categoryId">
	
	
	<option value="1"
	<%= product.getCategory().getId()==1?"selected":""%>>
	Book
	</option>
	
	
	<option value="2"
	<%= product.getCategory().getId()==2?"selected":""%>>
	Electronic
	</option>
	
	
	<option value="3"
	<%= product.getCategory().getId()==3?"selected":""%>>
	Household Appliance
	</option>
	
	
	</select>
	
	</div>
	
	
	
	<div class="form-group">
	
	<label>Quantity</label>
	
	<input type="number"
	       name="quantity"
	       value="<%= product.getQuantity()%>">
	
	</div>
	
	
	
	<button class="save-btn">
	
	Update Product
	
	</button>
	
	
	</form>
	
	</div>

</body>
</html>