@echo off
setlocal
title 🏦 BTG Pactual - Microservicio de Gestion de Fondos

:: Colores: 0A es fondo negro con letras verdes
color 0A

echo ===============================================================================
echo   BTG PACTUAL - SISTEMA DE GESTION DE FONDOS (MICROSERVICIO)
echo ===============================================================================
echo   Desarrollador: Jaime Alberto Gutierrez
echo   Objetivo: Levantamiento automatizado del ecosistema Docker
echo ===============================================================================
echo.

:: Verificación de requisitos: Docker
docker --version >nul 2>&1
if %errorlevel% neq 0 (
    color 0C
    echo [ERROR] Docker no esta instalado o no se encuentra en el PATH.
    echo Por favor, instale Docker Desktop para continuar.
    pause
    exit /b
)

echo [1/3] Limpiando contenedores y volumenes previos del proyecto...
:: --volumes: Borra la DB vieja para que el seeding de fondos sea fresco
:: --remove-orphans: Elimina contenedores huerfanos de versiones anteriores
docker-compose down --volumes --remove-orphans

echo.
echo [2/3] Compilando codigo fuente y construyendo imagenes...
echo (Este proceso puede tardar unos minutos en el primer arranque)
echo.
:: --build: Fuerza la recompilacion del JAR (vía Multi-stage build)
docker-compose up --build -d

echo.
echo [3/3] Validando estado de los servicios...
echo.

:: Espera breve para que Docker inicie el reporte de estado
timeout /t 5 /nobreak >nul

docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

echo.
echo ===============================================================================
echo   🚀 SOLUCION DESPLEGADA EXITOSAMENTE
echo ===============================================================================
echo   - API: http://localhost:8080/api/v1/funds
echo   - Base de Datos: Puerto 27017
echo.
echo   Nota: El microservicio esperara a que MongoDB este 'Healthy'
echo   antes de iniciar completamente (Healthcheck activado).
echo ===============================================================================
echo.
echo Presione cualquier tecla para ver los logs en tiempo real o cierre esta ventana.
pause >nul

:: Muestra los logs y permite que el usuario vea el banner de Spring Boot
docker-compose logs -f app

endlocal