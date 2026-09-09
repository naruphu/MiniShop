<%@ page import="model.Category" %>
<%@ page import="java.util.List" %>

<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Add Product</title>

<jsp:include page="/WEB-INF/include/head.jsp"/>

</head>


<body>

<jsp:include page="/WEB-INF/include/navbar.jsp"/>


	<div class="product-form-container">
	
	
	<h1>Add Product</h1>
	
	
	<form class="product-form"
	      action="ProductServlet"
	      method="post"
	      enctype="multipart/form-data">
	
	
	<div class="form-group">
	
	<label>Product Name</label>
	
	<input type="text"
	       name="name">
	
	</div>
	
	
	
	<div class="form-group">
	
	<label>Price</label>
	
	<input type="text"
	       name="price">
	
	</div>
	
	
	
	<div class="form-group">
	
	<label>Quantity</label>
	
	<input type="number"
	       name="quantity">
	
	</div>
	
	
	
	<div class="form-group">
	
	<label>Category</label>
	
	
	<select name="categoryId">
	
	
	<%
	List<Category> categories =
	    (List<Category>) request.getAttribute("categories");
	
	if (categories != null) {
	    for (Category category : categories) {
	%>
	
	
	<option value="<%= category.getId()%>">
	
	<%= category.getName()%>
	
	</option>
	
	
	<%
	    }
	}
	
	%>
	
	
	</select>
	
	
	</div>
	
	
	
	<div class="form-group">
	
	
	<label>Image</label>
	
	
	<input type="file"
	       name="image">
	
	
	</div>
	
	
	
	<button class="save-btn"
	        type="submit">
	
	Add Product
	
	</button>
	
	
	</form>
	
	
	</div>


</body>

</html>