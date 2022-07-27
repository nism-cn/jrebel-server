package org.nism.jrebel.servlet;

import org.nism.jrebel.core.C;
import org.nism.jrebel.util.Rsa;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReleaseTicketServlet extends HttpServlet {

    @Override
    public void service(ServletRequest request, ServletResponse res) throws IOException, ServletException {
        HttpServletResponse response = null;
        if (res instanceof HttpServletResponse) {
            response = (HttpServletResponse) res;
        } else {
            throw new ServletException("non-HTTP request or response");
        }
        response.setContentType(C.CONTENT_TYPE_HTML);
        String salt = request.getParameter("salt");
        if (salt == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            String xmlContent = "<ReleaseTicketResponse><message></message><responseCode>OK</responseCode><salt>" + salt + "</salt></ReleaseTicketResponse>";
            String xmlSignature = Rsa.sign(xmlContent);
            String body = "<!-- " + xmlSignature + " -->\n" + xmlContent;
            response.getWriter().print(body);
        }
    }
}
