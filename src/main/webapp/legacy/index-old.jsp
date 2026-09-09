<%@ page import="model.Category" %>
<%@page import="java.util.List"%>
<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<html>
<body>

    <h1>Add Product</h1>

    <form action="ProductServlet" method="post"
      enctype="multipart/form-data">

        <label for="name">Product Name:</label>
        <input type="text" id="name" name="name">

        <br><br>

        <label for="price">Price:</label>
        <input type="number" id="price" name="price">

        <br><br>

        <label for="quantity">Quantity:</label>
        <input type="number" id="quantity" name="quantity">

        <br><br>
        
        <%
        	List<Category> categories = (List<Category>) request.getAttribute("categories");
        %>
        <label for="categoryId">Category</label>
        <select id="categoryId" name="categoryId">
        <%
        	for(Category category : categories){
        %>
        	<option value="<%= category.getId() %>">
        		<%= category.getName()%>
        	</option>
        <%
        	}
        %>
        </select>
        
        <br><br>
        
        <input type = "file" name = "image">
        
       	<br><br>

        <button type="submit">Add Product</button>

    </form>

</body>
</html><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>