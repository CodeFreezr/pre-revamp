@echo off
REM Setup Environment Variable for Overall ReleaseNotes Generator

set vShort=74
set vFull=0.7.4
set piFruit=Dragonfruit
set scopeRoot=D:\KM\.KRMA\scope
set kmRoot=D:\KM


Rem *** Set Values to System Environment Variables
setx nxt_vShort %vShort%
setx nxt_vFull %vFull%
setx nxt_piFruit %piFruit%
setx nxt_scopeRoot %scopeRoot%
setx nxt_kmRoot %kmRoot%



Rem *** Set Values to current Shell
set nxt_vShort=%vShort%
set nxt_vFull=%vFull%
set nxt_piFruit=%piFruit%
set nxt_scopeRoot=%scopeRoot%
set nxt_kmRoot=%kmRoot%

