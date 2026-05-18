#  University Management & Research System (WSP)

A console-based **University Management System (WSP)** built in Java using Object-Oriented Programming principles, design patterns (Decorator, Factory, Singleton, Strategy), and Serialization.


## Authors

* **Bakhramov Abdulaziz**
* **Kairbek Abulkhair**
* **Saden Daniyar**
* **Abduova Akbota**

---

##  Core Features

### 1. Department & Course Management
* **Structured Curriculum System**: Fully supports courses, schedules, and lesson structures (Lecture/Practice).
* **School Realism**: Seeded with the complete **SITE** (School of Information Technology and Engineering) curriculum, as well as BS (Business School), ISE, and FEOG departments.
* **Credit Validation**: Strict credit limits (maximum 30 credits per semester) and failure counts checking for registration blockages.

### 2. Interactive Weekly Schedule
* **Automated Schedule Generators**: Enrolling in courses dynamically assigns days, times, instructors, and classrooms.
* **Student View**: Seamless weekly timetables for registered students.

### 3. Scientific Research Cabinet (Dynamic Decorators)
* **Researcher Decorator Pattern**: Dynamically turns any student (Bachelor, Master, PhD) or teacher (Professor, Tutor) into a fully-fledged Researcher.
* **H-Index Calculations**: Accurately computes global citation metrics based on approved publication logs in the IEEE format.
* **Academic Supervisor Validation**: Enforces supervisor assignment constraints (minimum H-index of 3 for supervising Bachelor students).
* **Research Projects & Papers**: Collaborative research projects with publication pipelines and administrative approvals.

### 4. Robust Object Persistence
* **Serialization/Deserialization**: Entire university database, users, courses, schedules, and research records are fully preserved and loaded via `database/data`.

---



---

## How to Run

1. **Compile**:
   ```bash
   javac -d bin -sourcepath src src/application/Main.java
   ```
2. **Execute**:
   ```bash
   java -cp bin application.Main
   ```
