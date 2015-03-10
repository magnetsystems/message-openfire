@echo off

setlocal

rem Change the following if it has been changed to use another port
set PORT_TO_CHECK=9090

set check_port=true
set TITLE="MagnetMessaging"
set PROG=mmx-server
set OPENFIRE_HOME=%CD%\..


if "%selfWrapped%"=="" (
  SET selfWrapped=true
  %ComSpec% /s /c ""%~0" %*"
  GOTO :EOF
)

if "-p"=="%1" (
	set check_port=false
	shift
)	

if "start"=="%1" (
	call :start
) else (
	if "stop"=="%1" (
		call :stop
	) else (
		if "restart"=="%1" (
			call :restart
		) else (
			call :print_usage
		)
	)
) 
endlocal
goto :end



:print_usage
	echo Usage: mmx-server.bat [-p] {start^|stop^|restart}
	echo.
	echo Start, stop, or restart the Magnet Mesaging server.
	echo. 
	echo Options:
	echo    -p    No port check when starting.
	echo. 
exit /b



:start
	if %check_port%==true (
		call :check_ports
	)
	call :check_java

	if exist %PROG%.pid (
		echo Error! %PROG% is already running or you have a stale pid file. If %PROG% is not running delete %PROG%.pid file and try again.
		exit 1
	)	
	call :check_java_home
	start %TITLE% "%JAVA_HOME%\bin\java" -server -DopenfireHome="%OPENFIRE_HOME%" -jar ..\lib\startup.jar
	FOR /F "usebackq tokens=2" %%i IN (`tasklist /nh /fi "WINDOWTITLE eq %TITLE%"`) DO echo %%i > .\%PROG%.pid
goto :end



:check_ports
	netstat -aon | findstr "%PORT_TO_CHECK%" 1>NUL
	if %ERRORLEVEL% equ 0 (
		echo TCP port "%PORT_TO_CHECK%" is already in use, cannot start messaging server
		exit 1
	)
	echo Using ports "%PORT_TO_CHECK%"
goto :eof



:stop
	set /p pid=<.\%PROG%.pid
	taskkill /f /pid %pid% /t
	del .\%PROG%.pid
goto :end



:restart
	call :stop
	call :start
goto :end



:check_java
	setlocal
	java -version 1>NUL 2>NUL
	if 0 neq %ERRORLEVEL% (
		echo java is not installed
		echo MMX server needs java version 1.6 OR higher
		echo Please check https://java.com/en/download/
		exit 1
	)

	for /f tokens^=2-5^ delims^=.-_^" %%j in ('java -fullversion 2^>^&1') do set "jver=%%j%%k%%l%%m
	if %jver% LSS 16000 (
		echo MMX server needs java version 1.6 OR higher
		echo Please check https://java.com/en/download/
		exit 1
	)
	echo java is installed

	endlocal
goto :eof



:check_java_home
	if "%JAVA_HOME%" == "" goto javaerror
	if not exist "%JAVA_HOME%\bin\java.exe" goto javaerror
	set OPENFIRE_HOME=%CD%\..
goto :eof



:javaerror
	echo JAVA_HOME environment variable not set. Please set it and start Magnet Messaging again.
	exit /b 1
goto :eof

:end
