#!/bin/bash

# Check if the out/production/phase1 folder exists and create it if it doesn't
if [ ! -d ./out/production/phase1 ]; then
    mkdir ./out/production/phase1/
fi

# build the entire project and put it in the out/production/phase1 folder
echo "building project"
javac -d ./out/production/phase1 ./src/*/*.java

# run the project
echo "starting project"
java -cp ./out/production/phase1 viewer.Viewer