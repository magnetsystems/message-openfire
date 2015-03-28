#!/bin/bash

#
# $RCSfile$
# $Revision: 1194 $
# $Date: 2005-03-30 13:39:54 -0300 (Wed, 30 Mar 2005) $
#

# tries to determine arguments to launch openfire

# OS specific support.  $var _must_ be set to either true or false.

# Change the following if it has been changed to use another port
source ../conf/startup.properties

PROG="mmx"
PID_PATH="./"
pid=

openfire_start() {
	cygwin=false;
	darwin=false;
	linux=false;
	case "`uname`" in
		CYGWIN*) cygwin=true ;;
		Darwin*) darwin=true
			if [ -z "$JAVA_HOME" ] ; then
				JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Home
			fi
			;;
		Linux*) linux=true
			if [ -z "$JAVA_HOME" ]; then
				shopt -s nullglob
				jdks=`ls -r1d /usr/java/j* /usr/lib/jvm/* /usr/bin/j* 2>/dev/null`
				for jdk in $jdks; do
					if [ -f "$jdk/bin/java" ]; then
						JAVA_HOME="$jdk"
						break
					fi
				done
			fi
			;;
	esac

	#if openfire home is not set or is not a directory
	if [ -z "$OPENFIRE_HOME" -o ! -d "$OPENFIRE_HOME" ]; then

		if [ -d /opt/openfire ] ; then
			OPENFIRE_HOME="/opt/openfire"
		fi

		if [ -d /usr/local/openfire ] ; then
			OPENFIRE_HOME="/usr/local/openfire"
		fi

		if [ -d ${HOME}/opt/openfire ] ; then
			OPENFIRE_HOME="${HOME}/opt/openfire"
		fi

		#resolve links - $0 may be a link in openfire's home
		PRG="$0"
		progname=`basename "$0$"`

		# need this for relative symlinks

		# need this for relative symlinks
		while [ -h "$PRG" ] ; do
			ls=`ls -ld "$PRG"`
			link=`expr "$ls" : '.*-> \(.*\)$'`
			if expr "$link" : '/.*' > /dev/null; then
				PRG="$link"
			else
				PRG=`dirname "$PRG"`"/$link"
			fi
		done

		#assumes we are in the bin directory
		OPENFIRE_HOME=`dirname "$PRG"`/..

		#make it fully qualified
		OPENFIRE_HOME=`cd "$OPENFIRE_HOME" && pwd`
	fi
	OPENFIRE_OPTS="${OPENFIRE_OPTS} -DopenfireHome=\"${OPENFIRE_HOME}\""


	# For Cygwin, ensure paths are in UNIX format before anything is touched
	if $cygwin ; then
		[ -n "$OPENFIRE_HOME" ] &&
			OPENFIRE_HOME=`cygpath --unix "$OPENFIRE_HOME"`
		[ -n "$JAVA_HOME" ] &&
			JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
	fi

	#set the OPENFIRE_LIB location
	OPENFIRE_LIB="${OPENFIRE_HOME}/lib"
	OPENFIRE_OPTS="${OPENFIRE_OPTS} -Dopenfire.lib.dir=\"${OPENFIRE_LIB}\""

	# Override with bundled jre if it exists.
	if [ -f "$OPENFIRE_HOME/jre/bin/java" ]; then
		JAVA_HOME="$OPENFIRE_HOME/jre"
		JAVACMD="$OPENFIRE_HOME/jre/bin/java"
	fi

	if [ -z "$JAVACMD" ] ; then
		if [ -n "$JAVA_HOME"  ] ; then
			if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
				# IBM's JDK on AIX uses strange locations for the executables
				JAVACMD="$JAVA_HOME/jre/sh/java"
			else
				JAVACMD="$JAVA_HOME/bin/java"
			fi
		else
			JAVACMD=`which java 2> /dev/null `
			if [ -z "$JAVACMD" ] ; then
				JAVACMD=java
			fi
		fi
	fi

	if [ ! -x "$JAVACMD" ] ; then
		echo "Error: JAVA_HOME is not defined correctly."
		echo "  We cannot execute $JAVACMD"
		exit 1
	fi

	if [ -z "$LOCALCLASSPATH" ] ; then
		LOCALCLASSPATH=$OPENFIRE_LIB/startup.jar
	else
		LOCALCLASSPATH=$OPENFIRE_LIB/startup.jar:$LOCALCLASSPATH
	fi

	# For Cygwin, switch paths to appropriate format before running java
	if $cygwin; then
		if [ "$OS" = "Windows_NT" ] && cygpath -m .>/dev/null 2>/dev/null ; then
			format=mixed
		else
			format=windows
		fi
		OPENFIRE_HOME=`cygpath --$format "$OPENFIRE_HOME"`
		OPENFIRE_LIB=`cygpath --$format "$OPENFIRE_LIB"`
		JAVA_HOME=`cygpath --$format "$JAVA_HOME"`
		LOCALCLASSPATH=`cygpath --path --$format "$LOCALCLASSPATH"`
		if [ -n "$CLASSPATH" ] ; then
			CLASSPATH=`cygpath --path --$format "$CLASSPATH"`
		fi
		CYGHOME=`cygpath --$format "$HOME"`
	fi

	# add a second backslash to variables terminated by a backslash under cygwin
	if $cygwin; then
		case "$OPENFIRE_HOME" in
			*\\ )
				OPENFIRE_HOME="$OPENFIRE_HOME\\"
				;;
		esac
		case "$CYGHOME" in
			*\\ )
				CYGHOME="$CYGHOME\\"
				;;
		esac
		case "$LOCALCLASSPATH" in
			*\\ )
				LOCALCLASSPATH="$LOCALCLASSPATH\\"
				;;
		esac
		case "$CLASSPATH" in
			*\\ )
				CLASSPATH="$CLASSPATH\\"
				;;
		esac
	fi

	openfire_exec_command="nohup $JAVACMD $OPENFIRE_OPTS -classpath \"$LOCALCLASSPATH\" -jar \"$OPENFIRE_LIB/startup.jar\" >mmx-server.out  2>&1 &"
	eval $openfire_exec_command
	pid=$!
}

check_port() {
	if nc -z 127.0.0.1 $1 > /dev/null; then
		echo "ERROR: TCP port $1 is already in use, cannot start messaging server"
		exit 1
	else
		echo "validated port $1"
	fi
}

check_port_list() {
	portList=( "$@" );
	for i in "${portList[@]}"; do
		check_port $i
	done
}

check_cmd() {
	type "$1" &> /dev/null;
}

check_java() {
	if check_cmd java ; then
		echo "java is installed"
	else
		echo "java is not installed"
		echo "MMX server needs java version 1.6 OR higher"
		echo "Please check https://java.com/en/download/"
		exit 1
	fi
}

start() {
	if [ -e "$PID_PATH/$PROG.pid" ]; then
		## Program is running, exit with error.
		echo "Error! $PROG is already running or you have a stale pid file. If $PROG is not running delete $PID_PATH/$PROG.pid file and restart" 1>&2
		exit 1
	else
		check_port_list $xmppPort $xmppPortSecure $httpPort $httpPortSecure $mmxAdminPort $mmxAdminPortSecure $mmxPublicPort $mmxPublicPortSecure
		check_java
		openfire_start
		touch "$PID_PATH/$PROG.pid"
		echo $pid >> $PID_PATH/$PROG.pid
	fi
}
stop() {
	if [ -e "$PID_PATH/$PROG.pid" ]; then
		pid=$(<$PID_PATH/$PROG.pid)
		kill -9 $pid

		rm "$PID_PATH/$PROG.pid"

		echo "$PROG stopped"
	else
		## Program is not running, exit with error.
		echo "stop:$PROG :  $PROG is not running" 1>&2
	fi
}

print_usage() {
	echo "Usage: mmx-server.sh [-p] {start|stop|restart}" >&2
	echo >&2
	echo "Start, stop, or restart the Magnet Messaging server." >&2
	echo >&2
	echo "Options:" >&2
	echo "    [-p]    No port check when starting" >&2
	echo >&2
}

if [ "$#" == 0 ] || [ "$#" -gt 2 ] ; then
	print_usage
	exit 1
fi

while getopts "p h" opt; do
	case $opt in
		h)
			print_usage
			exit 1
			;;
		\?)
			print_usage
			exit 1
			;;
	esac
done

shift $((OPTIND-1))

case "$1" in
	start)
		start
		exit 0
		;;
	stop)
		stop
		exit 0
		;;
	restart)
		stop
		start
		exit 0
		;;
	**)
		print_usage
		exit 1
		;;
esac


