package org.nism.jrebel;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.nism.jrebel.servlet.*;
import org.nism.jrebel.util.MainUtil;

import java.util.Map;

/**
 * app main
 *
 * @author inism
 */
public class MainServer {

    private static final String DEFAULT_PORT = "8080";

    public static void main(String[] args) throws Exception {
        Map<String, String> arguments = MainUtil.parseArguments(args);
        String port = arguments.get("p");

        if (port == null || !port.matches("\\d+")) {
            port = DEFAULT_PORT;
        }

        Server server = new Server(Integer.parseInt(port));

        WebAppContext app = new WebAppContext();
        app.setContextPath("/");
        app.setResourceBase("src/main/webapp");

        app.setParentLoaderPriority(true);

        app.addServlet(IndexServlet.class, "/");
        app.addServlet(JrebelLeasesServlet.class, "/jrebel/leases");
        app.addServlet(JrebelLeasesServlet.class, "/agent/leases");
        app.addServlet(JrebelLeases1Servlet.class, "/jrebel/leases/1");
        app.addServlet(JrebelLeases1Servlet.class, "/agent/leases/1");
        app.addServlet(PingServlet.class, "/rpc/ping.action");
        app.addServlet(JrebelValidateServlet.class, "/jrebel/validate-connection");
        app.addServlet(ObtainTicketServlet.class, "/rpc/obtainTicket.action");
        app.addServlet(ReleaseTicketServlet.class, "/rpc/releaseTicket.action");

        server.setHandler(app);
        server.setStopAtShutdown(true);

        server.start();
        System.out.println("JRebel & XRebel License Server started at http://localhost:" + port);
        server.join();
    }
}