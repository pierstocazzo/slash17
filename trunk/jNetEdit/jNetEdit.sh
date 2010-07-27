#! /bin/bash

#! /bin/bash

if [ $NETKIT_HOME = NULL ]; then
	echo "jNetEdit - version 0.2.1"
	echo "Copyright 2010 Salvatore Loria"
	echo ""
	echo "WARNING: No Netkit installation founded, some features wont be available."
	echo "You can get a free copy of Netkit here: <http://wiki.netkit.org>"
else
	echo "jNetEdit - version 0.2.1"
	echo "Copyright 2010 Salvatore Loria"
	echo ""
	echo "Starting jNetEdit..."
fi

java -cp ./bin:./lib/piccolo.jar:./lib/piccolox.jar com.jnetedit.Main $NETKIT_HOME
