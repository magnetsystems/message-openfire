@echo off

setlocal

if "%selfWrapped%"=="" (
  SET selfWrapped=true
  %ComSpec% /s /c ""%~0" %*"
  GOTO :EOF
)

set TITLE="MagnetMessaging"
set PROG=mmx-server

if "start"=="%1" (
	call :start
) else (
	if "stop"=="%1" (
		call :stop
	) else (
		if "restart"=="%1" (
			call :restart
		) else (
			echo Usage: %0 {start^|stop^|restart}
			exit /b
		)
	)
) 
endlocal
goto :end



:start
	if exist %PROG%.pid (
		echo Error! %PROG% is already running or you have a stale pid file. If %PROG% is not running delete %PROG%.pid file and restart"
		exit /b 1
	)	
	call :check_java_home
	start %TITLE% "%JAVA_HOME%\bin\java" -server -jar ..\lib\startup.jar
	FOR /F "usebackq tokens=2" %%i IN (`tasklist /nh /fi "WINDOWTITLE eq %TITLE%"`) DO echo %%i > .\%PROG%.pid
goto :end



:stop
	del .\%PROG%.pid
	taskkill /f /fi "WINDOWTITLE eq %TITLE%" /t
goto :end



:restart
	call :stop
	call :start
goto :end



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


