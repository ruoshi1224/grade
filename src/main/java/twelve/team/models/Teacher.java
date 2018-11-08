package twelve.team.models;

import java.util.ArrayList;

/**
 * The user representation of a teacher. Initialized after authentication and
 * saves a list of semesters. A teacher object is passed into the semester selection
 * screen as a reference to CRUD semesters.
 */
public class Teacher {
    String name;
    ArrayList<Semester> semesters;

    public Teacher(String name, long teacherId) {
        this.name = name;
        semesters = fetch(teacherId);
    }

    private ArrayList<Semester> fetch(long teacherId) {
        // Fetch the data from db using jdbc
        return null;
    }

    public ArrayList<Semester> getSemesters() {
        return semesters;
    }

    public boolean editSemester(String name) {
        return false;
    }

    public boolean removeSemester(String name, long semesterId) {
        return false;
    }

    public boolean addSemester(Semester semester) {
        return false;
    }
}