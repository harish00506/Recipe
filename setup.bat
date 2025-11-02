@echo off
REM Recipe App Setup Script for Windows

echo 🍳 Recipe App Setup Script
echo ================================

setlocal enabledelayedexpansion

REM Colors are not supported in batch, using text instead
REM Check Java
echo Checking Java...
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed
    echo Please install Java 21 or higher
    exit /b 1
) else (
    java -version
)

REM Check Node
echo.
echo Checking Node.js...
node -v >nul 2>&1
if errorlevel 1 (
    echo ERROR: Node.js is not installed
    echo Please install Node.js 18.x or higher
    exit /b 1
) else (
    node -v
)

REM Check Maven
echo.
echo Checking Maven...
mvn -v >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven is not installed
    echo Please install Maven 3.9.0 or higher
    exit /b 1
) else (
    mvn -v
)

REM Check Docker
echo.
echo Checking Docker...
docker -v >nul 2>&1
if errorlevel 1 (
    echo INFO: Docker is not installed (optional)
) else (
    docker -v
)

echo.
echo ✓ All checks passed!
echo.
echo Setup Options:
echo 1. Quick Start with Docker:
echo    docker-compose up -d
echo.
echo 2. Manual Setup:
echo    Backend:  cd backend ^&^& mvn spring-boot:run
echo    Frontend: cd frontend ^&^& npm install ^&^& npm run dev
echo    Database: createdb recipe_app
echo.
echo Visit: http://localhost:3000
echo.
pause
