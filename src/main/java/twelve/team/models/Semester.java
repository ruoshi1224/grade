package twelve.team.models;

import java.util.ArrayList;
import twelve.team.Database;

/**
 * The container for courses.
 * Teachers map to semesters first, from which
 * they can then view the courses they manage.
 */
public class Semester {
    
    ArrayList<Course> courses;
    String semesterID;
    String name;
    Database db;
    
    public Semester(String semesterID, String name) {
        this.semesterID = semesterID;
        this.name = name;
    }
    
    private void fetch() {
        
    }
}