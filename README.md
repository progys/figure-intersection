#Figure Intersection quiz
========================================
##About 
I wrote this program for the Java developer job interview. Requirements there provided.
Added this project to keep it as reference for myself.

##Prerequisities to run program
-Maven 3

-Java 8

-Internet connection to download artifacts

##Running program
To run program you can use **launch.sh** or **launchWithFile.sh** or just plain Maven:

-**launch.sh** - runs application with Maven.
-**launchWithFile.sh** - runs application with input file "shapesInput.txt". You can take a look at this file to find out how shapes are defined.
-**-f <filename>** - to invoke with any input file.

Type 'help' to get list of available commands in the program.

##Quiz Instruction
It is a full programming exercise whose outcome should be code that can be compiled, executed and tested with its own set of unit testing. We expect you to show your best technical skills applying the right patterns. You may provide an IDE project (Eclipse, IntelliJ) or, if preferred, a maven project to build source code.

##Technologies/Libraries used
- Java 8 streams for paralel execution
- JUnit for testing (minimal amount of tests for now)
- ObjectDB with JPA for shape storing
- Maven for building and dependencies
- Guice for dependency injection
- Args4j for command line argument parsing

##Quiz Requirements
Please write a console application with the following behavior:

1. When the user enters the name of a shape followed by the corresponding number of numeric parameters, define that shape and keep it in memory. The numbers may be of type double. 

Input Examples:

	circle 1.7 -5.05 6.9

	triangle 4.5 1 -2.5 -33 23 0.3

	donut 4.5 7.8 1.5 1.8

For the circle, the numbers are the x and y coordinates of the centre followed by the radius.
For the triangle it is the x and y coordinates of the three vertices (six numbers in total).
For the donut it is the x and y of the centre followed by the two radiuses.In addition, every time such a line is entered, the application should give it a unique identifier and print it out in a standardized form, for example:
=> shape 1: circle with centre at (1.7, -5.05) and radius 6.9

2. When the user enters a pair of numbers, the application should print out all the shapes that include that point in the (x, y) space, i.e. it should print out shape S if the given point is inside S. (A point is inside a donut shape if it is inside the outer circle but not inside the inner one.) It should also print out the surface area of each shape found, and the total area of all the shapes returned for a given point.

3. It should accept the commands “help” for printing instructions and “exit” for terminating the execution.

4. If the user enters anything unexpected (including errors like too few/many arguments, incorrect number format, etc.), it should print a meaningful error message and continue the execution.

4. Unit Testing. Feel free to use any frameworks for unit testing.

5. Think about implementing it in a way which would perform well even for a very large number shapes (e.g., tens of millions, but assuming it can still be held in the program memory).


##Extra requirements for Quiz
6. Allow input from a file as well as the console. It should be possible, for example, to read a file with shape definitions and then to continue with an interactive session. Please provide a sample input file for testing.

7. Feel free to add additional shapes (e.g. square, rectangle, ellipsis) and operations (e.g. to delete a given shape). An advanced option could be to find all the shapes that overlap one that’s named by the user.

8. Build file (ANT, Maven, Gradle, …) project 

9. When calculating all figures that contains a specific point (x, y), use threading for 
parallelism.

10. Dependency injection

11. Use any database to store the figures
