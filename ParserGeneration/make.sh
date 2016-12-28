#!/bin/bash

jjtree pfft.jjt
javacc pfft.jj
javac *.java
java Parser < infile

echo "#############################################################"

rm *.class