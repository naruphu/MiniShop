package filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.GlobalExceptionHandler;

@WebFilter("/*")
public class ExceptionFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			GlobalExceptionHandler.handle((HttpServletRequest) request, (HttpServletResponse) response, e);
		}
		

    }
}
