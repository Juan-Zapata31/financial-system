<div align="center">

<br/>

<p>
  <img src="https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white"/>
  <img src="https://img.shields.io/badge/REST_API-005C84?style=for-the-badge&logo=fastapi&logoColor=white"/>
  <img src="https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge"/>
</p>

<br/>

> **RESTful Web API for a banking system** — developed by **Juan Zapata**  
> Manage accounts, customers, and transactions through a Java + Spring Boot backend architecture.

<br/>

---

</div>

## 📌 What is this API?

**Bank API** is a **RESTful Web API** designed to simulate the core operations of a real banking system. A Web API (Application Programming Interface) exposes HTTP endpoints that allow clients — mobile apps, web frontends, or other services — to communicate with the backend in a standardized way, exchanging data in **JSON** format.

This API allows you to manage:

- 👤 **Customers** — registration and lookup of account holders
- 🏦 **Bank Accounts** — creation and management of accounts (savings, checking)
- 💸 **Transactions** — deposits, withdrawals, and transfers between accounts
- 📊 **Balance Queries** — real-time financial status of any account

The architecture follows the **client-server model**: the backend exposes resources and clients consume them via `GET`, `POST`, `PUT`, and `DELETE` HTTP requests.

---

## 🗂️ Class Diagram

<div align="center">
  <img src="./Clases%20Banco.drawio.png" alt="Banking system class diagram" width="80%"/>
</div>

---

## 🛠️ Technologies Used

| Technology | Version | Description |
|---|---|---|
| ☕ **Java** | 17+ | Main backend language |
| 🍃 **Spring Boot** | 3.x | Framework for building web apps and REST APIs quickly |
| 🔗 **Spring Web (MVC)** | — | REST controllers and HTTP request handling |
| 🗄️ **Spring Data JPA** | — | Data persistence abstraction with repositories |
| 📦 **Maven** | 3.x | Dependency management and project build lifecycle |
| 🔒 **Spring Security** *(optional)* | — | Endpoint authentication and authorization |
| 🗃️ **MySQL** | — | Relational database for data persistence |

---

## 🚀 Getting Started

```bash
# 1. Clone the repository
git clone https://github.com/Juan-Zapata31/Bank.git
cd Bank

# 2. Build and run with Maven
./mvnw spring-boot:run

# 3. The API will be available at:
# http://localhost:8080
```


---

## 👨‍💻 Author

<div align="center">

<br/>

<br/>

[![GitHub](https://img.shields.io/badge/GitHub-Juan--Zapata31-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Juan-Zapata31)

</div>

---

<div align="center">
  <sub>© 2025 Juan Zapata · MIT License</sub>
</div>
