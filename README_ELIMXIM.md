# Drones

## Supporting information

Developer environment:

- Windows 11
- JDK 20
  ```shell
  java version "20.0.1" 2023-04-18
  Java(TM) SE Runtime Environment (build 20.0.1+9-29)
  Java HotSpot(TM) 64-Bit Server VM (build 20.0.1+9-29, mixed mode, sharing)
  ```
- Docker Desktop v4.26.1
- Intellij IDEA 2023.1.3 (Community Edition)

Environment requirements:

- JDK 20
- Docker

There is no need to prepare a database because the project runs on test containers,
reducing prep work for code reviewers. The service listens on port **8084**.

To build the project from the console:

_Unix_

```shell
./gradlew build
```

_Windows_

```shell
gradlew.bat build
```

To run the application from the console:

_Unix_

```shell
./gradlew bootRun
```

_Windows_

```shell
gradlew.bat bootRun
```

To run tests from the console:

_Unix_

```shell
./gradlew test
```

_Windows_

```shell
gradlew.bat test
```

## Additional information

- the project uses _gradle wrapper_, so there is no need to use the installed one
- the project uses OAS to generate HTTP API and DTO entities
- the project contains the `postman.json` postman collection with prepared HTTP request
- `src/main/resources/db/changelog/db.changelog-dev.xml` contains prepared data
- application's logs are in the `logs/` folder of the project root directory

## Assumptions

- *Drone.weightLimit* depends on *Drone.model*
- *Drone.batteryCapacity* is a percentage of the battery's ideal value and depends on *Drone.model*
- *Drone.batteryCapacity* depends on *Drone.model*
- **Drone.serialNumber** is unique to all drones
- **Medication.code** is unique to all medications
- Available drones are drones in **IDLE** state
- Registered drone is set into **IDLE** state

Drone Model characteristics:

| Drone Model   | Weight limit (gr) | Battery capacity (%) |
|---------------|-------------------|----------------------|
| Lightweight   | from 1 to 100     | from 20 to 40        |
| Middleweight  | from 101 to 200   | from 41 to 60        |
| Cruiserweight | from 201 to 350   | from 61 to 80        |
| Heavyweight   | from 351 to 500   | from 81 to 100       |