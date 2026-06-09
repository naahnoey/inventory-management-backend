@echo off
setlocal enabledelayedexpansion
chcp 65001 > nul
echo.
echo ==============================
echo   Inventory 시스템 시작
echo ==============================
echo.

REM 1. Docker Desktop 실행 여부 확인
docker info > nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Docker Desktop이 실행되지 않았습니다.
    echo Docker Desktop을 먼저 실행해주세요.
    pause
    exit /b 1
)

REM 2. Oracle 컨테이너 상태 확인
echo [1/3] Oracle 컨테이너 확인 중...
docker inspect -f "{{.State.Status}}" oracle-xe > nul 2>&1

if %errorlevel% neq 0 (
    echo       oracle-xe 컨테이너가 없습니다. 새로 시작합니다.
    docker compose up -d
) else (
    for /f "delims=" %%s in ('docker inspect -f "{{.State.Status}}" oracle-xe') do set STATUS=%%s
    if "!STATUS!"=="running" (
        echo       oracle-xe 이미 실행 중입니다. 건너뜁니다.
    ) else (
        echo       oracle-xe 컨테이너를 시작합니다.
        docker compose start
    )
)

REM 3. Oracle healthy 상태 대기
echo.
echo [2/3] Oracle 준비 대기 중...
:WAIT_LOOP
for /f "delims=" %%h in ('docker inspect -f "{{.State.Health.Status}}" oracle-xe 2^>nul') do set HEALTH=%%h
if not "!HEALTH!"=="healthy" (
    echo       아직 준비 중입니다 !HEALTH! ... 10초 대기
    timeout /t 10 /nobreak > nul
    goto WAIT_LOOP
)
echo       Oracle 준비 완료!

REM 4. SpringBoot 앱 실행
echo.
echo [3/3] SpringBoot 앱 실행 중...
echo       로그는 app.log 에서 확인하세요.
echo.
start "Inventory App" cmd /k "cd /d %~dp0.. && gradlew bootRun > app.log 2>&1"

echo ==============================
echo   시작 완료!
echo   http://localhost:8081/health
echo ==============================
echo.
pause