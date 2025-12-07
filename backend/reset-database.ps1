# 重置数据库脚本
# 此脚本会删除现有数据库文件，让 Hibernate 重新创建表结构

Write-Host "正在重置数据库..." -ForegroundColor Yellow

$dbFile = "todolist.db"
$dbJournal = "todolist.db-journal"

# 检查数据库文件是否存在
if (Test-Path $dbFile) {
    Write-Host "发现数据库文件: $dbFile" -ForegroundColor Cyan
    Remove-Item $dbFile -Force
    Write-Host "已删除: $dbFile" -ForegroundColor Green
} else {
    Write-Host "数据库文件不存在，无需删除" -ForegroundColor Gray
}

# 删除日志文件（如果存在）
if (Test-Path $dbJournal) {
    Remove-Item $dbJournal -Force
    Write-Host "已删除: $dbJournal" -ForegroundColor Green
}

Write-Host ""
Write-Host "数据库重置完成！" -ForegroundColor Green
Write-Host "请重启后端应用，Hibernate 将自动创建新的表结构。" -ForegroundColor Yellow

