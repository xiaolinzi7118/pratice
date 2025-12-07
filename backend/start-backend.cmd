@echo off
chcp 65001 >nul
echo ========================================
echo 正在启动后端服务...
echo ========================================
echo.

cd /d %~dp0

REM 检查 Maven
mvn -version >nul 2>&1
if errorlevel 1 (
    echo [错误] 未找到 Maven 命令
    echo 请确保 Maven 已安装并添加到 PATH 环境变量
    echo.
    pause
    exit /b 1
)

echo [信息] Maven 已找到
echo.
echo [信息] 开始编译并启动 Spring Boot 应用...
echo [信息] 服务将在 http://localhost:8080 启动
echo.
echo 按 Ctrl+C 停止服务
echo ========================================
echo.

mvn spring-boot:run


