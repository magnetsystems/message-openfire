@echo off

if "%JAVA_HOME%" == "" goto javaerror
if not exist "%JAVA_HOME%\bin\java.exe" goto javaerror
set OPENFIRE_HOME=%CD%\..
goto run

:javaerror
echo.
echo Error: JAVA_HOME environment variable not set. Please set it and start Magnet Messaging again.
echo.
goto end

:run
start "Magnet Messaging" "%JAVA_HOME%\bin\java" -server -jar ..\lib\startup.jar
:end


