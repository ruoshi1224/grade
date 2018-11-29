package twelve.team;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Auth {
    public static boolean authenticate(String teacherId, String pass) {
        Database db = Database.getDatabase();
        ResultSet result = db.getQuery("select * from teacher where teacherID=" + teacherId);
        
        try {
            if (result.next()) {
                String verify = result.getString("password");
                if (verify.equals(pass)) {
                    return true;
                } else {
                    return false;
                } 
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // DEBUG
            return false;
        }
    }
}