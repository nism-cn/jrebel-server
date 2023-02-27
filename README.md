# 简介

JrebelServer 为IDEA中 JRebel & XReble 提供激活服务 [源项目](https://gitee.com/gsls200808/JetBrainsLicenseServerforJava)

# 使用

👉 [点我查看](https://gitee.com/nism/jrebel-server/releases) 👈

# 优点

1. **轻量:** 采用solon作为容器, 体积更小,更轻量(约1M).
2. **简单:** 命令式启动. 启动成功后, 在浏览器中直接进行傻瓜式拷贝.

# 与源项目差异

1. 升级

|     jar      |     source      |      target      |
|:------------:|:---------------:|:----------------:|
|     jdk      |       1.6       |       1.8        |
|    maven     |      3.0-       |       3.6+       |
|    jetty     | 8.1.2.v20120308 | 9.4.49.v20220914 |
|   servlet    |      3.0.1      |      4.0.1       |
| bouncycastle |      1.58       |       1.71       |

2. 更换

|      jar      |       source       |      target      |
|:-------------:|:------------------:|:----------------:|
|    base64     | commons-codec:1.10 | java.util.Base64 |
|     json      | org.json:20220320  |    exclusion     |
|     lang3     |        1.58        |    exclusion     |
|     jetty     |  8.1.2.v20120308   |    exclusion     |
|  bouncycastle |       1.71         |    exclusion     |

3. 升级历史  
#### 1.0.0  升级JDK8,优化算法  
#### 1.0.1  替换fastjson->org.json更轻量,更安全  
#### 1.0.2  升级jar包  
#### 2.0.0  【重大升级】除去bouncycastle采用JDK内置算法,jetty->solon更轻量 (整体约1M)

# 再次开发

开源不易. fork代码请注明原作者. 欢迎优秀的开发者提供宝贵的意见. 谢谢!!!