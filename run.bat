@echo off
setlocal
cd /d "%~dp0"
rem A Japanese console reads CP932 while Java writes UTF-8.
chcp 65001 >nul

if defined JAVA_HOME goto :run
where java >nul 2>&1
if errorlevel 1 goto :nojava

:run
call "%~dp0gradlew.bat" runClient %*
set EXITCODE=%ERRORLEVEL%
if not "%EXITCODE%"=="0" goto :failed
exit /b 0

:nojava
echo.
echo No JDK found. Set JAVA_HOME to a JDK 21 installation.
echo.
pause
exit /b 1

:failed
echo.
echo Failed with exit code %EXITCODE%.
echo.
pause
exit /b %EXITCODE%
