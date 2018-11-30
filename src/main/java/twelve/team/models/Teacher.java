package twelve.team.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import twelve.team.Database;

/**
 * The user representation of a teacher. Initialized after authentication and
 * saves a list of semesters. A teacher object is passed into the semester selection
 * screen as a reference to CRUD semesters.
 */
public class Teacher {
    public static Database db;
    
    /* Teacher Variables */
    String name;
    long teacherId;
    ArrayList<Semester> semesters;

    public Teacher(String name, long teacherId) {
        this.name = name;
        fetch(teacherId);
    }

    private boolean fetch(long teacherId) {
        if (db == null) {
            db = Database.getDatabase();
        }
        
        // Fetch the data from db using jdbc
        return false;
    }
    
    public static Teacher create(long teacherId, String password, String name) {
        if (db == null) {
            db = Database.getDatabase();
        }
        
        try {
            String query = "insert into teacher (username, password, name) values (?, ?, ?)";
            PreparedStatement prpst = db.prepareStatement(query);
            prpst.setString(1, Long.toString(teacherId));
            prpst.setString(2, password);
            prpst.setString(3, name);
            prpst.execute();
            
            return new Teacher(name,teacherId);
        } catch (SQLException e) {
            e.printStackTrace(); // DEBUG
            return null;
        }
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