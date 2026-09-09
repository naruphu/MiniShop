<%@page import="model.Category"%>
<%@page import="model.Role"%>
<%@page import="model.User"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Naruphu - Products</title>

    <!-- Bootstrap -->
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
        rel="stylesheet">
	
	<jsp:include page="/WEB-INF/include/head.jsp"/>
	
</head>

<body>

<jsp:include page="/WEB-INF/include/navbar.jsp"/>	

<%
    User loggedInUser =
        (User) session.getAttribute("loggedInUser");

    List<Category> categories =
        (List<Category>) request.getAttribute("categories");

    Integer selectedCategoryId =
        (Integer) request.getAttribute("categoryId");
%>


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

<form action="ProductServlet"
      method="get"
      class="search-section">

    <div class="search-box">

        <input
            type="text"
            name="keyword"
            value="${param.keyword}"
            class="search-input"
            placeholder="Search products...">

        <select name="categoryId"
                class="category-select">

            <option value="">
                All Categories
            </option>

            <%
                for(Category category : categories) {
            %>

                <option
                    value="<%= category.getId() %>"
                    <%= selectedCategoryId != null
                        && category.getId() == selectedCategoryId
                        ? "selected"
                        : "" %>>

                    <%= category.getName() %>

                </option>

            <%
                }
            %>

        </select>

        <button type="submit"
                class="search-btn">

            Search

        </button>

    </div>

</form>
    
<div class="row g-4">

<%
    List<Product> products =
        (List<Product>) request.getAttribute("products");
	if(products != null){
		for(Product product : products) {
%>

    <div class="col-sm-6 col-lg-4 col-xl-3">

        <div class="card h-100 shadow-sm border-0">
	        <a href="ProductServlet?id=<%= product.getId() %>">
			
			    <img
			        src="<%= request.getContextPath() %>/image/<%= product.getImageUrl() %>"
			        class="card-img-top product-image"
			        alt="<%= product.getName() %>">
			
			</a>
    

            <div class="card-body d-flex flex-column">

                <h5 class="card-title fw-bold">
				
				    <a href="ProductServlet?id=<%= product.getId() %>"
				       class="product-name-link">
				
				        <%= product.getName() %>
				
				    </a>
				
				</h5>

                <p class="text-muted mb-2">
                    <%= product.getCategory().getName() %>
                </p>

                <p class="fs-5 fw-bold text-primary">
                    <fmt:formatNumber
                        value="<%= product.getPrice() %>"
                        type="number"
                    /> ₫
                </p>

                <div class="mt-auto d-flex gap-2 flex-wrap">

                    <a
                        href="ProductServlet?id=<%= product.getId() %>"
                        class="btn btn-outline-primary btn-sm">
                        View Detail
                    </a>

                    <%
					if(loggedInUser != null 
					&& loggedInUser.getRole() == Role.ADMIN){
					%>

                        <a
                            href="ProductServlet?action=edit&id=<%= product.getId() %>"
                            class="btn btn-warning btn-sm">
                            Edit
                        </a>
                        
						
						<form action="ProductServlet" method="post">
							<input type="hidden"
						       name="action"
						       value="status">
						
							<input type="hidden" 
							       name="id"
							       value="<%= product.getId() %>">
							
							<input type="hidden"
							       name="page"
							       value="<%= request.getAttribute("currentPage") %>">

							<select name="status">
	
								<option value="ACTIVE"
									<%= "ACTIVE".equals(product.getStatus()) ? "selected" : "" %>>
									ACTIVE
									</option>
									
									
									<option value="INACTIVE"
									<%= "INACTIVE".equals(product.getStatus()) ? "selected" : "" %>>
									INACTIVE
								</option>
							
							</select>
							
							<button class="btn btn-danger btn-sm">
								SAVE
							</button>
						
						</form>
                    <%  } %>

                    <% if(loggedInUser != null
                        && loggedInUser.getRole() == Role.CUSTOMER) {
                    %>

                        <form action="CartServlet"
                              method="post">

                            <input
                                type="hidden"
                                name="action"
                                value="add">
                          	<input type="hidden"
							       name="page"
							       value="<%= request.getAttribute("currentPage") %>">

                            <input
                                type="hidden"
                                name="productId"
                                value="<%= product.getId() %>">
                            <button
                                type="submit"
                                class="btn btn-primary btn-sm">
                                Add to Cart
                            </button>

                        </form>

                    <% } %>

                </div>

            </div>

        </div>

    </div>

<%
		}
}
%>
</div>

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