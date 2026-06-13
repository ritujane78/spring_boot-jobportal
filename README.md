# JobPortal

A full-stack Job Portal application built with **Spring Boot 4**, **Spring Security**, **Thymeleaf**, **JPA/Hibernate**, and **MySQL**, designed to connect recruiters and job seekers through an intuitive web platform.

## Overview

JobPortal provides separate dashboards and workflows for recruiters and candidates. Recruiters can manage job postings and review applicants, while candidates can create profiles, search for opportunities, save jobs, and submit applications.

The project follows a traditional Spring MVC architecture with Thymeleaf-based server-side rendering and role-based access control.

## Features

### Recruiter Features
- Recruiter registration and authentication
- Recruiter profile management
- Create, edit, and manage job postings
- View applications for posted jobs
- Access candidate profiles and resumes
- Recruiter dashboard with job management tools

### Candidate Features
- Candidate registration and authentication
- Candidate profile creation and maintenance
- Search and browse available jobs
- Save jobs for later review
- Apply to jobs directly through the portal
- Candidate dashboard for tracking activity

## Technology Stack

### Backend
- Java
- Spring Boot 4
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

### Frontend
- Thymeleaf
- HTML5
- CSS3
- JavaScript
- Bootstrap

### Database
- MySQL

## Project Structure

```text
spring_boot-jobportal/
├── .mvn/
├── screenshots/
├── src/
│   ├── main/
│   │   ├── java/
│   │   ├── resources/
│   │   │   ├── static/
│   │   │   └── templates/
│   └── test/
├── pom.xml
├── mvnw
└── mvnw.cmd
```

## Getting Started

### Prerequisites

- Java 21+
- Maven
- MySQL

### Installation

1. Clone the repository

```bash
git clone https://github.com/ritujane78/spring_boot-jobportal.git
```

2. Navigate to the project directory

```bash
cd spring_boot-jobportal
```

3. Configure database settings in:

```properties
src/main/resources/application.properties
```

4. Run the application

```bash
./mvnw spring-boot:run
```

Or on Windows:

```cmd
mvnw.cmd spring-boot:run
```

## Screenshots

Application screenshots are available in the `screenshots/` directory.

## Learning Outcomes

This project demonstrates:

- Spring Boot application development
- MVC architecture
- Authentication and authorization
- Database design and ORM mapping
- File upload/download functionality
- Server-side rendering with Thymeleaf
- Full-stack Java web development
