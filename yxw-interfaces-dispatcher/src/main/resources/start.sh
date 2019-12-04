#!/bin/sh
base_path=/Users/YangXuewen/Downloads/yxw-interfaces-dispatcher
provider_classpath=$CLASSPATH:.;
lib_jars=$base_path/lib/*.jar;
project_path=
for i  in $lib_jars
do
   CLASSPATH=$CLASSPATH:$i
done
   CLASSPATH=$CLASSPATH:$provider_classpath:$base_path/yxw-interfaces-dispatcher.jar;
export CLASSPATH
#export JAVA_HOME=/usr/java/jdk1.7.0_71
cd $base_path
#nohup  $JAVA_HOME/bin/java -server -Xmx2048M -Xms2048M -XX:PermSize=512M -XX:MaxPermSize=512M -Xss5M com.yxw.bin.StartUpDispatcher &
nohup  $JAVA_HOME/bin/java -server com.yxw.bin.StartUpDispatcher &