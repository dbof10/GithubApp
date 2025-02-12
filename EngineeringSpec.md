# **Engineering Specification: GitHub Users Browser App**

## **1. Overview**
### **1.1 Introduction**
The **GitHub Users Browser App** is a native Android application that allows administrators to browse GitHub users, view detailed user profiles, and seamlessly navigate through the list. The application fetches user data from the GitHub API in batches of 20, supports infinite scrolling, and caches data for instant display upon relaunch.

### **1.2 Objective**
The primary goal of this project is to design a **scalable, reusable, and testable** application architecture that adheres to best engineering practices while ensuring a seamless user experience.

### **1.3 Scope**
- Fetch and display GitHub users in a paginated list.
- Implement infinite scrolling with a batch size of 20.
- Cache fetched users for instant display on the next launch.
- Navigate to user details when an item is clicked.
- Follow Clean Architecture principles.
- Ensure unit testing coverage.

---

## **2. System Design**
### **2.1 Architecture**
The project follows the **MVVM (Model-View-ViewModel) + Redux architecture with Clean Architecture principles**, ensuring modularity, scalability, and testability.
For detail feature, it's used MVVM while the list feature uses Redux. The reasons are discussed [here](Redux.md)


### **2.2 Component Overview**
| Component | Responsibility |
|-----------|---------------|
| **Data Layer** | Fetch data from the API (Retrofit) and cache it in a local database (Room). |
| **Domain Layer** | Contains use cases that handle business logic. |
| **Presentation Layer** | Composes UI and manages user interactions via ViewModel. |
| **Dependency Injection** | Uses Hilt for DI, ensuring modularity. |

### **2.3 Data Flow Diagram**
```
[GitHub API] → [Repository] → [UseCase] → [ViewModel] → [UI]
                     ↑
                [Local Cache]
```

---

### **2.4. Project Modules**
The project is structured into multiple modules to improve maintainability, modularity, and scalability.

| Module | Description                                                                                                             |
|--------|-------------------------------------------------------------------------------------------------------------------------|
| **app** | The main application module that integrates all features and UI components.                                             |
| **arch** | Contains redux architecture-related classes                                                                             |
| **common** | Holds shared utilities, extensions, and reusable components across different modules.                                   |
| **detail** | A feature module handling the user detail screen, including fetching and displaying user-specific information.          |
| **list** | A feature module responsible for listing GitHub users with pagination and infinite scrolling support.                   |
| **navigation** | Manages navigation across different screens and modules using Jetpack Navigation.                                       |
| **test** | Contains test cases, including unit tests and integration tests, ensuring stability and reliability of the application. |

---

## **3. Technical Implementation**
### **3.1 Technologies & Tools**
- **Language:** Kotlin
- **Architecture:** MVVM + Redux + Clean Architecture
- **Networking:** Retrofit + OkHttp
- **Local Storage:** Room
- **Dependency Injection:** Hilt
- **Navigation:** Jetpack Navigation
- **UI Framework:** Jetpack Compose
- **Testing:** JUnit + MockK

---

## **4. Features & Functional Requirements**
### **4.1 Core Features**
| Feature | Description |
|---------|------------|
| Fetch Users | Fetch GitHub users in pages of 20 using Retrofit. |
| Infinite Scrolling | Automatically load more users as the administrator scrolls down. |
| Data Caching | Store fetched users in Room for instant relaunch display. |
| User Details Page | Navigate to a details page showing user-specific information. |

### **4.2 Non-Functional Requirements**
- **Performance:** The app should load and display user data efficiently.
- **Scalability:** The architecture should allow easy feature extension.
- **Offline Support:** Previously fetched users should be available without an internet connection.
- **Maintainability:** Code should follow best practices for readability and modularity.

---

## **5. Testing Strategy**
### **5.1 Unit Tests**
| Component | Test Cases |
|-----------|------------|
| Repository | Verify API response and caching behavior. |
| Use Cases | Ensure correct data fetching and caching logic. |
| ViewModel | Test UI state changes on API response. |

---

## **6. Risks & Mitigation**
| Risk                   | Mitigation                                      |
|------------------------|-------------------------------------------------|
| Data refreshing        | Improve caching mechanisms, current interval 1h |
| Large dataset handling | Improve current paging mechanisms               |

---

## **7. Conclusion**
This document outlines the technical implementation for the **GitHub Users Browser App**, focusing on scalability, performance, and maintainability. The proposed architecture ensures modularity and testability, aligning with industry best practices.

---
