@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  streaming cql console script
@rem
@rem ##########################################################################
@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0

set CQL_DEPENDENCY_JAR_PATH=%DIRNAME%\..\stream-storm-1.0-jar-with-dependencies.jar
set CQL_HOME=%DIRNAME%\..\
set CQL_CONF_DIR=%DIRNAME%\..\conf

set JAVA_OPTS=
@rem set JAVA_OPTS=-Xms128m -Xmx256m -Xdebug -Xrunjdwp:transport=dt_socket,address=8011,server=y,suspend=n -Dcom.sun.management.jmxremote.port=2200 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false

@rem Find java.exe
if not "%JAVA_HOME%" == "" goto OkJHome

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.
goto fail

:OkJHome
if exist "%JAVA_HOME%\bin\java.exe" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windowz variants
set JAVA_EXE=%JAVA_HOME%/bin/java.exe
set APP_MAINCLASS=com.huawei.streaming.cql.CQLClient
set MAIN_ARGS=%$
set CLASSPATH=%DIRNAME%\..\conf

set CQLCLIENT_OPTS=%CQLCLIENT_OPTS% -Dlogback.configurationFile=%CQL_CONF_DIR%/logback.xml

for /r %DIRNAME%\..\lib %%b in (*.jar) do call :addcp %%~sb

@rem Execute client
"%JAVA_EXE%" %JAVA_OPTS% %CQLCLIENT_OPTS% -Dcql.dependency.jar=%CQL_DEPENDENCY_JAR_PATH% -classpath "%CLASSPATH%" %APP_MAINCLASS% %MAIN_ARGS%
if ERRORLEVEL 1 goto fail
goto end

:addcp
set CLASSPATH=%CLASSPATH%;%1

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:fail
exit /b 1
