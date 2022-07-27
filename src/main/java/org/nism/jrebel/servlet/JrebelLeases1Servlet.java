package org.nism.jrebel.servlet;

import com.alibaba.fastjson2.JSONObject;
import org.nism.jrebel.core.C;
import org.nism.jrebel.core.E;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JrebelLeases1Servlet extends HttpServlet {

    @Override
    public void service(ServletRequest request, ServletResponse res) throws IOException, ServletException {
        HttpServletResponse response = null;
        if (res instanceof HttpServletResponse) {
            response = (HttpServletResponse) res;
        } else {
            throw new ServletException("non-HTTP request or response");
        }
        response.setContentType(C.CONTENT_TYPE_JSON);

        E e = new E();
        e.setServerVersion(C.SERVER_VERSION);
        e.setServerProtocolVersion(C.SERVER_PROTOCOL_VERSION);
        e.setServerGuid(C.SERVER_GUID);
        e.setGroupType(C.GROUP_TYPE);
        e.setStatusCode(C.STATUS_CODE);
        e.setMsg(C.MSG);
        e.setStatusMessage(C.STATUS_MESSAGE);
        e.setCompany(request.getParameter("username"));
        response.getWriter().print(JSONObject.toJSONString(e));
    }
}
