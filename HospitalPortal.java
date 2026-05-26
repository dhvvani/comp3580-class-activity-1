// =========================
// File: HospitalPortal.java
// =========================

import java.io.*;
import java.util.*;

public class HospitalPortal {

    // Weak predictable session storage
    private static Map<String, String> sessions = new HashMap<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Hospital Employee Portal ===");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        // SQL Injection vulnerability possible here
        boolean authenticated = HospitalDatabase.login(username, password);

        if (!authenticated) {
            System.out.println("Invalid login.");
            return;
        }

        // Weak predictable session token
        String sessionId = username + "_session";
        sessions.put(sessionId, username);

        System.out.println("Logged in successfully.");
        System.out.println("Session ID: " + sessionId);

        while (true) {

            System.out.println("\n1. View Patient Record");
            System.out.println("2. View Payroll");
            System.out.println("3. Export Employee Report");
            System.out.println("4. Load Backup File");
            System.out.println("5. Exit");

            System.out.print("Choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            try {

                switch (choice) {

                    case 1:

                        System.out.print("Enter Patient ID: ");
                        String patientId = scanner.nextLine();

                        String patientRecord =
                                HospitalDatabase.getPatientRecord(patientId);

                        System.out.println(patientRecord);

                        break;

                    case 2:

                        System.out.print("Enter Employee ID: ");
                        String employeeId = scanner.nextLine();

                        String payroll =
                                HospitalDatabase.getPayroll(employeeId);

                        System.out.println(payroll);

                        break;

                    case 3:

                        System.out.print("Enter export filename: ");
                        String filename = scanner.nextLine();

                        HospitalDatabase.exportEmployeeReport(filename);

                        System.out.println("Export completed.");

                        break;

                    case 4:

                        System.out.print("Enter backup file path: ");
                        String backupPath = scanner.nextLine();

                        Object obj = HospitalDatabase.loadBackup(backupPath);

                        System.out.println("Loaded object: " + obj);

                        break;

                    case 5:
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}