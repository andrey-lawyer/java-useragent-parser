# User-Agent Parser

This project is a customizable tool for parsing User-Agent strings to extract detailed information about browsers, versions, and other key attributes. It is specifically designed to handle a wide variety of User-Agent patterns.

## Table of Contents

- [Technologies](#technologies)
- [Installation](#installation)
- [Running the Project](#running-the-project)
- [API Reference](#api-reference)

## Technologies

- Spring Boot (Java)
- Docker
- Redis

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/andrey-lawyer/java-useragent-parser
   ```

## Running the Project

To start the project, run:

```bash
docker-compose up
```
Once the containers are up and running, you can access the application at the following addresses:

http://localhost:8080


## API Reference

#### Endpoint to parse user agent from the "User-Agent" header

```http
  GET /api/user-agent/parse-header
```


####  Endpoint to parse user agent from query parameter 

```http
  GET /api/user-agent/parse-query
```
query parameters:
- userAgent (required): The User-Agent string to parse.











