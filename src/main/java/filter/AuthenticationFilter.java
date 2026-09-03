package filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import model.User;


@WebFilter("/*")
public class AuthenticationFilter implements Filter {


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest req = (HttpServletRequest) request;


        HttpServletResponse res = (HttpServletResponse) response;


        User user = (User) req.getSession() .getAttribute("loggedInUser");


        String path = req.getRequestURI();


        // ví dụ các trang cần login
        if(path.contains("CartServlet")
        || path.contains("OrderServlet")){


            if(user == null){

                res.sendRedirect(
                    "login.jsp"
                );

                return;
            }

        }


        chain.doFilter(request,response);
    }


}