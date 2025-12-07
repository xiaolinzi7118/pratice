# 快速重启后端服务

## 如果使用 IntelliJ IDEA（最常见）

### 步骤：

1. **停止当前服务**
   - 查看 IDEA 底部工具栏的 "Run" 或 "Debug" 窗口
   - 找到运行中的 `TodoApplication` 进程
   - 点击红色的停止按钮 ⏹️

2. **重新启动**
   - 在项目树中找到：`backend/src/main/java/com/todolist/TodoApplication.java`
   - 右键点击文件
   - 选择 `Run 'TodoApplication.main()'`
   - 或者直接点击文件编辑器中类名旁边的绿色运行按钮 ▶️

3. **等待启动完成**
   - 查看控制台输出
   - 看到 `Started TodoApplication` 表示启动成功

## 如果使用 VS Code

1. **停止服务**：在终端中按 `Ctrl+C`
2. **重新运行**：找到 `TodoApplication.java`，点击运行按钮

## 如果使用命令行

```powershell
cd backend
mvn spring-boot:run
```

## 验证服务已启动

启动成功后，在浏览器访问：
http://localhost:8080/api/todos

应该返回空数组 `[]` 或任务列表。

## 重要提示

重启后，我之前修复的代码才会生效，包括：
- ✅ 自动设置 createdAt 时间戳
- ✅ 改进的错误处理
- ✅ 更详细的错误日志

请重启后端后再试一次添加任务！


