@echo off
setlocal

:: Obtener el directorio de usuario
set USER_HOME=%USERPROFILE%

:: Eliminar los archivos
del "%USER_HOME%\test.h2.db" >nul 2>&1
del "%USER_HOME%\test.trace.db" >nul 2>&1

echo Ficheros eliminados.