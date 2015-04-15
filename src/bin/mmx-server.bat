@echo off

rem Please change ..\conf\startup.properties if you would like to use ports other than the default.
rem For detail, please reference the troubleshooting guide.



setlocal

if "%selfWrapped%"=="" (
  SET selfWrapped=true
  %ComSpec% /s /c ""%~0" %*"
  GOTO :EOF
)

set check_port=true
set TITLE="Messagingserver"
set PROGNAME="Messaging server"
set PROG=mmx-server


call :check2Args %*

call :loadPorts ..\conf\startup.properties

if "-p"=="%1" (
	set check_port=false
	shift
)	

if "start"=="%1" (
	cd ..
	call :touchPlugins 1>NUL 2>NUL
	cd .\bin
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



:touchPlugins
	setlocal

	set ODIR=plugins
	set NDIR=plugins_bak
	rmdir /q /s %NDIR%
	xcopy /e plugins %NDIR%\
	pushd %NDIR%

	for /d /r "." %%D in (.) do (
		cd "%%D"
		copy *+
	)
	popd

	rmdir /q /s %ODIR%
	rename %NDIR% %ODIR%

	endlocal
goto :eof



:print_usage
	echo Usage: mmx-server.bat [-p] {start^|stop^|restart}
	echo.
	echo Start, stop, or restart the %PROGNAME%.
	echo. 
	echo Options:
	echo    -p    No port check when starting.
	echo. 
	pause
exit /b



:start
	if %check_port%==true (
		call :check_port_list %xmppPort% %xmppPortSecure% %httpPort% %httpPortSecure% %mmxAdminPort% %mmxAdminPortSecure% %mmxPublicPort% %mmxPublicPortSecure%
	)

	call :check_java

	if exist %PROG%.pid (
		echo.
		echo Error! %PROGNAME% is already running or you have a stale pid file. If %PROGNAME% is not running, then please delete mmx-standalone-dist-win\messaging\bin\mmx-server.pid file and try again.
		exit 1
	)	

	call :check_java_home

	start %TITLE% "%JAVA_HOME%\bin\java" -DopenfireHome=..\ -jar ..\lib\startup.jar

	if 0 neq %ERRORLEVEL% (
		echo Error starting with error code:%ERRORLEVEL%
		exit 1
	)

	timeout /t 3 >nul

	FOR /F "usebackq tokens=2" %%i IN (`tasklist /nh /fi "WINDOWTITLE eq %TITLE%"`) DO echo %%i > .\%PROG%.pid
	set /p pid=<.\%PROG%.pid
	if "No "=="%pid%" (
		echo Error starting the server. It could be an environment issue. Please execute "%JAVA_HOME%\bin\java" -DopenfireHome=..\ -jar ..\lib\startup.jar and fix the environment accordingly.
		del .\%PROG%.pid
		exit 1
	)

goto :end



:check2Args
	setlocal EnableDelayedExpansion
	FOR %%A in (%*) DO (
	        SET /A args_count+=1
	        if !args_count! equ 3 (
	                call :print_usage
	                exit
	        )
	)

	if !args_count! equ 0 (
	        call :print_usage
	        exit
	)
	endlocal
goto :eof



:loadPorts
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "dbHost=" %1') do SET dbHost=%%i
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "dbPort=" %1') do SET dbPort=%%i
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "dbName=" %1') do SET dbName=%%i
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "dbUser=" %1') do SET dbUser=%%i
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "dbPassword=" %1') do SET dbPassword=%%i
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "xmppDomain=" %1') do SET xmppDomain=%%i
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "xmppPort=" %1') do SET xmppPort=%%i
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "xmppPortSecure=" %1') do SET xmppPortSecure=%%i
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "httpPort=" %1') do SET httpPort=%%i
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "httpPortSecure=" %1') do SET httpPortSecure=%%i
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "mmxAdminPort=" %1') do SET mmxAdminPort=%%i
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "mmxAdminPortSecure=" %1') do SET mmxAdminPortSecure=%%i
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "mmxPublicPort=" %1') do SET mmxPublicPort=%%i
	FOR /F "eol=; tokens=2 delims==" %%i IN ('findstr /i "mmxPublicPortSecure=" %1') do SET mmxPublicPortSecure=%%i
goto :eof



:check_port
        netstat -aon | findstr "%1" 1>NUL
        if %ERRORLEVEL% equ 0 (
                echo.
                echo Error! TCP port "%1" is already in use; thus, cannot start %PROGNAME%. Please refer to readme.htm on how to change the ports.
                exit 1
        )
        echo Validated port "%1"
goto :eof



:check_port_list
	for %%a in (%*) do (
        	call :check_port %%a
	)
goto :eof



:stop
	setlocal EnableDelayedExpansion
	if exist .\%PROG%.pid (
		set /p pid=<.\%PROG%.pid
		taskkill /f /pid !pid! /t >nul
		del .\%PROG%.pid
	) else (
		echo %PROGNAME% is not running
	)
	endlocal
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

	endlocal
goto :eof



:check_java_home
	if "%JAVA_HOME%" == "" goto javaerror
	if not exist "%JAVA_HOME%\bin\java.exe" goto javaerror
	set OPENFIRE_HOME=%CD%\..
goto :eof



:javaerror
	echo JAVA_HOME environment variable not set. Please set it and start %PROGNAME% again.
	exit 1
goto :eof

:end
