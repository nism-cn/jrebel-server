# 简介

JrebelServer 为IDEA中 JRebel & XReble 提供激活服务 [源项目](https://gitee.com/gsls200808/JetBrainsLicenseServerforJava)

# 使用

👉 [点我查看](https://gitee.com/nism/jrebel-server/releases) 👈

# 优点

1. **轻量:** 采用jetty嵌入式作为容器, 整体Jar包小于10M.
2. **简单:** 命令式启动. 启动成功后, 在浏览器中直接进行傻瓜式拷贝.

# 与源项目差异

1. 升级

|     升级内容  |        源项目      |         当前项目        |
|:------------:|:------------------:|:----------------------:|
|     jdk      |        1.6         |          1.8           |
|    maven     |        3.0-        |          3.6+          |
|    jetty     |  8.1.2.v20120308   |    9.4.46.v20220331    |
|   servlet    |       3.0.1        |         4.0.1          |
| bouncycastle |        1.58        |          1.70          |

2. 更换

|    调整内容   |        源项目      |         当前项目        |
|:------------:|:------------------:|:----------------------:|
|    base64    | commons-codec:1.10 |    java.util.Base64    |
|     json     |    json-lib:2.4    |    fastjson2:2.0.8     |
|    lang3     |        1.58        |       exclusion        |

# 再次开发

开源不易. fork代码请注明原作者. 欢迎优秀的开发者提供宝贵的意见. 谢谢!!!