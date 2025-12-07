@echo off
echo 正在重置数据库...
echo.

if exist todolist.db (
    echo 发现数据库文件: todolist.db
    del /f todolist.db
    echo 已删除: todolist.db
) else (
    echo 数据库文件不存在，无需删除
)

if exist todolist.db-journal (
    del /f todolist.db-journal
    echo 已删除: todolist.db-journal
)

echo.
echo 数据库重置完成！
echo 请重启后端应用，Hibernate 将自动创建新的表结构。
pause

