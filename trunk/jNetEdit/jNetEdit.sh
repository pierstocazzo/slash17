#! /bin/bash

if [ $NETKIT_HOME = NULL ]; then
	echo "jNetkit - version 0.1\n
		  Copyright 2010 Salvatore Loria\n\n
		  WARNING: No Netkit installation founded, some features wont be available.\n
		  You can get a free copy of Netkit here: <http://wiki.netkit.org>\n"
else
	echo "Starting jNetEdit v0.1"
fi

sudo java -cp ./bin:./lib/piccolo.jar:./lib/piccolox.jar com.jnetedit.Main $NETKIT_HOME
