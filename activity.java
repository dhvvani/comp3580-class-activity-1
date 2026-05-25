import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class VulnerableLogin {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/appdb",
                "root",
                "password123"
            );

            Statement stmt = conn.createStatement();

            // VULNERABLE TO SQL INJECTION
            String query =
                "SELECT * FROM users WHERE username = '" +
                username +
                "' AND password = '" +
                password +
                "'";

            System.out.println("Executing query: " + query);

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid credentials.");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //test
}
