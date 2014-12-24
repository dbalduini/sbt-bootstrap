#!/bin/sh
DIR=`echo $( cd "$( dirname "$0" )" && pwd )`
PROGRAM="$DIR/SbtProject.scala" 
exec scala $PROGRAM "$@"
