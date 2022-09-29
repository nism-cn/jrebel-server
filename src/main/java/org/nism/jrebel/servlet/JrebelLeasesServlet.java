package org.nism.jrebel.servlet;

import org.json.JSONObject;
import org.nism.jrebel.core.C;
import org.nism.jrebel.core.E;
import org.nism.jrebel.core.Sign;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JrebelLeasesServlet extends HttpServlet {

    @Override
    public void service(ServletRequest request, ServletResponse res) throws IOException, ServletException {
        HttpServletResponse response = null;
        if (res instanceof HttpServletResponse) {
            response = (HttpServletResponse) res;
        } else {
            throw new ServletException("non-HTTP request or response");
        }
        response.setContentType(C.CONTENT_TYPE_JSON);
        String clientRandomness = request.getParameter("randomness");
        String username = request.getParameter("username");
        String guid = request.getParameter("guid");

        boolean offline = Boolean.parseBoolean(request.getParameter("offline"));
        long validFrom = 0L;
        long validUntil = 0L;
        if (offline) {
            validFrom = Long.parseLong(request.getParameter("clientTime"));
            validUntil = validFrom + 180 * 24 * 60 * 60 * 1000L;
        }
        E e = new E();
        e.setServerVersion(C.SERVER_VERSION);
        e.setServerProtocolVersion(C.SERVER_PROTOCOL_VERSION);
        e.setServerGuid(C.SERVER_GUID);
        e.setGroupType(C.GROUP_TYPE);
        e.setId(C.ID);
        e.setLicenseType(C.LICENSE_TYPE);
        e.setEvaluationLicense(C.EVALUATION_LICENSE);
        e.setSignature(C.SIGNATURE);
        e.setServerRandomness(C.SERVER_RANDOMNESS);
        e.setSeatPoolType(C.SEAT_POOL_TYPE);
        e.setStatusCode(C.STATUS_CODE);
        e.setOffline(offline);
        e.setValidFrom(validFrom);
        e.setValidUntil(validUntil);
        e.setCompany(C.COMPANY);
        e.setOrderId(C.ORDER_ID);
        e.setZeroIds(C.ZERO_IDS);
        e.setLicenseValidFrom(C.LICENSE_VALID_FROM);
        e.setLicenseValidUntil(C.LICENSE_VALID_UNTIL);

        if (clientRandomness == null || username == null || guid == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            Sign sign = new Sign();
            sign.create(clientRandomness, guid, offline, validFrom, validUntil);
            String signature = sign.getSignature();
            e.setSignature(signature);
            e.setCompany(username);
            response.getWriter().print(new JSONObject(e));
        }
    }
}
