# 🚀 Assessment Feedback System (AFS) - Base Architecture

**Project**: OODJ Assignment - Assessment Feedback System  
**Team Size**: 4 Members  
**Deadline**: February 5, 2026  
**Institution**: Asia Pacific University (APU)

---

## 📋 Table of Contents
- [Project Overview](#-project-overview)
- [Team Structure](#-team-structure)
- [Project Structure](#-project-structure)
- [Developer Rules](#️-developer-rules-read-carefully)
- [Getting Started](#-getting-started)
- [OOP Principles Implementation](#-oop-principles-implementation)
- [File Database Structure](#-file-database-structure)
- [Important Notes](#️-important-notes)

---

## 🎯 Project Overview

The Assessment Feedback System (AFS) is a comprehensive Java-based application designed for managing academic assessments, student enrollments, and feedback delivery at Asia Pacific University. The system demonstrates core OOP principles including **Abstraction**, **Inheritance**, **Encapsulation**, **Polymorphism**, and **File I/O**.

### Key Features
- ✅ Role-based authentication (Admin, Academic Leader, Lecturer, Student)
- ✅ User management and profile updates
- ✅ Module and class management
- ✅ Assessment creation and grading
- ✅ Student enrollment and results tracking
- ✅ Feedback system for assessments
- ✅ Text-file based data persistence

---

## 👥 Team Structure

| Developer | Module | Classes | Responsibilities |
|-----------|--------|---------|------------------|
| **YOU** (Main System Lead) | Core System + Login | `User`, `Main`, `LoginManager`, `FileManager` | Base architecture, authentication, file I/O utilities |
| **Person 1** | Admin Module | `Admin`, `GradingSystem`, `Class` | User CRUD, grading system, class creation |
| **Person 2** | Academic Leader + Lecturer | `AcademicLeader`, `Module`, `Lecturer`, `Assessment`, `Feedback` | Module management, assessment design, marks entry |
| **Person 3** | Student Module | `Student`, `Enrollment`, `Result` | Class registration, results viewing |

---

## 📂 Project Structure

```
AFS-Project/
│
├── src/
│   └── com/mycompany/apu_oodj_afs/
│       ├── models/                    # Actor Classes (OOP Models)
│       │   ├── User.java              # ⭐ ABSTRACT BASE CLASS (Main Lead)
│       │   ├── Admin.java             # Person 1
│       │   ├── AcademicLeader.java    # Person 2
│       │   ├── Lecturer.java          # Person 2
│       │   ├── Student.java           # Person 3
│       │   ├── GradingSystem.java     # Person 1
│       │   ├── Class.java             # Person 1
│       │   ├── Module.java            # Person 2
│       │   ├── Assessment.java        # Person 2 (Abstract)
│       │   ├── Assignment.java        # Person 2
│       │   ├── Quiz.java              # Person 2
│       │   ├── Exam.java              # Person 2
│       │   ├── Feedback.java          # Person 2
│       │   ├── Enrollment.java        # Person 3
│       │   └── Result.java            # Person 3
│       │
│       ├── core/                      # Business Logic
│       │   ├── LoginManager.java      # Main Lead
│       │   └── FileManager.java       # Main Lead
│       │
│       ├── gui/                       # JFrame Forms (Swing/JavaFX)
│       │   ├── LandingPage.java       # Main Lead
│       │   ├── LoginPage.java         # Main Lead
│       │   ├── AdminDashboard.java    # Person 1
│       │   ├── LeaderDashboard.java   # Person 2
│       │   ├── LecturerDashboard.java # Person 2
│       │   └── StudentDashboard.java  # Person 3
│       │
│       └── Main.java                  # Entry Point (Main Lead)
│
├── data/                              # Text File Database
│   ├── users.txt                      # All users (Main Lead creates)
│   ├── grading_system.txt             # Person 1
│   ├── classes.txt                    # Person 1
│   ├── modules.txt                    # Person 2
│   ├── assessments.txt                # Person 2
│   ├── feedback.txt                   # Person 2
│   ├── marks.txt                      # Person 2
│   └── enrollments.txt                # Person 3
│
├── screenshots/                       # GUI Screenshots for Report
├── docs/                              # UML Diagrams, Documentation
└── README.md                          # ⭐ YOU ARE HERE
```

---

## 🛠️ Developer Rules (Read Carefully!)

### 🔴 RULE 1: Inheritance Hierarchy (MANDATORY)

**The `User` class is the ABSTRACT BASE CLASS. ALL actor classes MUST extend it.**

#### ✅ Correct Implementation:
```java
// Person 1, 2, or 3 creating their Actor class
public class Student extends User {
    // Student-specific attributes
    private String program;
    private int yearOfStudy;
    
    // Constructor MUST call super()
    public Student(String userId, String username, String password, 
                   String name, String email) {
        super(userId, username, password, name, email, "Student");
        this.program = "";
        this.yearOfStudy = 1;
    }
    
    // MUST override displayDashboard()
    @Override
    public void displayDashboard() {
        new StudentDashboard(this).setVisible(true);
    }
}
```

#### ❌ WRONG Implementation:
```java
// DO NOT DO THIS - Breaking inheritance!
public class Student {  // Missing "extends User"
    private String userId;
    private String username;
    // ... duplicating User attributes
}
```

---

### 🔴 RULE 2: Encapsulation (Private Attributes + Getters/Setters)

**All class attributes MUST be private. Use getters/setters for access.**

#### ✅ Correct:
```java
public class Module {
    private String moduleCode;
    private String moduleName;
    private int creditHours;
    
    // Getter
    public String getModuleCode() {
        return moduleCode;
    }
    
    // Setter
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
}
```

#### ❌ WRONG:
```java
public class Module {
    public String moduleCode;  // NO! Should be private
    public String moduleName;  // NO! Should be private
}
```

---

### 🔴 RULE 3: Polymorphism (Method Overriding)

**Each User subclass MUST override `displayDashboard()` to show their specific GUI.**

```java
// In User.java (Main Lead creates this)
public abstract class User {
    public abstract void displayDashboard();  // Forces all subclasses to implement
}

// In Student.java (Person 3 implements)
@Override
public void displayDashboard() {
    new StudentDashboard(this).setVisible(true);
}

// In Admin.java (Person 1 implements)
@Override
public void displayDashboard() {
    new AdminDashboard(this).setVisible(true);
}
```

---

### 🔴 RULE 4: GUI Integration (No Login Logic in JFrames!)

**Your JFrame should ONLY handle UI. Business logic stays in Model classes.**

#### ✅ Correct Flow:
```java
// 1. Main.java launches LandingPage
public class Main {
    public static void main(String[] args) {
        new LandingPage().setVisible(true);
    }
}

// 2. LoginPage calls LoginManager
String role = LoginManager.authenticate(username, password);
User user = LoginManager.getCurrentUser();

// 3. LoginManager redirects to appropriate dashboard
user.displayDashboard();  // Polymorphism in action!
```

#### ❌ WRONG:
```java
// DO NOT put authentication logic inside JFrame
public class LoginPage extends JFrame {
    private void loginButtonActionPerformed() {
        // NO! Don't do this here
        if (username.equals("admin") && password.equals("123")) {
            new AdminDashboard().setVisible(true);
        }
    }
}
```

---

### 🔴 RULE 5: File I/O via FileManager

**Use the centralized `FileManager` class. DO NOT create your own file readers/writers.**

#### ✅ Correct:
```java
// Reading from file
ArrayList<String> users = FileManager.readFromFile("users.txt");

// Writing to file
FileManager.writeToFile("users.txt", userData);

// Appending to file
FileManager.appendToFile("enrollments.txt", newEnrollment);
```

#### ❌ WRONG:
```java
// DO NOT create your own FileReader in each class
FileReader fr = new FileReader("users.txt");  // NO!
```

---

## 🚦 Getting Started

### Prerequisites
- **Java JDK**: 8 or higher
- **IDE**: NetBeans (recommended) or IntelliJ IDEA
- **Git**: For version control

### Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-team/afs-project.git
   cd afs-project
   ```

2. **Open in NetBeans**
   - File → Open Project → Select the `AFS-Project` folder
   - Wait for dependencies to resolve

3. **Initialize Data Files**
   - Run `Main.java` once to auto-create the `data/` folder and `.txt` files
   - Check `data/users.txt` for default credentials

4. **Test Login**
   - Use these default credentials:
     ```
     Admin:          admin / admin123
     Academic Leader: leader1 / pass123
     Lecturer:       lecturer1 / pass123
     Student:        student1 / pass123
     ```

5. **Start Development**
   - Create your assigned classes in the `models/` package
   - Create your GUI forms in the `gui/` package
   - Use `FileManager` for all file operations

---

## 🎓 OOP Principles Implementation

### 1️⃣ Abstraction
- **`User`** class (abstract base class)
- **`Assessment`** class (abstract base class for Assignment, Quiz, Exam)
- Abstract method: `displayDashboard()`

### 2️⃣ Inheritance
```
User (abstract)
├── Admin
├── AcademicLeader
├── Lecturer
└── Student

Assessment (abstract)
├── Assignment
├── Quiz
└── Exam
```

### 3️⃣ Encapsulation
- All attributes are `private`
- Public getters/setters for controlled access
- Example: `User.java` has private `userId`, public `getUserId()`

### 4️⃣ Polymorphism
- **Method Overriding**: `displayDashboard()` implemented differently in each User subclass
- **Runtime Polymorphism**: `User currentUser = new Student();`

### 5️⃣ File I/O
- `FileManager` class with CRUD operations
- 7 text files for data persistence
- Methods: `readFromFile()`, `writeToFile()`, `appendToFile()`, `deleteFromFile()`

---

## 💾 File Database Structure

### `users.txt` Format
```
userID|username|password|name|email|role
A001|admin1|admin123|John Admin|admin@apu.edu.my|Admin
L001|leader1|pass123|Mary Leader|leader@apu.edu.my|AcademicLeader
T001|lecturer1|pass123|Bob Teacher|lecturer@apu.edu.my|Lecturer
S001|student1|pass123|Alice Student|student@apu.edu.my|Student
```

### `grading_system.txt` Format (Person 1)
```
gradeLetter|minScore|maxScore|gradePoint
A+|90|100|4.0
A|80|89|4.0
B+|75|79|3.5
B|70|74|3.0
C+|65|69|2.5
C|60|64|2.0
D|50|59|1.0
F|0|49|0.0
```

### `modules.txt` Format (Person 2)
```
moduleCode|moduleName|creditHours|description
OODJ|Object Oriented Development with Java|3|Core programming module
DSTR|Data Structures|3|Algorithms and data structures
```

### `assessments.txt` Format (Person 2)
```
assessmentID|moduleCode|assessmentType|title|totalMarks
ASS001|OODJ|Assignment|AFS Project|100
QZ001|OODJ|Quiz|Midterm Quiz|50
EX001|OODJ|Exam|Final Exam|100
```

### `marks.txt` Format (Person 2)
```
resultID|studentID|assessmentID|moduleCode|marksObtained|grade
R001|S001|ASS001|OODJ|85|A
R002|S001|QZ001|OODJ|42|B+
```

### `enrollments.txt` Format (Person 3)
```
enrollmentID|studentID|classID|moduleCode|enrollmentDate|status
E001|S001|C001|OODJ|2025-01-15|Active
E002|S001|C002|DSTR|2025-01-15|Active
```

---

## ⚠️ Important Notes

### Before You Push Code:
1. ✅ **Pull First**: Always `git pull` before starting work
2. ✅ **Test Locally**: Run your code and test all features
3. ✅ **No Syntax Errors**: Ensure code compiles without errors
4. ✅ **Follow Naming Conventions**: CamelCase for classes, camelCase for methods
5. ✅ **Add Comments**: JavaDoc for classes, inline comments for complex logic

### DO NOT:
- ❌ Delete or modify the `User` class without consulting the Main Lead
- ❌ Move files out of their assigned packages
- ❌ Create your own file I/O methods (use `FileManager`)
- ❌ Hardcode file paths (use relative paths: `data/users.txt`)
- ❌ Push broken code to the main branch

### Communication:
- 💬 **Daily Standups**: 9pm every night (15 min)
- 💬 **WhatsApp Group**: For urgent questions
- 💬 **GitHub Issues**: For tracking bugs and tasks
- 💬 **Code Reviews**: Main Lead reviews all pull requests

---

## 🏆 Grading Criteria (What Lecturers Look For)

| Criteria | Weight | Our Implementation |
|----------|--------|-------------------|
| **Abstraction** | 15% | ✅ User, Assessment abstract classes |
| **Inheritance** | 20% | ✅ 4 User subclasses, 3 Assessment subclasses |
| **Encapsulation** | 15% | ✅ Private attributes, getters/setters |
| **Polymorphism** | 20% | ✅ displayDashboard() overriding |
| **File I/O** | 15% | ✅ FileManager with 7 text files |
| **GUI** | 10% | ✅ Swing forms for all dashboards |
| **Documentation** | 5% | ✅ This README + JavaDoc comments |

**Target Grade**: Distinction (70%+)

---

## 📞 Contact & Support

**Project Lead (Main System)**: [Your Name] - [Your Email/Phone]  
**Person 1 (Admin)**: [Name] - [Contact]  
**Person 2 (Academic Leader + Lecturer)**: [Name] - [Contact]  
**Person 3 (Student)**: [Name] - [Contact]

---

## 📚 Additional Resources

- [Java OOP Tutorial](https://docs.oracle.com/javase/tutorial/java/concepts/)
- [NetBeans GUI Builder Guide](https://netbeans.apache.org/kb/docs/java/gui-functionality.html)
- [Git Basics](https://git-scm.com/book/en/v2/Getting-Started-Git-Basics)
- [APU OODJ Assignment Brief](./docs/assignment-brief.pdf)

---

## 🎉 Final Reminder

> **"Code is like humor. When you have to explain it, it's bad."** – Cory House

Write clean, self-documenting code. Follow the rules above, and we'll ace this assignment! 💪

**Last Updated**: January 27, 2026  
**Project Status**: 🟢 Active Development (9 days remaining)

---

### 📝 Changelog

| Date | Change | Author |
|------|--------|--------|
| Jan 27, 2026 | Initial README creation | Main Lead |
| Jan 28, 2026 | Added file format specifications | Main Lead |
| Jan 29, 2026 | Updated team structure (4 members) | Main Lead |

---

**Good luck, team! Let's build something amazing! 🚀**
