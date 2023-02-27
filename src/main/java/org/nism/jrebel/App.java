package org.nism.jrebel;

import org.nism.jrebel.utils.MainUtil;
import org.noear.solon.Solon;

import java.util.Map;

public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args, app -> {
            Map<String, String> arguments = MainUtil.parseArguments(args);
            String port = arguments.get("p");
            port = port == null || !port.matches("\\d+") ? "8080" : port;
            app.cfg().setProperty("server.port", port);
        });
    }
}
