# 🚀 FeedUp - AI Powered Feedback & Analytics System

FeedUp is a full-stack Spring Boot web application designed to collect, analyze, and manage user feedback with real-time analytics. It is deployed on AWS EC2 and connected with a MySQL/MariaDB database for persistent storage.

---

## 🌐 Live Deployment

👉 http://3.25.217.196:8080

(Hosted on AWS EC2)

---

## ⚙️ Tech Stack

### Backend
- Java 17 (Spring Boot)
- Spring MVC
- Spring Data JPA (Hibernate)
- Maven

### Database
- MySQL / MariaDB

### Frontend
- HTML
- CSS
- JavaScript

### Deployment
- AWS EC2 (Amazon Linux)
- Nginx (optional future setup)
- nohup background process

---

## 📌 Features

- User feedback submission system
- Admin dashboard for monitoring feedback
- Session-based analytics tracking
- Rating and comment system
- Persistent database storage
- REST-based backend architecture
- Live cloud deployment on AWS

---

## ☁️ Deployment Architecture

Frontend + Backend (Spring Boot)  
⬇  
AWS EC2 Instance  
⬇  
MySQL / MariaDB (Local EC2 DB)

---

## 🚀 How It Works

1. User submits feedback via web interface  
2. Spring Boot backend processes the request  
3. Data is stored in MySQL database  
4. Admin can view analytics and responses  
5. System runs continuously on AWS EC2

---

## 🛠️ Run Locally

```bash
git clone https://github.com/your-username/feedup.git
cd feedup
mvn clean install
mvn spring-boot:run\
Open in browser:

http://localhost:8080
☁️ AWS Deployment Notes

Application is deployed on EC2 and runs in background using:

nohup java -jar target/feedup-0.0.1-SNAPSHOT.jar > app.log 2>&1 &

Security Group must allow:

Port: 8080 (HTTP access)
📊 Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/feedup
spring.datasource.username=root
spring.datasource.password=********

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
server.port=8080
📈 Future Improvements
Domain mapping (feedup.com)
HTTPS (SSL)
Nginx reverse proxy setup
Docker containerization
CI/CD pipeline with GitHub Actions
Auto-restart system (systemd service)
👨‍💻 Author

Shree 🚀
Backend Developer | Java | Spring Boot | AWS Enthusiast

⭐ Project Status

✔ Deployed on AWS EC2
✔ Running 24/7 in background
✔ Fully functional full-stack application
