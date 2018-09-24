#!/usr/bin/env bash
mvn clean
mvn install
mvn package
# cp target/annotation-generator-1.0-SNAPSHOT-jar-with-dependencies.jar [path to repo annotation_help_scripts]/sources/annotation-generator-v-3.jar