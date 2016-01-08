## SWRLAPI Integration Tests

Integration tests for the [SWRLAPI](https://github.com/protegeproject/swrlapi).

### Downloading

You can get a copy of the latests JAR from the [project's GitHub Release area](https://github.com/protegeproject/swrlapi-integration-tests/releases).

This JAR will have a name of the form:

    swrlapi-integration-tests-${version}.jar

#### Building 

To build and run the tests you must have the following items installed:

+ Apache's [Maven](http://maven.apache.org/index.html)
+ A tool for checking out a [Git](http://git-scm.com/) repository
+ [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

Get a copy of the latest code:

    git clone https://github.com/protegeproject/swrlapi-integration-tests.git 

Change into the swrlapi-integration-tests directory:

    cd swrlapi-integration-tests 

Then build and run the tests:

    mvn verify

