Since you are the **Project Architect**, your `README.md` needs to be very clear so your teammates (Student, Lecturer, and Academic Leader leads) don't break the **Inheritance** and **Encapsulation** rules you've set up.

Copy and paste the following content into your `README.md` file.

---

# 🚀 Assessment Feedback System (AFS) - Base Architecture

This repository contains the core OOP structure for our OODJ assignment. Please follow the guidelines below to ensure we get full marks for **Generalization, Abstraction, and Modularity**.

## 📂 Project Structure

* **`com.mycompany.apu_oodj_afs.models`**: Contains all Actor classes (User, Admin, etc.).
* **`com.mycompany.apu_oodj_afs.core`**: Contains business logic like `LoginManager`.
* **`gui`**: Contains all JFrame forms.
* **`data`**: Contains `users.txt` (Our Text-File Database).

---

## 🛠️ Developer Rules (Read Carefully!)

### 1. Creating your Actor Class

If you are in charge of the **Student** or **Lecturer** role, your class MUST:

* `extends User` (Inheritance).
* Use `super(userId, username, password, name, role)` in your constructor.
* Implement `@Override public void displayDashboard()`.

### 2. Launching your GUI

Do **not** put login logic inside your JFrame.

1. Create your JFrame in the `gui` package.
2. Inside your Model class (e.g., `Student.java`), use the `displayDashboard()` method to make your JFrame visible:
```java
@Override
public void displayDashboard() {
    new StudentDashboard().setVisible(true);
}

```



### 3. Database (Text Files)

* All user credentials are stored in `data/users.txt`.
* Format: `ID|Username|Password|Name|Role`
* Example: `S001|josh|pass123|Joshua|Student`

---

## 🚦 How to Start

1. **Clone** this repository.
2. Open the project in **NetBeans**.
3. Run `Main.java` to launch the `LandingPage`.
4. Use the credentials in `users.txt` to test the login.

---

### ⚠️ IMPORTANT: Before You Push

* **Do not** delete the `User` class.
* **Do not** move files out of their assigned packages.
* Always **Pull** before you **Push** to avoid merge conflicts!

---

### One final tip for you:

Make sure you save this file in the **Project Root** (the same folder that contains the `src` folder). When your lecturer opens your GitHub link, this professional documentation will be the first thing they see!
