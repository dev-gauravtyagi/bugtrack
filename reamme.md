# Bug Tracking App

BugTrack is a backend service for project management.

### Tech

It uses:

* Java8
* Spring boot
* Maven
* lombok
* mysql

### Swagger UI
To view our REST API go to {HOST}:{PORT}/swagger-ui.html
> http://localhost:8080/swagger-ui.html

OR 

Postman collection file with the name bug track.postman_collection.json also present
you can import that as well

## How to run

->  Please update db username and password in application.yml file
->  create database with the name bugtrack

### Initial Data loading

-> For loading sample Data in System please set property
bugtrack:
  loaddata: true

-> Set false after running project once  

bugtrack:
  loaddata: false