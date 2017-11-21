#!/bin/bash

# Check if the out/production/phase1 folder exists and create it if it doesn't
if [ ! -d ./out/production/phase1 ]; then
    mkdir ./out/production/phase1/
fi

#Generate the javaDocs for the project
echo "generating javaDoc"
if [ ! -d ./javaDoc ]; then
	mkdir ./javaDoc
fi
javadoc -d ./javaDoc -cp src/ model viewer

# build the entire project and put it in the out/production/phase1 folder
echo "building project"
javac -d ./out/production/phase1 ./src/*/*.java

# run the project
echo "starting project"
java -cp ./out/production/phase1 viewer.Viewer
