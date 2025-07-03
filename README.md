# Prescripto - Doctor Appointment Booking Platform

[![Java](https://img.shields.io/badge/Java-17-blue.svg?style=for-the-badge&logo=java)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F.svg?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-61DAFB.svg?style=for-the-badge&logo=react)](https://reactjs.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-336791.svg?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.x-231F20.svg?style=for-the-badge&logo=apache-kafka)](https://kafka.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-20.10-2496ED.svg?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![AWS](https://img.shields.io/badge/AWS-Deployed-232F3E.svg?style=for-the-badge&logo=amazon-aws)](https://aws.amazon.com/)

Prescripto is a modern, full-stack, cloud-native application designed to streamline the process of booking doctor appointments. Built on a robust microservices architecture, it provides a seamless and secure experience for patients, doctors, and administrators.

## 🚀 Live Demo & Credentials

The application is deployed and publicly accessible.

| Platform      | URL                                                                  | Status                                                                                                   |
|---------------|----------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| **Frontend App**  | [**https://prescripto-webapp.netlify.app**](https://prescripto-webapp.netlify.app) |  **Running** |
| **Admin Panel**   | [**https://prescripto-admin-teal.vercel.app**](https://prescripto-admin-teal.vercel.app) | **Running**                                                                                              |

You can explore the admin panel using the following demo credentials:

-   **Username:** `nishant@outlook.com`
-   **Password:** `nishantrathore`

> **Note:** These are demo-only credentials for review purposes.

---

## ✨ Key Features

-   **Role-Based Access Control:** Separate interfaces and privileges for Patients, Doctors, and Admins.
-   **Secure Authentication:** JWT-based authentication and authorization for secure API access.
-   **Microservices Architecture:** A scalable and resilient backend built with 5 independent services.
-   **Asynchronous Communication:** Uses Apache Kafka for event-driven actions like user profile creation, ensuring a responsive system.
-   **Doctor Discovery:** Patients can browse a comprehensive list of doctors and filter by specialty.
-   **Seamless Appointment Booking:** An intuitive multi-step process for booking and confirming appointments.
-   **Integrated Payment Gateway:** Secure payment processing powered by Razorpay in test mode.
-   **Appointment Management:** Patients can view their upcoming and past appointments and cancel if needed.
-   **Cloud-Native Deployment:** Fully containerized with Docker and deployed on AWS App Runner for high availability and scalability.

## 📸 Screenshots

| Landing Page                                        | Appointment Booking & Payment                               |
| --------------------------------------------------- | ----------------------------------------------------------- |
| ![alt text](https://github.com/NISHANT-RATHORE/Doctor-Appointment-Project-2.O/blob/main/.github/assets/screenshot-landing.png?raw=true)         | ![](.github/assets/screenshot-payment.png)                  |
| **Payment Successful**                              | **Appointment Management**                                  |
| ![](.github/assets/screenshot-payment-successful.png) | ![](.github/assets/screenshot-appointments.png)             |

## 🛠️ Tech Stack & Architecture

Prescripto is built with a modern, decoupled tech stack designed for performance and scalability.

### System Architecture
The backend follows a microservices pattern, with an API Gateway as the single entry point. Services communicate synchronously via REST APIs and asynchronously via a Kafka message broker.

![](.github/assets/architecture-diagram.png)

### Technologies Used

| Category      | Technology                                                                                                    |
|---------------|---------------------------------------------------------------------------------------------------------------|
| **Frontend**  | `React.js`, `Material-UI (MUI)`, `Axios`                                                                      |
| **Backend**   | `Java 17`, `Spring Boot 3`, `Spring Cloud Gateway`, `Spring Security`, `Spring Data JPA`, `Lombok`                |
| **Database**  | `PostgreSQL`                                                                                                  |
| **Messaging** | `Apache Kafka`                                                                                                |
| **DevOps**    | `Docker`, `Docker Compose`, `AWS ECR`, `AWS App Runner`, `Netlify` (Frontend), `Vercel` (Admin)                 |

## ⚙️ Local Setup and Installation

To run this project locally, you will need to have the following prerequisites installed:
-   Git
-   Java 17 or higher
-   Apache Maven
-   Node.js and npm
-   Docker and Docker Compose
-   An IDE like IntelliJ IDEA or VS Code

### Steps

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/NISHANT-RATHORE/Doctor-Appointment-Project-2.0.git
    cd Doctor-Appointment-Project-2.0
    ```

2.  **Setup Backend Infrastructure:**
    The backend relies on PostgreSQL and Kafka. A `docker-compose.yml` file is provided in the `Backend` directory to easily spin up these services.
    ```bash
    cd Backend
    docker-compose up -d
    ```

3.  **Configure Environment Variables:**
    Each microservice in the `Backend` directory requires its own `application.properties` or `.env` file for database credentials, JWT secrets, and Kafka broker URLs. Refer to the respective configuration files for the required variables.

4.  **Run Backend Microservices:**
    You can run each of the 5 microservices (`ApiGateway`, `AuthService`, etc.) by:
    -   Opening each project in your IDE and running the main Spring Boot application class.
    -   Or by navigating to each service's directory and running: `mvn spring-boot:run`

5.  **Run Frontend Application:**
    ```bash
    cd ../Frontend
    npm install
    npm start
    ```
    The frontend will be available at `http://localhost:3000`.

6.  **Run Admin Panel:**
    Follow the same steps as the frontend for the admin panel project.

## 🗺️ API Gateway Endpoints

The API Gateway is the single entry point for all backend requests.

| Method | Endpoint Prefix       | Forwards To         | Description                         |
|--------|-----------------------|---------------------|-------------------------------------|
| `ANY`  | `/api/auth/**`        | `AuthService`       | Handles user login, signup, and authentication. |
| `ANY`  | `/api/doctor/**`      | `DoctorService`     | Manages doctor profiles and listings.  |
| `ANY`  | `/api/patient/**`     | `PatientService`    | Manages patient-specific data.       |
| `ANY`  | `/api/appointment/**` | `AppointmentService`| Handles booking, payment, and cancellation. |

---

Developed with ❤️ by **Nishant Rathore**.
