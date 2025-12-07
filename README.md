# Todo List 全栈项目

一个基于 Vue 3 + Spring Boot + SQLite 的全栈 TodoList 应用，适合作为学习项目。

## 技术栈

### 前端
- **Vue 3** - 使用 Composition API
- **Vite** - 现代化构建工具
- **Axios** - HTTP 客户端

### 后端
- **Spring Boot 3.2.0** - Java 后端框架
- **Spring Data JPA** - 数据持久化
- **SQLite** - 轻量级数据库

## 项目结构

```
practice/
├── frontend/          # Vue 3 前端项目
│   ├── src/
│   │   ├── components/    # Vue 组件
│   │   ├── services/      # API 服务
│   │   └── App.vue        # 主应用组件
│   ├── package.json
│   └── vite.config.js
├── backend/           # Spring Boot 后端项目
│   ├── src/main/java/com/todolist/
│   │   ├── entity/        # 实体类
│   │   ├── repository/    # 数据访问层
│   │   ├── service/       # 业务逻辑层
│   │   ├── controller/    # 控制器层
│   │   └── config/        # 配置类
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
└── README.md
```

## 核心功能

- ✅ 添加任务
- ✅ 删除任务
- ✅ 标记任务完成/未完成
- ✅ 任务列表展示

## 环境要求

- **Java**: JDK 17 或更高版本
- **Node.js**: 16 或更高版本
- **Maven**: 3.6 或更高版本（用于后端）

## 快速开始

### 后端启动

1. 进入后端目录：
```bash
cd backend
```

2. 使用 Maven 构建项目：
```bash
mvn clean install
```

3. 运行 Spring Boot 应用：
```bash
mvn spring-boot:run
```

后端服务将在 `http://localhost:8080` 启动。

### 前端启动

1. 进入前端目录：
```bash
cd frontend
```

2. 安装依赖：
```bash
npm install
```

3. 启动开发服务器：
```bash
npm run dev
```

前端应用将在 `http://localhost:3000` 启动。

## API 文档

### 获取所有任务
```
GET /api/todos
```

响应示例：
```json
[
  {
    "id": 1,
    "title": "学习 Vue 3",
    "completed": false,
    "createdAt": "2024-01-01T10:00:00"
  }
]
```

### 创建新任务
```
POST /api/todos
Content-Type: application/json

{
  "title": "学习 Vue 3",
  "completed": false
}
```

### 更新任务
```
PUT /api/todos/{id}
Content-Type: application/json

{
  "title": "学习 Vue 3",
  "completed": true
}
```

### 删除任务
```
DELETE /api/todos/{id}
```

## 数据库

项目使用 SQLite 数据库，数据库文件 `todolist.db` 会在首次运行时自动创建在项目根目录。

数据库表结构：
- `id`: 主键，自增
- `title`: 任务标题
- `completed`: 是否完成（布尔值）
- `created_at`: 创建时间

## 开发说明

### 后端开发

后端采用标准的 Spring Boot 三层架构：
- **Controller 层**: 处理 HTTP 请求和响应
- **Service 层**: 业务逻辑处理
- **Repository 层**: 数据访问

### 前端开发

前端使用 Vue 3 Composition API：
- 组件化开发
- 响应式数据管理
- API 服务封装

## 注意事项

1. 确保后端服务在 8080 端口运行，前端才能正常调用 API
2. 首次运行后端会自动创建数据库和表结构
3. 前端开发服务器配置了代理，API 请求会自动转发到后端

## 许可证

此项目仅用于学习目的。


