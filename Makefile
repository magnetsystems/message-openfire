.PHONY: all clean dbkg eclipse

all: build-openfire

# Can not use 'build' as target name, because there is a
# directory called build
build-openfire:
	cd build && ant

clean:
	cd build && ant clean

dpkg:
	cd build && ant installer.debian

eclipse: .settings .classpath .project

.settings:
	ln -s build/eclipse/settings .settings

.classpath:
	ln -s build/eclipse/classpath .classpath

.project:
	ln -s build/eclipse/project .project

mvninstall: clean build-openfire packageInstall

packageInstall:
	./package.sh && cd target && mvn install:install-file -Dfile=openfire.zip -DgroupId=com.magnet.mmx.ext -DartifactId=mmx-openfire -Dversion=0.8.1-SNAPSHOT -Dpackaging=zip -DgeneratePom=true && cd openfire/lib && mvn install:install-file -Dfile=openfire.jar -DgroupId=com.magnet.mmx.ext -DartifactId=openfire -Dversion=0.8.1-SNAPSHOT -Dpackaging=jar 
