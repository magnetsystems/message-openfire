#!/bin/bash

# Deploy openfire.jar to local maven repository
CURDIR=`pwd`
ARTIFACT_VERSION="1.2.3"
FILE="${CURDIR}/target/openfire/lib/openfire.jar"

cd ${CURDIR}/target
zip -r mmx-openfire.zip openfire
FILE_ZIP="${CURDIR}/target/mmx-openfire.zip"

mvn \
  -Dfile=$FILE \
  -DgroupId=com.magnet.mmx.ext \
  -DartifactId=openfire \
  -Dversion=$ARTIFACT_VERSION \
  -Dpackaging=jar \
  -DgeneratePom=true \
  install:install-file

mvn \
  -Dfile=$FILE_ZIP \
  -DgroupId=com.magnet.mmx.ext \
  -DartifactId=mmx-openfire \
  -Dversion=$ARTIFACT_VERSION \
  -Dpackaging=zip \
  -DgeneratePom=true \
  install:install-file
