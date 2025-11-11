Tuyệt vời! Đây là đoạn mô tả chi tiết của bạn, đã được dịch sang tiếng Anh và giữ nguyên các biểu tượng cảm xúc (emoji) để làm nổi bật:

🃏 Project: E-commerce Website 🛍️ Yu-Gi-Oh! Card Shop 💳
This is a classic ☕ Java Web Application built to function as an e-commerce platform specializing in selling 🎴 Yu-Gi-Oh! trading cards. The project is well-structured and follows the 🏛️ Model-View-Controller (MVC) architectural pattern.

🏛️ Core Architecture (MVC)
The application's structure is clearly separated for ease of maintenance:

Model (M) 🗃️: Handles all data logic 📊 and database interactions 🔄.

DAO (Data Access Object): 🧑‍💻 Classes like CardDAO.java, UserDAO.java, and OrderDAO.java contain all the 📜 SQL queries for database communication.

DTO (Data Transfer Object): 📄 Classes such as CardDTO.java and UserDTO.java are simple Java objects (POJOs) used to transfer data 📦 between the database and the application.

View (V) 🖥️: The user interface 👀 that users see in their web browser 🌐.

Built entirely using JSP (JavaServer Pages) 📄 files (e.g., home.jsp, Cart.jsp, login.jsp).

Utilizes JSTL (JSP Standard Tag Library) 🏷️ for dynamic data display (e.g., using <c:forEach> 🔁 for listing products instead of messy Java code).

Controller (C) 🧠: The application's "brain" 🤖 that processes user requests 📩.

Implemented using Java Servlets 🧑‍🚀 (e.g., UserController.java, ProductController.java).

Uses the Front Controller 🚪 design pattern, where MainController.java acts as a central "gate" 📍, receiving all requests and dispatching tasks 📝 to other controllers based on the action parameter.

🛠️ Backend Technologies (Server-Side)
Java Servlets: 🏃 Used to process all HTTP requests (GET, POST) and manage the application flow ➡️.

JSP (JavaServer Pages): 📄 Serves as the templating engine to generate dynamic HTML content 🖼️ for users.

JSTL (JSP Standard Tag Library): 🏷️ Used within JSP files for clean logic (loops 🔄, conditionals ❓).

Servlet Filters (/filter/): 🔐 A critical feature used for security and request processing.

AuthenticateFilter.java: 🔑 Checks if a user is logged in before granting access to private pages 🔒.

AdminFilter.java & StaffFilter.java: 👮 Provide role-based authorization, ensuring only users with the correct role (Admin 👑, Staff 👷) can access management pages.

JavaMail API (javax.mail-1.6.2.jar): 📧 Used for sending emails, typically for features like account verification ✅ or password reset 📬 (EmailUtils.java).

🎨 Frontend Technologies (Client-Side)
HTML5: 🧱 Provides the semantic structure for all web pages.

CSS3: 💅 Used for custom styling of the website (e.g., style.css, login.css, manager.css).

JavaScript (ES6): 💡 Used for client-side interactions, such as form validation ✅ or confirmation dialogs 👆 (manager.js).

🗃️ Database
Microsoft SQL Server: 💾 The Relational Database Management System (RDBMS) used, based on the presence of the sqljdbc4.jar driver.

JDBC (Java Database Connectivity): 🔗 Used directly (without an ORM) via the utils/DBUtils.java class 🏤 to establish connections and execute SQL queries.

⚙️ Environment & Build Tools
IDE: 💻 Apache NetBeans (identified by the nbproject/ directory).

Build Tool: 🔨 Apache Ant (identified by the build.xml file), which is managed by NetBeans for compiling, building, and packaging the project 🎁.

Server: 🌐 Designed for deployment on a Java Web Server, most commonly Apache Tomcat 😺.

Deployment: 📦 The project is packaged as a .war (Web Application Archive) file (dist/YUGIOH_SHOP_PROJECT.war) for deployment 🚀.
