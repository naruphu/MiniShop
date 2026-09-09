package exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExceptionHandler {

    public static void handle(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException, ServletException {

        e.printStackTrace();

        if (e instanceof AppException) {

            request.getSession().setAttribute(
                "message",
                e.getMessage()
            );

            response.sendRedirect(
                request.getHeader("Referer")
            );

        } else {

            request.setAttribute(
                "errorMessage",
                "Something went wrong. Please try again later."
            );


        request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}