1. 导入项目idea
打开 IntelliJ IDEA，选择 File -> Open，然后选择项目workspace所在的目录，点击 OK 导入项目。
2. 配置依赖
项目使用 Maven-3.3.9版本 进行依赖管理，IDEA 会自动检测 pom.xml 文件并下载所需的依赖。如果依赖下载失败，可以手动刷新 Maven 项目：右键点击 pom.xml 文件，选择 Maven -> Reimport。
3. 数据库配置
在 build/server/proj-admin/src/main/resources目录下找到 application-druid.yml 文件，配置mysql数据库连接信息，例如：
            master:
                url: jdbc:mysql://localhost:3306/build?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
                username: root
                password: 123456
4.	安装数据库
使用源码中的build.sql文件
5. 安装依赖
npm install
6.	运行项目
先运行源码中的redis-start
再在idea中启动前后端服务，最后访问系统

