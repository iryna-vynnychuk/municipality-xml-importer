## Municipality XML Importer ##
Backend application for importing municipalities and their parts from XML (ZIP archive) into a PostgreSQL database.

### Purpose ###
This project was created to practice backend development and data processing in Java.

### Description ###

This application downloads a ZIP file containing XML data, parses it using a streaming approach (StAX), and stores structured data into a relational database.

The project demonstrates working with:

- external data sources
- XML parsing
- database persistence
- containerized environments

### Technologies ###

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- StAX XML parser
- Mockito & JUnit 5
- Maven

### Features ###
- Download XML data from external URL
- Parse ZIP archive and extract XML
- Efficient streaming XML parsing (StAX)
- Store: municipalities (Obec), municipality parts (CastObce)
- REST API with Swagger UI

### How to run ###

Clean packages:

mvn clean package

**Docker:**

docker-compose up --build

Application will be available at: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui/index.html#/
### API ###
**Endpoints:**

- POST /obce/start
- GET /obce/allObci
- GET /obce/allCastObce
- DELETE /obce/deleteObceById
- DELETE /obce/deleteCastObceByKod

### Exemple request: ###

POST /obce/start?url=https://raw.githubusercontent.com/iryna-vynnychuk/xml-data/main/data.xml.zip

### Database shema: ###

**obec**
- id (UUID)
- kodObce
- nazevObce
- 
**castObce**
- id (UUID)
- kodCastiObce
- nazevCastiObce
- kodObce

### Implementation Details ###
- Uses StAX parser for memory-efficient XML processing
- Handles ZIP streams without full extraction to disk
- Ensures uniqueness of entities via database constraints
- Maps relationships between Obec and CastObce

### Docker Setup ###

Includes:

- Spring Boot application
- PostgreSQL database

Configured via docker-compose.yml

### Notes ### 

- Data is loaded from external XML source
- Project created as backend practice / technical task
- Focus on clean structure and data processing logic

### Tests ###
Includes:
-   XML parser unit tests
-   Service layer tests (Obec Service)

### Future Improvements ###
- Validation of input XML
- Error handling improvements
- Pagination for data retrieval
- Unit and integration tests
- Deployment (e.g. Render / Railway)

**Author**

Created by Iryna Vynnychuk.
