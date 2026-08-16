[![Java CI with Maven](https://github.com/progys/figure-intersection/actions/workflows/maven.yml/badge.svg)](https://github.com/progys/figure-intersection/actions/workflows/maven.yml)

# Figure Intersection

A console application that lets you define geometric shapes, store them in a database, and query which shapes contain a given point.

This project was written as a solution to a Java developer job interview quiz. The original requirements are preserved in the [Quiz Requirements](#quiz-requirements) section below. It is kept here as a personal reference implementation.

## Features

- Define shapes interactively or from a file: **circle**, **triangle**, **donut**
- Query all shapes that contain a given point, along with each shape's surface area and the total combined area
- Point queries are backed by an in-memory **spatial grid index** (bounding-box broad phase) and run in **parallel** for good performance with large numbers of shapes
- Shapes are persisted in an **ObjectDB** database
- Additional commands: `list`, `clear`, `help`, `exit`
- Meaningful error messages for unexpected input; execution continues after an error
- Continuous integration via GitHub Actions
- Docker image based on the same Java 25 runtime

## Prerequisites

- Maven 3
- Java 25
- Internet connection (to download dependencies)

## Building and running

Build and run the tests:

```bash
mvn clean install
```

Run the application interactively:

```bash
mvn clean install exec:java -Dexec.mainClass="com.progys.interview.quiz.FigureIntersection"
```

Or use the provided scripts (Linux):

| Command                | Description                                              |
| ---------------------- | -------------------------------------------------------- |
| `./launch.sh`          | Runs the interactive application.                        |
| `./launchWithFile.sh`  | Loads shapes from `shapesInput.txt`, then starts the interactive session. |
| `./launch.sh -f FILE`  | Loads shapes from any input file.                        |

You can also build a runnable JAR and execute it:

```bash
mvn clean package
java -jar target/FigureIntersection-1.0-SNAPSHOT.jar -f shapesInput.txt
```

## Running with Docker

The provided `Dockerfile` builds and runs the application on the **same Java version (Java 25)**. No local JDK or Maven installation is required.

Build the image:

```bash
docker build -t figure-intersection .
```

Run the application interactively (the app needs an interactive terminal):

```bash
docker run -it --rm figure-intersection
```

Load shapes from a file (mount the file into the container):

```bash
docker run -it --rm -v "$PWD/shapesInput.txt:/app/shapesInput.txt:ro" figure-intersection -f shapesInput.txt
```

The shapes database is stored inside the container at `/app/db`. To keep it between runs, mount a host directory:

```bash
docker run -it --rm -v "$PWD/db:/app/db" figure-intersection
```

## Usage

Once running, type `help` to print the list of available commands with usage examples.

### Shapes

| Command                              | Description                                                                                   |
| ------------------------------------ | --------------------------------------------------------------------------------------------- |
| `circle <x> <y> <radius>`            | Circle with centre at (x, y) and the given radius.                                            |
| `triangle <x1> <y1> <x2> <y2> <x3> <y3>` | Triangle defined by its three vertices.                                                     |
| `donut <x> <y> <innerR> <outerR>`    | Donut with centre at (x, y), inner and outer radius (inner radius must be smaller than outer). |

Every created shape is assigned a unique identifier and printed back in a standardized form, for example:

```
=> Shape 1: circle with centre at (1.7, -5.05) and radius 6.9
```

### Other commands

| Command   | Description                                                    |
| --------- | -------------------------------------------------------------- |
| `x y`     | Prints all shapes containing the point (x, y), their surface areas, and the total area. A point is inside a donut if it is inside the outer circle but not inside the inner one. |
| `list`    | Prints all currently stored shapes.                            |
| `clear`   | Deletes all stored shapes.                                     |
| `help`    | Prints help with command examples.                             |
| `exit`    | Terminates the program.                                        |

### Example session

```
circle 1.7 -5.05 6.9
=> Shape 1: circle with centre at (1.7, -5.05) and radius 6.9

triangle 4.5 1 -2.5 -33 23 0.3
=> Shape 2: triangle at v0=(4.5, 1.0), v1=(-2.5, -33.0), v2=(23.0, 0.3)

donut 4.5 7.8 1.5 1.8
=> Shape 3: donut with centre at (4.5, 7.8) with inner radius 1.5 and outer radius 1.8

5.1 6.2
Shape list containing point (5.1, 6.2):
=> Shape 3: donut with centre at (4.5, 7.8) with inner radius 1.5 and outer radius 1.8 ; Shape area: 3.11
Found 1 shapes containing point (5.1, 6.2). Surface area combined:  3.1102
```

### File input

Shapes can also be loaded from a file with `-f <filename>` (see `shapesInput.txt` for an example of the format). The interactive session continues afterwards.

## Technologies and libraries

- **Java 25** — language features and Java streams
- **Maven** — build tool and dependency management
- **Java Streams** — parallel point query processing over index candidates
- **Guice** — dependency injection
- **ObjectDB + JPA** — shape persistence
- **Args4j** — command line argument parsing
- **JUnit 5, AssertJ, Mockito** — testing

## Project layout

```
src/main/java/com/progys/interview/quiz/
├── Application.java, FigureIntersection.java   # entry point
├── commands/                                    # command implementations
├── exceptions/                                  # custom exceptions
├── model/                                       # shape domain model (Circle, Triangle, Donut, Point)
├── parser/                                      # command and input parsing
├── persistence/                                 # ObjectDB storage
├── processor/                                   # console and file input handling
└── providers/                                   # Guice dependency injection module
src/test/java/com/progys/interview/quiz/         # unit tests
```

## Quiz Requirements

The original quiz instructions and requirements.

### Instructions

It is a full programming exercise whose outcome should be code that can be compiled, executed and tested with its own set of unit testing. We expect you to show your best technical skills applying the right patterns. You may provide an IDE project (Eclipse, IntelliJ) or, if preferred, a maven project to build source code.

### Requirements

1. When the user enters the name of a shape followed by the corresponding number of numeric parameters, define that shape and keep it in memory. The numbers may be of type double.

   Input command examples:

   ```
   circle 1.7 -5.05 6.9
   triangle 4.5 1 -2.5 -33 23 0.3
   donut 4.5 7.8 1.5 1.8
   ```

   For the circle, the numbers are the x and y coordinates of the centre followed by the radius. For the triangle it is the x and y coordinates of the three vertices (six numbers in total). For the donut it is the x and y of the centre followed by the two radii. In addition, every time such a line is entered, the application should give it a unique identifier and print it out in a standardized form, for example:

   ```
   => Shape 1: circle with centre at (1.7, -5.05) and radius 6.9
   ```

2. When the user enters a pair of numbers, the application should print out all the shapes that include that point in the (x, y) space, i.e. it should print out shape S if the given point is inside S. (A point is inside a donut shape if it is inside the outer circle but not inside the inner one.) It should also print out the surface area of each shape found, and the total area of all the shapes returned for a given point.

3. It should accept the commands "help" for printing instructions and "exit" for terminating the execution.

4. If the user enters anything unexpected (including errors like too few/many arguments, incorrect number format, etc.), it should print a meaningful error message and continue the execution.

5. Unit testing. Feel free to use any frameworks for unit testing.

6. Think about implementing it in a way which would perform well even for a very large number of shapes (e.g., tens of millions, but assuming it can still be held in the program memory).

### Extra requirements

7. Allow input from a file as well as the console. It should be possible, for example, to read a file with shape definitions and then to continue with an interactive session. Please provide a sample input file for testing.

8. Feel free to add additional shapes (e.g. square, rectangle, ellipsis) and operations (e.g. to delete a given shape). An advanced option could be to find all the shapes that overlap one that's named by the user.

9. Build file (ANT, Maven, Gradle, ...) project.

10. When calculating all figures that contain a specific point (x, y), use threading for parallelism.

11. Dependency injection.

12. Use any database to store the figures.
