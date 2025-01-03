package com.itsc;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebServlet("/")
public class DispatcherServlet extends GenericServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String path = request.getServletPath();

        switch (path) {
            case "/registerBook":
                request.getRequestDispatcher("/registerBook").forward(req, res);
                break;
            case "/displayBooks":
                request.getRequestDispatcher("/displayBooks").forward(req, res);
                break;
            case "/deleteBook":
                request.getRequestDispatcher("/deleteBook").forward(req, res);
                break;
            case "/searchBooks":
                request.getRequestDispatcher("/searchBooks").forward(req, res);
                break;
            default:
                res.getWriter().println("Invalid endpoint.");
        }
    }
}
