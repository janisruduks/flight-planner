
# Flight Planner

Spring Boot REST API application that stores and retrieves flights. The application can store data in-memory or in a PostgreSQL database, and the storage method can be easily switched from the application's properties file. Liquibase is used to manage the database schema.

Check out [API Reference](#api-reference)
## Tech Stack

**Programming Language:** Java

**Framework:** Spring Boot

**Database:** PostgreSQL

**Database schema manager:** Liquibase

## Run Locally

Clone the project

```bash
git clone https://github.com/janisruduks/flight-planner
```

To run Database variant you need to set up postgres docker image

```bash
docker run --name flight-planner -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres
```

**Finally**, run the Spring Boot application using your preferred method (e.g., IDE or command line).

**Optional**

If you don't wish to set up docker then head to application properties and change store-type

```bash
cd flight-planner

cd src/main/resources/
```

edit application.properties with text editor

```bash
nano application.properties
```

and edit line

```bash
flight-planner.store-type=database
```
to

```bash
flight-planner.store-type=in-memory
```




## API Reference

### Client API `/api/`

#### Get flight by id

```http
GET /api/flights/${id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `Long` | **Required**. ID of flight to fetch |

#### Search for airport

```http
GET /api/aiports
```
Search for Airport that matches `search` param.
Ignores casing and searches for incomplete phrases.

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `search` | `String` | **Required**. Finds airport matching either `airport`, `country`,`city` |

#### Search for flight

```http
POST /api/flights/search
```

Searches for flight matching `from` (Airport name), `to` (Airport name), `date` (Flight departure date) 

Check out [Entites](#entities)

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `from` | `String` | **Required**. From airport name |
| `to` | `String` | **Required**. To airport name |
| `date` | `LocalDate` | **Required**. Date that matching flight will depart |

### Admin API `/admin-api/`

**Authentication:** The Flight Planner `/admin-api` uses basic authentication.

Username `codelex-admin`

Password `Password123`

#### Delete flight by id

```http
DELETE /admin-api/flights/${id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `Long` | **Required**. ID of flight to delete |

#### Get flight by id

```http
GET /admin-api/flights/${id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `Long` | **Required**. ID of flight to fetch |

#### Create Flight

```http
PUT /admin-api/flights
```
This references [Flight Object](#flight-object)

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `from` | `Airport` | **Required**. Airport from object |
| `to` | `Airport` | **Required**. Airport to object |
| `carrierName` | `String` | **Required**. Airline name |
| `departureTime` | `LocalDateTime` | **Required**. Departure date and time |
| `arrivalTime` | `LocalDateTime` | **Required**. Arrival date and time |

### Testing API `/testing-api/`

#### Clear all flights from either in-memory or postgresql

```http
POST /testing-api/flights/clear
```

# Entities

### Flight Object
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `from` | `Airport` | **Required**. Airport from object |
| `to` | `Airport` | **Required**. Airport to object |
| `carrierName` | `String` | **Required**. Airline name |
| `departureTime` | `LocalDateTime` | **Required**. Departure date and time |
| `arrivalTime` | `LocalDateTime` | **Required**. Arrival date and time |

### Airport Object

The `Airport` object represents an airport. It has the following properties:

| Property | Type | Description |
| :------- | :--- | :---------- |
| `country` | `String` | The country of the airport. |
| `city` | `String` | The city of the airport. |
| `airport` | `String` | The name of the airport. |
