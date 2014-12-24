::#!
@echo off
call scala %~dp0SbtProject.scala %*
goto :eof
::!#
pause