## SWRLAPI Integration Tests

Integration tests for the [SWRLAPI](https://github.com/protegeproject/swrlapi).

#### Building and Running the Tests

To build and run the tests you must have the following items installed:

+ [Java 11](https://www.oracle.com/java/technologies/downloads/archive/)
+ A tool for checking out a [Git](https://git-scm.com/) repository
+ Apache's [Maven](https://maven.apache.org/index.html)
+ A Protégé (5.6.0 or higher) distribution. Download [here](https://protege.stanford.edu/products.php#desktop-protege).

Get a copy of the latest code:

    git clone https://github.com/protegeproject/swrlapi-integration-tests.git 

Change into the swrlapi-integration-tests directory:

    cd swrlapi-integration-tests 

Then build and run the tests:

    mvn verify

#### License

This software is licensed under the [BSD 2-clause License](https://github.com/protegeproject/swrlapi-integration-tests/blob/master/license.txt).

#### Questions

If you have questions about this software, please go to the main
Protégé website and subscribe to the [Protégé Developer Support
mailing list](http://protege.stanford.edu/support.php#mailingListSupport).
After subscribing, send messages to protege-dev at lists.stanford.edu.
