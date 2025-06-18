**College Mess Management System**

**Project Description**  
The College Mess Management System is a Java-based application designed to streamline the management of college mess activities. It features role-specific dashboards for students, chefs, managers, and college management, with functionalities such as menu planning, feedback collection, inventory management, and report generation. The project integrates with a MySQL database and uses JDBC for database connectivity.

---

**Required Software, Libraries, and Versions**  
1. Java Development Kit (JDK): Version 17 or higher  
2. MySQL: Version 8.0 or higher  
3. MySQL JDBC Driver: `mysql-connector-j-9.1.0.jar`  
4. Integrated Development Environment (IDE): IntelliJ IDEA (or any other Java IDE)  
5. Additional Libraries:  
   - Standard Java libraries (`java.sql`, `java.io`)  
   - JDBC for MySQL database integration  

---

**Setup Instructions**

1. **Software Installation**  
   - Install JDK 17 or higher and set up the environment variables (`JAVA_HOME`).  
   - Install MySQL Server and MySQL Workbench.  
   - Download the MySQL JDBC Driver (`mysql-connector-j-9.1.0.jar`) and add it to the classpath in IntelliJ IDEA.

2. **Database Setup**  
   - Create a new MySQL database named `messmangement`.  
   - Run the provided SQL scripts to create the required tables (`users`, `feedback`, `menu`, `inventory`, `staff`, `dishes`, `counters`, `messpopulation`).  
   - Populate the tables with initial data using the provided SQL insert statements.  

3. **Steps to Run the Project**  
   - Open the project in IntelliJ IDEA.  
   - Add the MySQL JDBC Driver to the classpath:  
     1. Go to **File > Project Structure > Libraries**.  
     2. Click **+** and add the `mysql-connector-j-9.1.0.jar` file.  
   - Ensure that your MySQL server is running and your database is set up as described above.  
   - Configure the database connection in the code by updating the connection details:  
     ```java
     Connection con = DriverManager.getConnection(
         "jdbc:mysql://localhost:3306/messmangement", "root", "your_password"
     );
     ```
   - Compile the project:  
     ```
     javac temmain.java
     ```
   - Run the project:  
     - In IntelliJ, right-click the **Main** class and select **Run**.  
     - Or use the terminal:  
       ```
       java Main
       ```

---

**Project Execution**  
- Ensure that the database username, password, and URL match your MySQL configuration.  
- If you encounter any issues, check the IntelliJ logs for troubleshooting.  
- The project supports role-based logins (`students`, `chefs`, `managers`, `college management`). Use the pre-populated database users or add your own entries for testing.  

---

**Additional Notes**  
- Ensure that the database username, password, and URL match your MySQL configuration.  
- If you encounter any issues, check the IntelliJ logs for troubleshooting.  
- The project supports role-based logins (`students`, `chefs`, `managers`, `college management`). Use the pre-populated database users or add your own entries for testing.  

---
