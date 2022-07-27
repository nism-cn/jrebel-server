package org.nism.jrebel.servlet;

import org.nism.jrebel.core.C;
import org.nism.jrebel.util.RandomEmail;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class IndexServlet extends HttpServlet {


    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setContentType(C.CONTENT_TYPE_HTML);
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
