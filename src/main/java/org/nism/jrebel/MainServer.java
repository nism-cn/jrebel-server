package org.nism.jrebel;

import com.alibaba.fastjson2.JSONObject;
import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.nism.jrebel.core.MainHandler;
import org.nism.jrebel.core.Sign;
import org.nism.jrebel.core.C;
import org.nism.jrebel.core.E;
import org.nism.jrebel.util.MainUtil;
import org.nism.jrebel.util.Rsa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

/**
 * @author inism
 */
public class MainServer {

    private static final String DEFAULT_PORT = "80";

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