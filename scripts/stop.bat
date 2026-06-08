@echo off
chcp 65001 > nul
echo.
echo ==============================
echo   Inventory 시스템 종료
echo ==============================
echo.

REM 1. SpringBoot 프로세스 종료
echo [1/2] SpringBoot 앱 종료 중...
for /f "tokens=5" %%p in ('netstat -ano ^| findstr ":8080" ^| findstr "LISTENING"') do (
    taskkill /PID %%p /F > nul 2>&1
    echo       PID %%p 종료 완료
)

REM 2. Oracle 컨테이너 중지 (데이터 보존)
echo [2/2] Oracle 컨테이너 중지 중...
docker compose stop
echo       Oracle 중지 완료 (데이터 보존됨)

echo.
echo ==============================
echo   종료 완료
echo ==============================
echo.
pause