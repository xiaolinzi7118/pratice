@echo off
echo 正在启动后端服务...
echo.

REM 检查 Java 是否安装
java -version >nul 2>&1
if errorlevel 1 (
    echo [错误] 未检测到 Java，请先安装 JDK 17
    echo 请参考 ../SETUP_GUIDE.md 安装 Java
    pause
    exit /b 1
)

REM 检查 Maven 是否安装
mvn -version >nul 2>&1
if errorlevel 1 (
    echo [错误] 未检测到 Maven
    echo.
    echo 请选择以下方式之一启动后端：
    echo 1. 安装 Maven（参考 ../SETUP_GUIDE.md）
    echo 2. 使用 IDE（如 IntelliJ IDEA）运行 TodoApplication.java
    echo.
    pause
    exit /b 1
)

echo 使用 Maven 启动 Spring Boot 应用...
echo.
mvn spring-boot:run


