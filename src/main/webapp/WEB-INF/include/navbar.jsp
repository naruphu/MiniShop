<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%@ page import="model.Role" %>
<%@ page import="model.User" %>


<%
User loggedInUser = 
(User) session.getAttribute("loggedInUser");
%>


<nav class="mini-navbar">

    <div class="mini-navbar-inner">


        <!-- Logo -->
        <a class="mini-logo"
           href="${pageContext.request.contextPath}/ProductServlet">

            Naruphu Shop

        </a>



        <!-- Quote -->
        <div class="navbar-slogan">

            Good products for customers

        </div>



        <!-- User -->
        <div class="mini-user-area">


            <% if(loggedInUser != null){ %>


                <span class="welcome-text">

                    Welcome, <%= loggedInUser.getName() %>

                </span>


                <div class="mini-actions">


                <% if(loggedInUser.getRole() == Role.CUSTOMER){ %>


                    <a href="CartServlet">
                        Cart
                    </a>


                    <a href="OrderServlet">
                        Orders
                    </a>


                <% } %>



                <% if(loggedInUser.getRole() == Role.ADMIN){ %>


                    <a href="AdminOrderServlet">
                        Orders
                    </a>


                    <a href="ProductServlet?action=create">
                        Add Product
                    </a>


                <% } %>



                    <a href="LogoutServlet"
                       class="logout-link">

                        Logout

                    </a>


                </div>


            <% } else { %>


			<a href="login.jsp"
			class="nav-btn">
			Login
			</a>
			
			
			<a href="register.jsp"
			class="nav-btn">
			Register
			</a>
			
			
			<% } %>


        </div>


    </div>


</nav>

</body>
</html>