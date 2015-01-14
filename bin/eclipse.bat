@echo off

cd %~dp0
cd ..
call mvn eclipse:clean -Dwtpversion=2.0 eclipse:eclipse
cd bin

pause