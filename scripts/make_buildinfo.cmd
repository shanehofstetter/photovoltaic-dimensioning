@echo off
"C:/Program Files (x86)/Git/bin/git.exe" describe --tags --long > temp.txt
set /p Version=<temp.txt

for /F "usebackq tokens=1,2 delims==" %%i in (`wmic os get LocalDateTime /VALUE 2^>NUL`) do if '.%%i.'=='.LocalDateTime.' set ldt=%%j
set ldt=%ldt:~0,4%-%ldt:~4,2%-%ldt:~6,2% %ldt:~8,2%:%ldt:~10,2%:%ldt:~12,6%
echo %ldt% > temp.txt
set /p DateTime=<temp.txt
del temp.txt

(
echo package ch.abbts.pvdimension;
echo /*
echo AUTO-GENERATED CLASS
echo */
echo public class BuildInfo {
echo    public static final String VERSION_NUMBER = "%Version%";
echo    public static final String BUILD_TIME = "%DateTime%";
echo }
) > src/ch/abbts/pvdimension/BuildInfo.java

