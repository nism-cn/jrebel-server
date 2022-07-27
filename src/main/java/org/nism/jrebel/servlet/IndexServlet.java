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

    private static final String FAVICON = "data:image/x-icon;base64,AAABAAEAICAAAAEAIACoEAAAFgAAACgAAAAgAAAAQAAAAAEAIAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAtJCD/KyQg/yokIf8qJCH/KyQh/yskIf8rJCH/KyQh/yskIf8rJCH/LCUi/yghHv8vODD/ME8//2+Adv+0s7H/29rZ////////////lJGQ/yomI/8rJCH/JB0a/xwVEv8bFRL/IhsY/ywlIv8rJCH/IxwZ/xwVEv8dFhP/JyAd/y0kIP8sJCH/KiQh/yskIf8sJCH/KyQh/yskIf8rJCH/KyQh/yskIf8tJSL/KCIe/yg1Kv9MdGD/QmVU/xorIv9ucm/////////////Avbz/NTAt/yQdGv9UT03/fnt5/4SBgP9cWFb/Jh8c/ywlIv9ZVFL/gX57/316eP8+Ojj/LSQh/ywkIf8rJCH/LSQh/y0kIf8rJCH/KyQh/yskIf8qJCH/LCQh/y0kIf8qIx//IC4i/z1tVv9Yk3r/LVtI/01fV//2+vj//////9LPzv86NTL/HBYT/5mVlP///////////7Curf8bFBH/R0JA/9jW1v///////////15bWv8rJCH/KyQh/yskIf8rJCH/KyQh/yskIf8rJCH/KyQh/yskIf8rJCL/LCQj/yskI/8cJx7/Ml1K/1SWe/9GiG7/bZWF/+n58///////0s/O/zk1Mv8dFxP/kY6M//z8+///////pqSj/x8YFf+hnZv///////////+0sLD/ODMx/yskIf8rJCH/KyQh/yskIf8rJCH/KyQh/yskIf8rJCH/LCQi/yslI/8nIiL/JiIi/xsiHP8uUUH/UZJ3/0CMbv9yqJP/4/vz///////Rz87/OTUy/xwWE/+Sj47//v79//////+joaH/XFdW/+zr6///////3dvb/1JNS/8kHhr/LSQh/ywkIf8rJCH/KyQh/yskIf8rJCH/KyQh/yskIv8pJCP/HiAe/yUuK/8uODX/ESEa/yFCMv9IiG3/QI5v/26mkf/i+/H//////8/Qzv82NDL/HBcT/5GQjv/+/////////9TT0//Qzs7///////Py8v93c3L/Ix0b/y0mIv8rJCH/KyQh/yskIf8rJCH/KyQh/yskIf8tJSL/JSMf/xwpJf8tTkT/Q3Vk/0Z9a/8pY0//HlxF/zqJa/88jm//bqaR/+P78v//////y9bS/zZBOv8YFxP/kZCO///////+/v7/////////////////9PT0/4qHhv8wKij/KiMg/ykkIf8qJCH/KyQh/yskIf8rJCH/KyUi/yMjH/8gLyj/O2NX/0iMdv8+mHj/PJ59/0mti/9JrIv/SK6K/z2Zd/9qqJH/4fzz//v////L4tv/V3Np/xslHv+Mj4z////////////U09P/pKKh/+no5///////+fj3/29saf8jHBn/KyQh/yskIf8rJCH/KyQh/yskIf8mIx//HDAo/z5tX/9LkXj/OpVz/zmjff9CuI//Q7+V/0bBl/9GwZf/RrON/3G6oP/e/fP/+f///8Tk2f9qloX/Lk4//4KPiP/+//3//////6Sjo/8WEhH/k5GR//v7+///////o6Ce/y4oJf8rJCL/KyQh/yskIf8rJCH/JyQh/x4oI/84YVT/SZJ5/zmVcv88qID/RryR/0LBlP89wZL/PMCS/z3ClP9FupL/ecet/93+9f/4////v+LW/2aZhv84YU//gpWL//z9/P//////u7q6/1tYV/+5uLj//Pz8//////+ioJ//LScl/ywjJP8rJCP/KyQh/ywlIv8eIR3/LEY//06Kdf87lXP/PKeB/0W9k/9AwZP/PcGS/z3Bkv89wZP/PsKU/0O6kf90yK3/2P/3//T///++6dv/YZuG/zNgTf+FmZD////////////8+/v/+Pj3////////////8vLx/2xnZv8jHRr/KiIf/ysjIP8tJCL/LSYj/xshHP81Vkz/T5Z+/zqhfP9EvJL/QcCU/z7Bk/88wZP/PMGT/z3Bk/8+wpT/QLuR/2bIqP+6+On/z/rz/6jt1/9ksJT/Kl9J/3GFfP/t7uz/6urq/+zr6//t7ez/4d/e/7u4t/9saGf/Ligl/ysiH/8lMin/ICwj/xwlHf8dIxz/EyAY/yJFOP9CkHT/RbeP/0HBlP89wZP/PcGT/zzBk/87wZP/PcGT/z3Bk/9AwJT/S76Y/17An/9lv6H/WsCd/1i+mv8/jW//G0My/y85M/87OTj/PTg3/zw4Nv87NjX/My0r/ychHv8pIh//LSQh/0JuWv88aVX/MltI/y5QP/8nRTf/Gj8v/ziMbv9JxJj/Pb+R/z3Bk/89wZP/PcGT/z3Bk/89wZP/PcGT/z7Ak/8/v5T/QL2S/0G9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";
    private static final String FAVICON_LINK = "<link rel='icon' href='" + FAVICON + "'>";

    private static final String HTML_CSS = "<style>h1{text-align:center}p{text-align:center}.b{background-color:#21252b}.w{color:white}.r{color:red}.n{margin:0;padding:0}.f{position:fixed;text-align:center;line-height:50px;width:100%;bottom:0;}.p2{padding:10px}</style>";
    private static final String HTML_JS = "<script>function fn(e){var i=document.createElement('input');document.body.appendChild(i);i.setAttribute('value',e.innerText);i.select();if(document.execCommand('copy')){alert(e.innerText+'\\n复制文本到剪切板')}document.body.removeChild(i);location.reload()}</script>";


    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setContentType(C.CONTENT_TYPE_HTML);
        String licenseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        String html = "<!DOCTYPE html><html lang='zh'>" +
                "<head>" + "<title>JrebelServer</title>" + FAVICON_LINK + HTML_CSS + "</head>" +
                "<body class='n'>" +
                "<h1 class='b w n p2'><img style='margin-top: 7px' src='" + FAVICON + "'> 您在浏览的是 JetBrains License Server 服务!</h1>" +
                "<p>&nbsp;</p>" +
                "<p>JRebel 7.1 及旧版本激活地址:  <b class='r'>" + licenseUrl + "/{token}</b>, 以及任意邮箱地址。</p>" +
                "<p>JRebel 2018.1+ 版本激活地址: <b class='r'>" + licenseUrl + "/{guid} </b>, 以及任意邮箱地址。</p>" +
                "<p>(例: 👉<a href='javascript:void(0)' onclick='fn(this)'>" + licenseUrl + "/" + UUID.randomUUID() + "</a> 👈点我复制并刷新)</p>" +
                "<p>(随机邮箱: 👉<a href='javascript:void(0)' onclick='fn(this)'>" + RandomEmail.get() + "</a> 👈点我复制并刷新)</p>" +
                "<div class='b w f'>&copy;2022-" + Calendar.getInstance().get(Calendar.YEAR) + " All Right Reserved.</div>" +
                "</body>" + HTML_JS + "</html>";

        response.getWriter().println(html);
    }
}
