package twelve.team;

import java.sql.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    public static final String DATABASE_NAME = "gradesystem";
    private static Database database;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private Database() {
        initDB();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> close()));
    }

    public static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    private void initDB() {
        String url = "jdbc:mysql://localhost";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to Database...");
            connection = DriverManager.getConnection(url, "root", "root");
            statement = connection.createStatement();

            // Check if the database exists on the server...
            resultSet = connection.getMetaData().getCatalogs();
            while (resultSet.next()) {
                // Get the database name, which is at position 1
                String databaseName = resultSet.getString(1);
                if (databaseName.equals(DATABASE_NAME)) {
                    System.out.println(DATABASE_NAME + " found, connecting...");
                    return;
                }
            }

            String sql = "CREATE DATABASE " + DATABASE_NAME + ";";
            statement.executeUpdate(sql);
            System.out.println("Database " + DATABASE_NAME + " created.");

            sql = ("USE gradesystem;");
            statement.execute(sql);
            
            sql = ("create table teacher(\n"
                    + "teacherID varchar(20) NOT NULL,\n"
                    + "password varchar(20) NOT NULL,\n"
                    + "name varchar(20) NOT NULL,\n"
                    + "PRIMARY KEY (teacherID)\n"
                    + ");");

            sql = ("create table semester(\n"
                    + "semesterID int NOT NULL,\n"
                    + "teacherID varchar(20) NOT NULL,\n"
                    + "semesterName varchar(20) NOT NULL,\n"
                    + "PRIMARY KEY (semesterID)\n"
                    + "FOREIGN KEY (teacherID) references teacher(teacherID)\n"
                    + ");");
            statement.execute(sql);
            System.out.println("Table semester created successfully...");

            sql = ("create table course (\n"
                    + "courseID int NOT NULL,\n"
                    + "courseName varchar(20) NOT NULL,\n"
                    + "semesterID int NOT NULL,\n"
                    + "PRIMARY KEY (courseID),\n"
                    + "FOREIGN KEY (semesterID) references semester(semesterID)\n"
                    + ");");
            statement.execute(sql);
            System.out.println("Table courses created successfully...");

            sql = ("create table student (\n"
                    + "studentID int NOT NULL,\n"
                    + "studentName varchar(20) NOT NULL,\n"
                    + "degree int NOT NULL,\n"
                    + "courseID int NOT NULL,\n"
                    + "PRIMARY KEY (studentID),\n"
                    + "FOREIGN KEY (courseID) REFERENCES courses(courseID)\n"
                    + ");");
            statement.execute(sql);
            System.out.println("Table student created successfully...");

            sql = ("create table category(\n"
                    + "categoryID int NOT NULL,\n"
                    + "categoryName varchar(20) NOT NULL,\n"
                    + "courseID int NOT NULL,\n"
                    + "PRIMARY KEY (categoryID),\n"
                    + "FOREIGN KEY (courseID) \tREFERENCES courses(courseID));");
            statement.execute(sql);
            System.out.println("Table category created successfully...");

            sql = ("create table assignment(\n"
                    + "assignmentID int NOT NULL,\n"
                    + "assignmentName varchar(20) NOT NULL,\n"
                    + "totalPoints float,\n"
                    + "extraCredit float,\n"
                    + "tag varchar(10),\n"
                    + "status bit default 0,\n"
                    + "categoryID int NOT NULL,\n"
                    + "PRIMARY KEY (assignmentID),\n"
                    + "FOREIGN KEY (categoryID) REFERENCES category(categoryID)\n"
                    + ");");
            statement.execute(sql);
            System.out.println("Table assignment created successfully...");

            sql = ("create table grade (\n"
                    + "studentID int NOT NULL,\n"
                    + "assignmentID int NOT NULL,\n"
                    + "grade int,\n"
                    + "foreign key (studentID) REFERENCES student(studentID),\n"
                    + "foreign key (assignmentID) REFERENCES assignment(assignmentID)\n"
                    + ");");
            statement.execute(sql);
            System.out.println("Table grade created successfully...");

            sql = ("create table commentbyteacher (\n"
                    + "studentID int NOT NULL,\n"
                    + "assignmentID int NOT NULL,\n"
                    + "commentbyteacher TEXT,\n"
                    + "foreign key (studentID) REFERENCES student(studentID),\n"
                    + "foreign key (assignmentID) REFERENCES assignment(assignmentID)\n"
                    + ");");
            statement.execute(sql);
            System.out.println("Table commentbyteacher created successfully...");

            sql = ("CREATE TABLE weights (\n"
                    + "categoryID int,\n"
                    + "assignmentID int,\n"
                    + "degree int NOT NULL,\n"
                    + "weight float\n"
                    + ");");
            statement.execute(sql);
            System.out.println("Table weights created successfully...");
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (Exception e) {
            e.printStackTrace(); // DEBUG
        }
    }
    
    public ResultSet getQuery(String query) {
        try {
            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace(); // DEBUG
            return null;
        }
    }
    
    public PreparedStatement prepareStatement(String query) {
        try {
            PreparedStatement prpst = connection.prepareStatement(query);
            return prpst;
        } catch (SQLException e) {
            e.printStackTrace(); // DEBUG
            return null;
        }
    }
    
    public boolean execute(String sql) {
        try {
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // DEBUG
            return false;
        }
    }

    private void close() {
        try {
            System.out.println("Closing " + DATABASE_NAME + " connection...");
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
