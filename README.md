## SWRLAPI Integration Tests

[![Dependency Status](https://www.versioneye.com/user/projects/56a278d09b5998003d000081/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56a278d09b5998003d000081)

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

#### License

The software is licensed under the [BSD 2-clause License](https://github.com/protegeproject/swrlapi-integration-tests/blob/master/license.txt).

#### Questions

If you have questions about this library, please go to the main
Protégé website and subscribe to the [Protégé Developer Support
mailing list](http://protege.stanford.edu/support.php#mailingListSupport).
After subscribing, send messages to protege-dev at lists.stanford.edu.
