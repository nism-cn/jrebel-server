package org.nism.jrebel.controller;

import org.nism.jrebel.core.C;
import org.nism.jrebel.utils.RsaUtil;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Produces;
import org.noear.solon.boot.jlhttp.HTTPServer;
import org.noear.solon.boot.web.ContextBase;

import java.io.IOException;

/**
 * RpcController
 *
 * @author inism
 */
@Controller
@Mapping("/rpc")
public class RpcController {

    @Mapping("/ping.action")
    @Produces(C.CONTENT_TYPE_HTML)
    public String ping(ContextBase ctx, String salt) throws IOException {
        if (salt == null) {
            HTTPServer.Response response = (HTTPServer.Response) ctx.response();
            response.sendError(403);
            return null;
        } else {
            String xmlContent = "<PingResponse><message></message><responseCode>OK</responseCode><salt>" + salt + "</salt></PingResponse>";
            String xmlSignature = RsaUtil.sign(xmlContent);
            return "<!-- " + xmlSignature + " -->\n" + xmlContent;
        }
    }

    @Mapping("/obtainTicket.action")
    @Produces(C.CONTENT_TYPE_HTML)
    public String obtainTicket(ContextBase ctx, String salt, String userName) throws IOException {
        if (salt == null || userName == null) {
            HTTPServer.Response response = (HTTPServer.Response) ctx.response();
            response.sendError(403);
            return null;
        } else {
            String xmlContent = "<ObtainTicketResponse><message></message><prolongationPeriod>607875500</prolongationPeriod><responseCode>OK</responseCode><salt>" + salt + "</salt><ticketId>1</ticketId><ticketProperties>licensee=" + userName + "\tlicenseType=0\t</ticketProperties></ObtainTicketResponse>";
            String xmlSignature = RsaUtil.sign(xmlContent);
            return "<!-- " + xmlSignature + " -->\n" + xmlContent;
        }
    }

    @Mapping("/releaseTicket.action")
    @Produces(C.CONTENT_TYPE_HTML)
    public String releaseTicket(ContextBase ctx, String salt) throws IOException {
        if (salt == null) {
            HTTPServer.Response response = (HTTPServer.Response) ctx.response();
            response.sendError(403);
            return null;
        } else {
            String xmlContent = "<ReleaseTicketResponse><message></message><responseCode>OK</responseCode><salt>" + salt + "</salt></ReleaseTicketResponse>";
            String xmlSignature = RsaUtil.sign(xmlContent);
            return "<!-- " + xmlSignature + " -->\n" + xmlContent;
        }
    }
}
