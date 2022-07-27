package org.nism.jrebel;

import org.eclipse.jetty.server.Server;
import org.nism.jrebel.core.MainHandler;
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
        server.setHandler(new MainHandler());
        server.start();

        System.out.println("License Server started at http://localhost:" + port);
        System.out.println("JRebel 7.1  and earlier version Activation address was: http://localhost:" + port + "/{token}, with any email.");
        System.out.println("JRebel 2018.1 and later version Activation address was: http://localhost:" + port + "/{guid} , with any email.");

        server.join();
    }
}