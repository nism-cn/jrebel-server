package org.nism.jrebel;

import org.nism.jrebel.utils.BrowseUtil;
import org.nism.jrebel.utils.MainUtil;
import org.noear.solon.Solon;

import java.util.Map;

/**
 * main class
 *
 * @author inism
 */
public class App {

    public static void main(String[] args) {
        Map<String, String> arguments = MainUtil.parseArguments(args);
        String port = arguments.get("p");
        port = port == null || !port.matches("\\d+") ? "8080" : port;
        try {
            String finalPort = port;
            Solon.start(App.class, args, app -> app.cfg().setProperty("server.port", finalPort));
            BrowseUtil.open("http://localhost:" + port);
        } catch (Exception e) {
            System.err.println("can not auto open browse!");
        }
    }

}
