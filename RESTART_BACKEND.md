# 重启后端服务指南

## 方式一：使用 IDE（推荐）

### IntelliJ IDEA

1. **停止当前服务**
   - 在运行窗口（Run/Debug）中找到运行中的后端服务
   - 点击红色停止按钮

2. **重新启动**
   - 找到 `backend/src/main/java/com/todolist/TodoApplication.java`
   - 右键点击文件 -> Run 'TodoApplication.main()'
   - 或点击类名旁边的绿色运行按钮 ▶️

### VS Code

1. **停止当前服务**
   - 在终端中找到运行中的 Java 进程
   - 按 `Ctrl+C` 停止

2. **重新启动**
   - 找到 `TodoApplication.java`
   - 点击 `main` 方法上方的 "Run" 按钮

## 方式二：使用命令行

### 如果已安装 Java 和 Maven

```powershell
cd backend
mvn spring-boot:run
```

### 如果没有安装 Maven

您需要先安装环境，请参考 `SETUP_GUIDE.md`

## 方式三：使用批处理脚本

双击运行 `backend/start-backend.bat`（需要先安装 Java 和 Maven）

## 验证服务是否启动

服务启动成功后，您应该看到：

```
Started TodoApplication in X.XXX seconds
```

然后可以通过以下方式验证：

1. **浏览器访问**: http://localhost:8080/api/todos
2. **检查端口**: 
   ```powershell
   netstat -ano | findstr ":8080"
   ```

## 如果遇到问题

- 检查端口 8080 是否被占用
- 查看控制台的错误日志
- 参考 `TROUBLESHOOTING.md` 进行故障排查


