## 🃏 Project: E-commerce Website 🛍️ Yu-Gi-Oh! Card Shop 💳

This is a classic ☕ **Java Web Application** built to function as an e-commerce platform specializing in selling 🎴 Yu-Gi-Oh! trading cards. The project is well-structured and follows the 🏛️ **Model-View-Controller (MVC)** architectural pattern.

### 🏛️ Core Architecture (MVC)

The application's structure is clearly separated for ease of maintenance:

* **Model (M) 🗃️:** Handles all data logic 📊 and database interactions 🔄.
    * **DAO (Data Access Object):** 🧑‍💻 Classes like `CardDAO.java`, `UserDAO.java`, etc., contain all the 📜 SQL queries for database communication.
    * **DTO (Data Transfer Object):** 📄 Classes such as `CardDTO.java` are simple Java objects (POJOs) used to transfer data 📦 between the database and the application.

* **View (V) 🖥️:** The user interface 👀 that users see in their web browser 🌐.
    * Built entirely using **JSP (JavaServer Pages)** 📄 files (e.g., `home.jsp`, `Cart.jsp`).
    * Utilizes **JSTL (JSP Standard Tag Library)** 🏷️ for dynamic data display (e.g., using `<c:forEach>` 🔁 for listing products).

* **Controller (C) 🧠:** The application's "brain" 🤖 that processes user requests 📩.
    * Implemented using **Java Servlets** 🧑‍🚀 (e.g., `UserController.java`, `ProductController.java`).
    * Uses the **Front Controller** 🚪 design pattern (`MainController.java`) to manage the application flow ➡️.

### 🛠️ Key Technologies

| Category | Technology | Files/Classes Used | Purpose |
| :--- | :--- | :--- | :--- |
| **Backend** | **Java Servlet/JSP** | Controllers, View files | Core application logic and view rendering. |
| **Database** | **MS SQL Server** | `sqljdbc4.jar`, `DBUtils.java` | RDBMS and direct **JDBC** 🔗 connection utilities. |
| **Security** | **Servlet Filters** | `AdminFilter.java`, `AuthenticateFilter.java` | Role-based **Authorization** 👮 and user login verification 🔑. |
| **Functionality** | **JavaMail API** | `javax.mail-1.6.2.jar`, `EmailUtils.java` | Sending emails 📧 for account verification/reset. |
| **Frontend** | **HTML5/CSS3/JS** | `.css`, `.js`, `.jsp` files | Client-side presentation 💅 and interaction 💡. |

### ⚙️ Environment & Build

* **IDE:** 💻 Apache NetBeans.
* **Build Tool:** 🔨 Apache Ant (`build.xml`).
* **Server:** 🌐 Apache Tomcat 😺.
* **Deployment:** 📦 Packaged as a `.war` file for deployment 🚀.
