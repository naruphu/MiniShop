<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.Product" %>

<%
    Product product =
        (Product) request.getAttribute("product");
%>

<html>
<body>

<h1>Edit Product</h1>

<form action="ProductServlet?action=update" method="post">

    <input type="hidden"
           name="id"
           value="<%= product.getId() %>">

    <label>Name:</label>
    <input type="text"
           name="name"
           value="<%= product.getName() %>">

    <br><br>

    <label>Price:</label>
    <input type="number"
           name="price"
           value="<%= product.getPrice() %>">

    <br><br>
    
    <label>Category:</label>
    <select name="categoryId">

    <option value="1"
        <%= product.getCategory().getId() == 1 ? "selected" : "" %>>
        Book
    </option>

    <option value="2"
        <%= product.getCategory().getId() == 2 ? "selected" : "" %>>
        Electronic
    </option>

    <option value="3"
        <%= product.getCategory().getId() == 3 ? "selected" : "" %>>
        Household Appliance
    </option>

	</select>

    <br><br>

    <label>Quantity:</label>
    <input type="number"
           name="quantity"
           value="<%= product.getQuantity() %>">

    <br><br>

    <button type="submit">
        Update Product
    </button>

</form>

</body>
</html>