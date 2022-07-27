package org.nism.jrebel.core;

import com.alibaba.fastjson2.JSONObject;
import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.nism.jrebel.util.RandomEmail;
import org.nism.jrebel.util.Rsa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

/**
 * main handler
 *
 * @author inism
 */
public class MainHandler extends AbstractHandler {

    private static final Logger LOG = Log.getLogger(AbstractConnector.class);
    private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
    private static final String CONTENT_TYPE_HTML = "text/html; charset=utf-8";

    @Override
    public void handle(String uri, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.info("url: {}", uri);
        baseRequest.setHandled(true);
        response.setStatus(HttpServletResponse.SC_OK);
        switch (uri) {
            case "/":
                indexHandler(request, response);
                break;
            case "/jrebel/leases":
            case "/agent/leases":
                jrebelLeasesHandler(request, response);
                break;
            case "/jrebel/leases/1":
            case "/agent/leases/1":
                jrebelLeases1Handler(request, response);
                break;
            case "/rpc/ping.action":
                pingHandler(request, response);
                break;
            case "/jrebel/validate-connection":
                jrebelValidateHandler(request, response);
                break;
            case "/rpc/obtainTicket.action":
                obtainTicketHandler(request, response);
                break;
            case "/rpc/releaseTicket.action":
                releaseTicketHandler(request, response);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void jrebelValidateHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE_JSON);
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
        response.getWriter().print(JSONObject.toJSONString(e));
    }

    private void jrebelLeases1Handler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE_JSON);
        String username = request.getParameter("username");

        E e = new E();
        e.setServerVersion(C.SERVER_VERSION);
        e.setServerProtocolVersion(C.SERVER_PROTOCOL_VERSION);
        e.setServerGuid(C.SERVER_GUID);
        e.setGroupType(C.GROUP_TYPE);
        e.setStatusCode(C.STATUS_CODE);
        e.setMsg(C.MSG);
        e.setStatusMessage(C.STATUS_MESSAGE);
        if (username != null) {
            e.setCompany(username);
        }
        response.getWriter().print(JSONObject.toJSONString(e));
    }

    private void jrebelLeasesHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE_JSON);
        String clientRandomness = request.getParameter("randomness");
        String username = request.getParameter("username");
        String guid = request.getParameter("guid");

        boolean offline = Boolean.parseBoolean(request.getParameter("offline"));
        String validFrom = "0";
        String validUntil = "0";
        if (offline) {
            String clientTime = request.getParameter("clientTime");
            long clientTimeUntil = Long.parseLong(clientTime) + 180L * 24 * 60 * 60 * 1000;
            validFrom = clientTime;
            validUntil = String.valueOf(clientTimeUntil);
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
        e.setValidFrom(Long.parseLong(validFrom));
        e.setValidUntil(Long.parseLong(validUntil));
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
            response.getWriter().print(JSONObject.toJSONString(e));
        }
    }

    private void releaseTicketHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE_HTML);
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

    private void obtainTicketHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE_HTML);
        String salt = request.getParameter("salt");
        String username = request.getParameter("userName");
        String prolongationPeriod = "607875500";
        if (salt == null || username == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            String xmlContent = "<ObtainTicketResponse><message></message><prolongationPeriod>" + prolongationPeriod + "</prolongationPeriod><responseCode>OK</responseCode><salt>" + salt + "</salt><ticketId>1</ticketId><ticketProperties>licensee=" + username + "\tlicenseType=0\t</ticketProperties></ObtainTicketResponse>";
            String xmlSignature = Rsa.sign(xmlContent);
            String body = "<!-- " + xmlSignature + " -->\n" + xmlContent;
            response.getWriter().print(body);
        }
    }

    private void pingHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE_HTML);
        String salt = request.getParameter("salt");
        if (salt == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            String xmlContent = "<PingResponse><message></message><responseCode>OK</responseCode><salt>" + salt + "</salt></PingResponse>";
            String xmlSignature = Rsa.sign(xmlContent);
            String body = "<!-- " + xmlSignature + " -->\n" + xmlContent;
            response.getWriter().print(body);
        }
    }

    private void indexHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE_HTML);
        String licenseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        String html = "<!DOCTYPE html><html lang='zh'>" +
                "<head>" + "<title>JrebelServer</title>" + C.FAVICON_LINK + C.HTML_CSS + "</head>" +
                "<body class='n'>" +
                "<h1 class='b w n p2'><img style='margin-top: 7px' src='" + C.FAVICON + "'> 您在浏览的是 JetBrains License Server 服务!</h1>" +
                "<p>&nbsp;</p>" +
                "<p>JRebel 7.1 及旧版本激活地址:  <b class='r'>" + licenseUrl + "/{token}</b>, 以及任意邮箱地址。</p>" +
                "<p>JRebel 2018.1+ 版本激活地址: <b class='r'>" + licenseUrl + "/{guid} </b>, 以及任意邮箱地址。</p>" +
                "<p>(例: 👉<a href='javascript:void(0)' onclick='fn(this)'>" + licenseUrl + "/" + UUID.randomUUID() + "</a> 👈点我复制并刷新)</p>" +
                "<p>(随机邮箱: 👉<a href='javascript:void(0)' onclick='fn(this)'>" + RandomEmail.get() + "</a> 👈点我复制并刷新)</p>" +
                "<div class='b w f'>&copy;2022-" + Calendar.getInstance().get(Calendar.YEAR) + " All Right Reserved.</div>" +
                "</body>" + C.HTML_JS + "</html>";

        response.getWriter().println(html);
    }
}
