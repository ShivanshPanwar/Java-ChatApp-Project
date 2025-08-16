# 💬 Multi-User Chat Application

A **Java-based multi-user chat system** built using **Swing** for GUI, **JDBC** for database connectivity, and **Socket programming** for networking.  
It allows multiple users to register, login, and communicate in real-time, with features like **user authentication, chat history, and active user tracking**.

## 📂 Project Structure

MultiUserChatApplication/
│── .settings/ # Eclipse project settings
│── src/
│ └── com/shivansh/chatapp/  # Main Java source code
│ ├── dao/  # Data Access Objects (DB interactions)
│ ├── dto/  # Data Transfer Objects (User, Message, etc.)
│ ├── network/  # Networking (Client & Server sockets)
│ ├── utils/  # Utility classes (DB connection, helpers)
│ └── views/  # Swing UI (Login, Register, Chat windows)
│── Images/  # Screenshots & documentation assets
│── config.properties  # Database configuration (URL, username, password)
│── mysql-connector-j-9.2.0.jar  # MySQL JDBC driver
│── .classpath / .project # Eclipse configs
│── .gitignore # Git ignore rules

## 🚀 Features

- 🧑‍🤝‍🧑 **Multi-User Support** – Multiple clients can join and chat simultaneously.  
- 📡 **Client-Server Architecture** – Socket-based communication for real-time messaging.  
- 💾 **Persistent Chat History** – All messages are stored in the MySQL database.  
- 🔑 **User Authentication** – Login/Register functionality with database validation.  
- 📝 **Chat History Retrieval** – Users can view old conversations stored in the DB.  
- 🎨 **User-Friendly GUI** – Java Swing-based interface for smooth user experience.  
- ⚙️ **Configurable Database** – DB credentials stored in `config.properties`.  
- ✅ **Lightweight & Scalable** – Handles multiple clients simultaneously  

## 🖼️ Screenshots  

### 🔹 Login Screen  
<img width="806" height="514" alt="image" src="https://github.com/user-attachments/assets/6f505208-cedd-4a0c-b749-66b09c1a266a" />

### 🔹 Chat Window  
<img width="1271" height="642" alt="image" src="https://github.com/user-attachments/assets/c8b19350-236b-457f-b764-d39e3be2b6e8" />


### 🔹 User List  
<img width="1017" height="482" alt="image" src="https://github.com/user-attachments/assets/e0097fa0-a180-4469-8782-fc8054552704" />


## ⚙️ Tech Stack

- **Programming Language**: Java  
- **GUI Framework**: Swing, AWT  
- **Database**: MySQL  
- **Networking**: Java Socket Programming  
- **IDE / Build Tool**: Eclipse  
- **Driver**: MySQL Connector/J  

---

## 🛠️ Setup & Installation  

### 1️⃣ Clone the Repository  
```bash
git clone https://github.com/ShivanshPanwar/MultiUserChatApplication.git
cd MultiUserChatApplication

2️⃣ Configure Database
Create a MySQL database (e.g., chatdb)

Import the provided SQL script (if available)

Update config.properties with your DB credentials:
db.url=jdbc:mysql://localhost:3306/chatdb
db.username=root
db.password=yourpassword

3️⃣ Add MySQL Connector/J
Ensure mysql-connector-j-9.2.0.jar is added to the project classpath.

4️⃣ Run the Application
Start the server from network/ChatServer.java

Launch clients from views/Login.java

📖 How It Works
The server listens for incoming client connections.

Each client connects via a socket and authenticates through MySQL.

Messages are broadcasted to all clients or sent privately.

Active users are displayed in the chat window.

🎯 Future Improvements
🔒 Add encryption for secure messaging

💬 Implement chat rooms / group chats

📝 Store chat history in the database

☁️ Deploy on cloud for real-time global usage

👨‍💻 Author
Developed by Shivansh Panwar 🚀
