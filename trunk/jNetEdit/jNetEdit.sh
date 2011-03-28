#! /bin/bash

VER=$(cat version) 

echo "jNetEdit - version $VER"
echo "Copyright 2010 Salvatore Loria"
echo ""
echo "Starting jNetEdit..."

if test "${NETKIT_HOME+set}" != set ; then
	echo "WARNING: No Netkit installation founded, some features wont be available."
	echo "You can get a free copy of Netkit here: <http://wiki.netkit.org>"
else
 	echo "NETKIT_HOME=$NETKIT_HOME"
fi

java -cp ./bin:./lib/piccolo.jar:./lib/piccolox.jar com.jnetedit.Main $NETKIT_HOME
