<p align="center">
	<img alt="logo" src="https://foruda.gitee.com/avatar/1685606382242091856/10212686_nism_1685606382.png!avatar100">
</p>
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">Jrebel Server v2.0.3</h1>
<h4 align="center">JRebel 激活服务</h4>
<p align="center">
    <a href="https://gitee.com/nism/jrebel-server/stargazers"><img src="https://gitee.com/nism/jrebel-server/badge/star.svg?theme=dark"></a>
    <a href="https://gitee.com/nism/jrebel-server"><img src="https://img.shields.io/badge/JrebelServer-v2.0.3-%233b5d3f"></a>
    <a href="https://gitee.com/nism/jrebel-server/blob/main/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>
    <a href="http://solon.noear.org/"><img src="https://img.shields.io/badge/solon-2+-%23ff8c90"></a>
</p>

## 简介

为 JRebel & XReble 提供激活服务 [源项目](https://gitee.com/gsls200808/JetBrainsLicenseServerforJava) 

## 前置条件

**本服务仅限 jrebel<=2022.4.1 且 idea<2022.3**

## 优点

1. **轻量:** 采用solon作为容器, 体积更小,更轻量( **约500KB** ).
2. **简单:** 命令式启动. 启动成功后, 在浏览器中直接进行傻瓜式拷贝.

## 使用

1. 终端输入： java -jar JrebelServer.jar -p 8080 (默认8080端口)
2. 服务会自动打开浏览器至 http://localhost:8080/
3. 点击页面中的注册信息进行复制
4. 打开IDEA中JRebel & XReble 的设置
5. 粘贴复制的信息到IDEA中
6. 恭喜你, 就可以开心使用

👉 [详细](https://gitee.com/nism/jrebel-server/wikis/pages) 👈

## 注意

- V2.X.X 使用的是jdk内置加解密jdk版本可能不兼容.
- 已测试 (OracleJDK 1.8.202 / OpenJDK 11+ 17+)
- 如碰无法注册的情况可以使用 V1.X.X版本

## 本项目仅提供学术研究

开源不易. fork代码请注明原作者. 欢迎优秀的开发者提供宝贵的意见. 谢谢!!!