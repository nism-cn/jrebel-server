package org.nism.jrebel.servlet;

import org.json.JSONObject;
import org.nism.jrebel.core.C;
import org.nism.jrebel.core.E;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

public class JrebelValidateServlet extends HttpServlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        response.setContentType(C.CONTENT_TYPE_JSON);
        E e = new E();
        e.setServerVersion(C.SERVER_VERSION);
        e.setServerProtocolVersion(C.SERVER_PROTOCOL_VERSION);
        e.setServerGuid(C.SERVER_GUID);
        e.setGroupType(C.GROUP_TYPE);
        e.setLicenseType(C.LICENSE_TYPE);
        e.setEvaluationLicense(C.EVALUATION_LICENSE);
        e.setServerRandomness(C.SERVER_RANDOMNESS);
        e.setSeatPoolType(C.SEAT_POOL_TYPE);
        e.setStatusCode(C.STATUS_CODE);
        e.setCompany(C.COMPANY);
        response.getWriter().print(new JSONObject(e));
    }
}
