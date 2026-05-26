// =========================
// File: HospitalDatabase.java
// =========================

import java.io.*;
import java.sql.*;

public class HospitalDatabase {

    // Hardcoded credentials vulnerability
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/hospital";

    private static final String DB_USER = "admin";

    private static final String DB_PASSWORD = "SuperSecret123";

    public static Connection connect() throws Exception {

        return DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASSWORD
        );
    }

    public static boolean login(String username, String password) {

        try {

            Connection conn = connect();

            Statement stmt = conn.createStatement();

            String query =
                    "SELECT * FROM employees WHERE username='"
                            + username
                            + "' AND password='"
                            + password
                            + "'";

            System.out.println("Executing query: " + query);

            ResultSet rs = stmt.executeQuery(query);

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public static String getPatientRecord(String patientId) {

        try {

            Connection conn = connect();

            Statement stmt = conn.createStatement();

            String query =
                    "SELECT * FROM patients WHERE patient_id='"
                            + patientId
                            + "'";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                return "Patient: "
                        + rs.getString("name")
                        + "\nDiagnosis: "
                        + rs.getString("diagnosis")
                        + "\nSSN: "
                        + rs.getString("ssn");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "Patient not found.";
    }

    public static String getPayroll(String employeeId) {

        try {

            Connection conn = connect();

            Statement stmt = conn.createStatement();

            String query =
                    "SELECT * FROM payroll WHERE employee_id='"
                            + employeeId
                            + "'";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                return "Employee Salary: $"
                        + rs.getString("salary")
                        + "\nBank Account: "
                        + rs.getString("bank_account");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "Payroll record not found.";
    }

    public static void exportEmployeeReport(String filename)
            throws Exception {

        FileWriter writer = new FileWriter(filename);

        Connection conn = connect();

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(
                "SELECT * FROM employees"
        );

        while (rs.next()) {

            String line =
                    rs.getString("username")
                            + ","
                            + rs.getString("password")
                            + ","
                            + rs.getString("salary");

            writer.write(line + "\n");
        }

        writer.close();
    }

    public static Object loadBackup(String filePath)
            throws Exception {

        ObjectInputStream in =
                new ObjectInputStream(
                        new FileInputStream(filePath)
                );

        Object obj = in.readObject();

        in.close();

        return obj;
    }
}