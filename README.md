You need to have java8 jdk install in your PC. If you are using any jdk above java8, you need to downgrade to java8. Otherwise, the test will fail in build process dues to the issue with powermock on one of the class.

Checkout Unix/Linux instructions

In Terminal, cd to your workspace directory(${workspace}) then checkout the project.

cd ~/${workspace}

git clone https://github.com/alexandtse/sainsburys.git

When the clone is completed, then build the project

cd ~/${workspace}/sainsburys

./gradlew build

It will build and run all the unit test in the project. It also created a jar file that you can execute the application.

cd ~/${workspace}/sainsburys/build/libs


Execute java application by using the following command.

java -jar assessment-0.0.1-SNAPSHOT.jar 

