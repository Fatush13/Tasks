COPY /b/y "%~dp0\src" "%~dp0\dest" 

IF exist "%~dp0\dest\tool.exe" (
REN "%~dp0\dest\tool.exe" "launcher.exe"
)

DEL /q "%~dp0\src" 

"%~dp0\dest\launcher.exe"