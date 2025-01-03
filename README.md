# ConnectHub: A Social Networking Platform

ConnectHub is a lightweight social networking platform implemented using Java Swing for the frontend and plain Java for the backend. It supports basic social networking features such as user management, content creation, and group management.

---

## Features

- User registration and authentication
- Notifications system for friend requests, new posts, and group activities
- News feed with posts and stories
- Group management (create, join, and moderate groups)

---

## Requirements

### **Dependencies**

The project uses the following dependencies:

- **Jackson Databind**: For JSON parsing and serialization.
- **Lombok**: To simplify model class creation.
- **JBCrypt**: For password hashing.

### **Environment Setup**

1. Install Java 11 or higher.
2. Install Maven for dependency management.
3. Ensure database files are present in the `src/resources/data` folder with the following initial state mentioned below.

---

## How to Build and Run

### **1. Clone the Repository**

```bash
$ git clone https://github.com/malek-attia/Connect-Hub.git
$ cd Connect-Hub
```

### **2. Build the Project**

Run the following Maven command to build the project:

```bash
$ mvn clean install
```

### **3. Run the Application**

Run the `Main` class to start the application:

```bash
$ mvn exec:java -Dexec.mainClass="com.socialnetwork.connecthub.Main"
```

---

## Project Structure

```
com.socialnetwork.connecthub
├── backend
│   ├── api                        // Controllers and API interfaces
│   ├── service                    // Business logic
│   ├── persistence                // Repositories and database-specific logic
│   │   ├── repository             // Abstract repositories
│   │   ├── json                   // JSON-specific implementations
│   │   └── sql                    // SQL-specific implementations (future scope)
│   ├── model                      // Domain models
│   ├── interfaces/                // Interfaces for APIs and services
├── frontend
│   ├── swing                      // Swing-specific UI
│   └── web                        // Future HTML/JavaScript-based UI
├── utils                          // Utility classes
├── shared                         // Shared resources
│   ├── dto                        // Data Transfer Objects
│   └── exceptions                 // Custom exception classes
└── resources
    ├── pics                       // In-Application pictures and icons.
    └── data                       // Database files (JSON)
```

---

## Setting Up the Database

For Json based Database ensure the following database files are present under `src/resources/data`:

- **users.json**: User data
- **groups.json**: Group data
- **posts.json**: Post data
- **stories.json**: Story data
- **friendRequests.json**: Friend request data
- **blocks.json**: Blocked users data

Each file should have the following initial structure:

```json
[ ]
```

---

## Contribution Guidelines

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes with descriptive commit messages.
4. Submit a pull request.

---

## Acknowledgments

- [Jackson Library](https://github.com/FasterXML/jackson)
- [Lombok](https://projectlombok.org/)
- [JBCrypt](https://www.mindrot.org/projects/jBCrypt/)

