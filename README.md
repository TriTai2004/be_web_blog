Blog REST API

A simple Blog System REST API built with Spring Boot.
The system allows users to register, authenticate using JWT, and create, manage blog posts.
This project is created for learning Spring Boot, RESTful API design, authentication, and backend development.

Tech Stack
- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- SQL Server

Features:
- User registration and login with JWT authentication
- User account management
- Create, update, delete, and view blog articles
- Manage article categories
- Add and manage comments on articles
  
Run the Project
1. Clone repository
git clone https://github.com/TriTai2004/be_web_blog.git
2. Configure database
Update application.properties:
  spring.application.name=demo
  spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=namedb;encrypt=true;trustServerCertificate=true
  spring.datasource.username=yourname
  spring.datasource.password=yourpass
  spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
  spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true

  cloudinary.cloud_name=your_cloud_name
  cloudinary.api_secret=your_api_secret
  cloudinary.api_key=your_api_key

3. Run project
mvn spring-boot:run
Or run the main class:
DemoApplication.java

Author
Nguyen Tri Tai
