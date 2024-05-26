# Feedback Tracking API

## Description
Simple Spring Boot project for the first FIAP Tech Challenge 
The objective of the project is to centralize and manage the feedback received by the organization, allowing efficient visualization and export of this data. The platform will be responsible for storing all feedback, where it can be managed and exported in PDF and CSV formats.

## Technologies Used
- **Spring Boot**: For building the RESTful API;
- **Spring Data JPA**: For data manipulation with the database;
- **Thymeleaf**: Template engine used for generating HTML templates that will be converted into PDFs;
- **Flying Saucer**: Library used for converting HTML templates into PDF documents.
- **H2 Database**: Used for testing and development.

## How to Use
To run the project locally:
1. Clone the repository;
2. Set up your preferred database in `application.properties` (H2 database will be used by default);
3. Run the project via Spring Boot;
4. Create new feedbacks;
   POST '/feedback'
   Body JSON example:
   {
    "id": null,
    "category": {
        "id": 1,
        "name": "Elogio"
    },
    "title": "Título",
    "content": "Conteúdo do feedback",
    "author": "Nome do Usuário",
    "viewed": false,
    "viewedOn": null,
    "adminComment": null
  }
5. Access the available endpoints to export data in the desired formats.

## Endpoints
- `/feedback/export/pdf` - Exports data to PDF.
- `/feedback/export/csv` - Exports data to CSV.
