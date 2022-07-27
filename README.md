# jrebel-server

搭建jrebel激活服务; 欢迎star。
> 源项目fork自这里，仅用作记录，谢谢。
> 源地址： https://gitee.com/gsls200808/JrebelLicenseServerforJava

# 使用教程

1. 克隆本项目，根据个性化需求修改，打成jar包，或者到这里[直接下载]()
   ``` bash
   mvn clean package
   ```

2. 进入到jar包所在目录，执行(默认端口80)：
   ``` bash
   java -jar JrebelServer.jar -p 80
   ```

3. 通过浏览器访问ip

4. idea 配置

# 修改内容

1. 代码逻辑调整
2. 升级系统安全性

|      \       |        前        |        后         |
|:------------:|:---------------:|:----------------:|
|     jdk      |       1.6       |       1.8        |
|    jetty     | 8.1.2.v20120308 | 9.4.46.v20220331 |
|   servlet    |      3.0.1      |      4.0.1       |
| bouncycastle |      1.58       |       1.70       |
|    base64    | commons-codec:1.10  |       java.util.Base64        |
|    json    |  json-lib:2.4   | fastjson2:2.0.8  |
|    lang3    |      1.58       |       不依赖        |



