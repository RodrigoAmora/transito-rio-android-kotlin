#!/bin/bash

###################################
## Shell script to generate SHA1 ##
###################################

echo "Choose an option:"
echo "1) Get SHA1 Debug"
echo "2) Get SHA1 from keystore"
echo -e "\n\n"
echo "Type your choise: "
read number

if [ $number -eq 1 ]
then
	echo -e "\n\n";
	echo -e "\033[01;32m#######################\033[01;32m"
	echo -e "\033[01;32m### SHA1 generated! ###\033[01;32m"
	echo -e "\033[01;32m#######################\033[01;32m"
	echo -e "\n\n";

	keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
else
	echo "Type the path of the your file of .keystore: "
	read directoryKeyStore;

	echo "Type your alias: ";
	read alias;

	echo "Type your sotre pass: ";
	read storePasss;

	echo "Type your key pass: ";
	read keyPass;

	echo -e "\n\n";
	echo -e "\033[01;32m#######################\033[01;32m"
	echo -e "\033[01;32m### SHA1 generated! ###\033[01;32m"
	echo -e "\033[01;32m#######################\033[01;32m"
	echo -e "\n\n";

	keytool -list -v -keystore $directoryKeyStore -alias $alias -storepass $storePasss -keypass $keyPass
fi
