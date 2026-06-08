@echo off
chcp 65001 > nul
echo.
echo ==============================
echo   DB 초기화 (데이터 전체 삭제)
echo ==============================
echo.
echo [WARNING] 이 작업은 DB 데이터를 완전히 삭제합니다.
echo           Flyway 이력도 초기화되어 V1부터 다시 실행됩니다.
echo.
set /p CONFIRM=정말 초기화하시겠습니까? (yes 입력):

if not "!CONFIRM!"=="yes" (
    echo 취소되었습니다.
    pause
    exit /b 0
)

REM 1. SpringBoot 프로세스 종료
echo.
echo [1/3] SpringBoot 앱 종료 중...
for /f "tokens=5" %%p in ('netstat -ano ^| findstr ":8080" ^| findstr "LISTENING"') do (
    taskkill /PID %%p /F > nul 2>&1
)

REM 2. 컨테이너 + 볼륨 삭제
echo [2/3] 컨테이너 및 볼륨 삭제 중...
docker compose down -v
echo       삭제 완료

REM 3. 컨테이너 재시작
echo [3/3] Oracle 재시작 중...
docker compose up -d
echo       재시작 완료. 앱을 다시 실행하면 Flyway가 V1부터 재실행됩니다.

echo.
echo ==============================
echo   초기화 완료
echo   이제 start.bat 을 실행하세요.
echo ==============================
echo.
pause