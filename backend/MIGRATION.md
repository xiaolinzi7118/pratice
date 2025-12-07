# 数据库迁移指南

## 问题说明

当向现有 SQLite 数据库表添加新字段时，可能会遇到以下错误：
```
Cannot add a NOT NULL column with default value NULL
```

这是因为 SQLite 不支持直接向现有表添加 NOT NULL 列。

## 解决方案

### 方案 1: 删除数据库重新创建（推荐，开发环境）

如果您在开发环境中，数据可以丢失，最简单的方法是删除现有数据库文件，让 Hibernate 重新创建：

```powershell
# 在 backend 目录下执行
cd backend
Remove-Item todolist.db -ErrorAction SilentlyContinue
Remove-Item todolist.db-journal -ErrorAction SilentlyContinue
```

然后重启后端应用，Hibernate 会自动创建新的表结构。

### 方案 2: 手动添加列（保留数据）

如果您需要保留现有数据，可以使用 SQLite 命令行工具手动添加新列：

1. 停止后端应用

2. 打开 SQLite 数据库：
```powershell
cd backend
sqlite3 todolist.db
```

3. 执行以下 SQL 语句添加新列：
```sql
ALTER TABLE todos ADD COLUMN priority TEXT;
ALTER TABLE todos ADD COLUMN due_date TEXT;
ALTER TABLE todos ADD COLUMN category TEXT;
ALTER TABLE todos ADD COLUMN description TEXT;
```

4. 退出 SQLite：
```sql
.quit
```

5. 重启后端应用

### 方案 3: 使用 SQL 脚本

创建一个 SQL 脚本文件 `migrate.sql`：

```sql
-- 添加新列（SQLite 不支持同时添加多个列，需要分别执行）
ALTER TABLE todos ADD COLUMN priority TEXT;
ALTER TABLE todos ADD COLUMN due_date TEXT;
ALTER TABLE todos ADD COLUMN category TEXT;
ALTER TABLE todos ADD COLUMN description TEXT;
```

然后执行：
```powershell
cd backend
sqlite3 todolist.db < migrate.sql
```

## 注意事项

- 所有新增字段都是可空的（nullable = true），所以不会影响现有数据
- 建议在开发环境使用方案 1
- 生产环境建议使用数据库迁移工具（如 Flyway 或 Liquibase）

