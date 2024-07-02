# RESTful API Service Example

This example demonstrates the basic technologies/methods and code organization/style 
for developing a typical RESTful API service.

---
1. [Technology Stack](#technology-stack)  
2. [Domain Example](#domain-example)  
   2.1. [Requirements](#requirements)  
   2.2. [Functional requirements](#functional-requirements)  
   2.3. [Non-functional requirements](#non-functional-requirements)  
3. [Development](#development)
---

## Technology Stack

- Core
  - Java (or Kotlin)
  - Spring Boot
- REST API
  - Spring Web MVC
  - Open API
  - Swagger Codegen
- Data Management
  - Spring Data JPA / Hibernate
  - Liquibase
- Logging
  - Slf4j + Logback
- Jobs Scheduling
  - Quartz Scheduler
- DevOps
  - Gradle + Kotlin extension
  - Docker
- Testing
  - JUnit 5
  - Testcontainers
- Other tools
  - Mapstruct
  - Lombok

## Domain Example

There is a major new technology that is destined to be a disruptive force in the field 
of transportation: **the drone**. Just as the mobile phone allowed developing countries 
to leapfrog older technologies for personal communication, the drone has the potential 
to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in 
locations with difficult access.

### Requirements

We have a fleet of **10 drones**. A drone is capable of carrying devices, other than cameras, 
and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the drones 
(i.e. **dispatch controller**). The specific communicaiton with the drone is outside the scope of this task. 

The service should allow:
- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone; 
- checking available drones for loading;
- check drone battery level for a given drone;

> Feel free to make assumptions for the design approach.

#### Functional requirements

- There is no need for UI;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones battery levels and create history/audit event log for this.

#### Non-functional requirements

- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Any data required by the application to run (e.g. reference tables, dummy data) must be preloaded in the database.
- JUnit tests are mandatory;
- Advice: Show us how you work through your commit history.

## Development

### Assumptions

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


### Launching

Environment requirements:

- JDK _20_ or above
  ```shell
  java version "20.0.1" 2023-04-18
  Java(TM) SE Runtime Environment (build 20.0.1+9-29)
  Java HotSpot(TM) 64-Bit Server VM (build 20.0.1+9-29, mixed mode, sharing)
  ```
- Docker Desktop `v4.26.1`

There is no need to prepare a database because the project runs on test containers,
reducing prep work for code reviewers. The service listens on port **8084**.

To build the project from the console:

```shell
./gradlew build
```

To run the application:

```shell
./gradlew bootRun
```

To run tests from the console:

```shell
./gradlew test
```

### Additional information

- the project uses _gradle wrapper_, so there is no need to use the installed one
- the project uses OAS to generate HTTP API and DTO entities
- the project contains the `postman.json` postman collection with prepared HTTP request
- `src/main/resources/db/changelog/db.changelog-dev.xml` contains prepared data
- application's logs are in the `logs/` folder of the project root directory