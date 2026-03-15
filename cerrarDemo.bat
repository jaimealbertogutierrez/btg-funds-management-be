@echo off
setlocal enabledelayedexpansion
title Sistema de Gestion de Fondos BTG - Cierre de Entorno

echo ======================================================
echo          CIERRE DE DEMO Y LIMPIEZA DE ENTORNO
echo ======================================================
echo.

:: 1. Detener contenedores
echo [1/4] Deteniendo servicios y eliminando volumenes...
docker-compose down -v
if %errorlevel% neq 0 (
    echo [ERROR] No se pudo detener docker-compose.
    pause
    exit /b %errorlevel%
)

:: 2. Eliminar la imagen
echo [2/4] Eliminando imagen local del backend...
docker rmi btg-funds-api:latest 2>nul

:: 3. Limpieza de sistema
echo [3/4] Limpiando residuos de Docker...
docker system prune -f

:: 4. Verificacion de puertos (Re-escrito para evitar errores de sintaxis)
echo [4/4] Verificando puertos 8080 y 27017...

:: Verificar Puerto 8080
netstat -ano | findstr :8080 >nul
if %errorlevel% equ 0 (
    echo [ADVERTENCIA] Puerto 8080 sigue ocupado.
) else (
    echo [OK] Puerto 8080 liberado correctamente.
)

:: Verificar Puerto 27017
netstat -ano | findstr :27017 >nul
if %errorlevel% equ 0 (
    echo [ADVERTENCIA] Puerto 27017 sigue ocupado.
) else (
    echo [OK] Puerto 27017 liberado correctamente.
)

echo.
echo ======================================================
echo    PROCESO FINALIZADO: El entorno esta limpio.
echo    MUCHAS GRACIAS POR SU ATENCION
echo    JAIME ALBERTO GUTIERREZ MEJIA - ANALISTA PROGRAMADOR JAVA BACKEND
echo    TODOS LOS DERECHOS RESERVADOS 2026
echo ======================================================
echo.
pause