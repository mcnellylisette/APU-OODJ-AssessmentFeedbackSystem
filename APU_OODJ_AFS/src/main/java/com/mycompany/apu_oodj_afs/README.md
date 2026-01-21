
# 🚀 Assessment Feedback System (AFS) - Team Instructions

This is the central repository for our OODJ assignment. As the Lead Architect, I have set up the **Core Foundation**. Follow these rules to ensure we get maximum marks for **OOP Implementation (16%)** and **Presentation (8%)**.

## 📂 Project Structure (Do not change!)
- `com.mycompany.apu_oodj_afs.models`: Store all Actor classes (Lecturer, Student, etc.) here.
- `com.mycompany.apu_oodj_afs.gui`: Store all JFrame/UI forms here.
- `data/`: Place all `.txt` database files here.

## 🏗️ How to Implement your assigned Role
Every teammate must create their own class in the `models` package. To satisfy **Generalization (Inheritance)**:

1. **Inherit**: Your class must `extends User`.
2. **Super**: You must call `super(id, user, pass, name, role)` in your constructor.
3. **Dashboard**: You must `@Override` the `displayDashboard()` method to open your GUI.

### Example Template:
```java
public class Lecturer extends User {
    public Lecturer(String id, String user, String pass, String name, String role) {
        super(id, user, pass, name, role); 
    }

    @Override
    public void displayDashboard() {
        // Person 2 (Lecturer Lead) will call their GUI here:
        // new LecturerDashboard().setVisible(true);
    }
}