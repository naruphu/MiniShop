<%@page import="model.Role"%>
<%@page import="model.User"%>
<%@page import="model.Product"%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Product Detail</title>
	
	<link 
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet">
	
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css?v=5">
		
</head>
<body>
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
    
    <!-- ----------------------------------------------------------- -->
	<%
		Product product = (Product) request.getAttribute("product");
	%>

	<%
	User loggedInUser = (User) session.getAttribute("loggedInUser");
	%>
	
	<jsp:include page="/WEB-INF/include/navbar.jsp"/>
	
	<div class="container py-5">

    <div class="card shadow-lg border-0">

        <div class="row g-0">

            <!-- Image -->
            <div class="col-md-6">

                <img 
                src="${pageContext.request.contextPath}/image/<%=product.getImageUrl()%>"
                class="img-fluid rounded-start product-detail-image">

            </div>


            <!-- Info -->
            <div class="col-md-6">

                <div class="card-body">

                    <h1 class="product-title">
					    <%= product.getName() %>
					</h1>


                    <p class="text-muted">
                        Category:
                        <%= product.getCategory().getName() %>
                    </p>


                    <h3 class="text-primary">

                        <fmt:formatNumber
                        value="<%= product.getPrice() %>"
                        type="number"
                        />

                        ₫

                    </h3>


                    <p>
					    Available:
					    <%= product.getQuantity() %>
					</p>
					
					
					<%
					
					if(loggedInUser != null 
					&& loggedInUser.getRole() == Role.CUSTOMER){
					
					    if(product.getQuantity() > 0){
					
					%>
					
					<form action="CartServlet" method="post">
					
					    <input 
					    type="hidden"
					    name="productId"
					    value="<%=product.getId()%>">
					
					    <button 
					    class="btn btn-primary">
					        Add to Cart
					    </button>
					
					</form>
					
					
					<%
					    } else {
					%>
					
					
					<button 
					class="btn btn-secondary"
					disabled>
					
					    Out of Stock
					
					</button>
					
					
					<%
					    }
					
					}
					%>




                </div>

            </div>

        </div>

    </div>

</div>
	
	<script>
    setTimeout(function() {
        const message = document.getElementById("success-message");

        if (message) {
            message.remove();
        }
    }, 3000);
	</script>     
</body>
</html>