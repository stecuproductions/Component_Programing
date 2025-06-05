# Sudoku Game

This repository contains a simple Sudoku puzzle application written in Java. The project is split into two Maven modules:

- **ModelProject** – implements the core logic, model classes and database/file DAO for storing boards. Uses Java 21.
- **ViewProject** – JavaFX based GUI for playing the game. Requires Java 23.

## Requirements

- Java Development Kit (JDK) 23 or newer
- Maven 3.8+

## Building

From the repository root run:

```bash
mvn clean package
```

All unit tests will execute during the build and the module JARs will be created under each module's `target` directory.

## Running the GUI

To start the JavaFX interface use:

```bash
cd ViewProject
mvn javafx:run
```

## Database support

`ModelProject` can save and load boards from a PostgreSQL database. Provide connection details in a `.env` file with the variables `DB_URL`, `DB_USER` and `DB_PASSWORD`.

## Authors

- **Jakub Stec** (Album `250357`) – [250357@edu.p.lodz.pl](mailto:250357@edu.p.lodz.pl)
- **Julia Rogalska** (Album `250353`) – [250353@edu.p.lodz.pl](mailto:250353@edu.p.lodz.pl)

Licensed under the MIT License.

