# 🚀 FeedUp - AI Powered Feedback & Analytics System

FeedUp is a full-stack Spring Boot web application designed to collect, analyze, and manage user feedback with real-time analytics. It is deployed on AWS EC2 and connected with a MySQL/MariaDB database for persistent storage.

## 🌐 Live Deployment

👉 **http://3.25.217.196:8080**
*(Hosted on AWS EC2)*

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

✅ User feedback submission system
✅ Admin dashboard for monitoring feedback
✅ Session-based analytics tracking
✅ Rating and comment system
✅ Persistent database storage
✅ REST-based backend architecture
✅ Live cloud deployment on AWS

---

## ☁️ Deployment Architecture

Frontend + Backend (Spring Boot)
            ⬇
    AWS EC2 Instance
            ⬇
  MySQL / MariaDB (EC2)

---

## 🚀 How It Works

1. User submits feedback via web interface
2. Spring Boot backend processes the request
3. Data is stored in MySQL database
4. Admin can view analytics and responses
5. System runs continuously on AWS EC2

---

## 🛠️ Run Locally

PREREQUISITES:
- Java 17+
- Maven 3.6+
- MySQL 5.7+ or MariaDB

INSTALLATION STEPS:

# Clone the repository
git clone https://github.com/Shrecan/feedup.git
cd feedup

# Build the project
mvn clean install

# Run the Spring Boot application
mvn spring-boot:run

ACCESS THE APPLICATION:
Open your browser and navigate to:
http://localhost:8080

---

## ☁️ AWS Deployment Notes

RUNNING ON EC2:
The application is deployed on EC2 and runs in the background using:

nohup java -jar target/feedup-0.0.1-SNAPSHOT.jar > app.log 2>&1 &

SECURITY GROUP CONFIGURATION:
Ensure your AWS Security Group allows:
- Port: 8080 (HTTP access)
- Protocol: TCP
- Source: 0.0.0.0/0 (or restrict as needed)

VIEWING LOGS:

# View real-time logs
tail -f app.log

# Check if application is running
ps aux | grep java

---

## 📊 Database Configuration

APPLICATION.PROPERTIES:

spring.datasource.url=jdbc:mysql://localhost:3306/feedup
spring.datasource.username=root
spring.datasource.password=your_password_here

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

server.port=8080

DATABASE SETUP:

# Create database
CREATE DATABASE feedup;

# Use database
USE feedup;

The application will automatically create tables using Hibernate's ddl-auto=update setting.

---

## 📈 Future Improvements

🔜 Domain mapping (feedup.com)
🔜 HTTPS/SSL Certificate
🔜 Nginx reverse proxy setup
🔜 Docker containerization
🔜 CI/CD pipeline with GitHub Actions
🔜 Auto-restart system (systemd service)
🔜 Email notifications
🔜 Advanced analytics dashboard
🔜 API rate limiting
🔜 User authentication & authorization

---

## 📁 Project Structure

feedup/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/feedup/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       ├── model/
│   │   │       └── FeedupApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           ├── css/
│   │           ├── js/
│   │           └── index.html
│   └── test/
├── pom.xml
└── README.md

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (git checkout -b feature/AmazingFeature)
3. Commit your changes (git commit -m 'Add some AmazingFeature')
4. Push to the branch (git push origin feature/AmazingFeature)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👨‍💻 Author

Shree 🚀
Backend Developer | Java | Spring Boot | AWS Enthusiast
GitHub: @Shrecan

---

## ⭐ Project Status

✔️ Deployed on AWS EC2
✔️ Running 24/7 in background
✔️ Fully functional full-stack application
✔️ Real-time analytics tracking
✔️ Database persistence active

---

## 📞 Support & Contact

If you have any questions or issues, feel free to:
- Open an Issue on GitHub
- Contact via GitHub @Shrecan

---

⭐ If you find this project helpful, please consider giving it a star!
