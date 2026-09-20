import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ExpenseTracker {
    private static final String FILE_NAME = "expenses.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=============================================");
        System.out.println(" Corporate Digital Expense Tracker ");
        System.out.println("=============================================");

        while (true) {
            System.out.println("\n--- Operations Menu ---");
            System.out.println("1. Log New Financial Expense");
            System.out.println("2. Display Saved Audit Logs");
            System.out.println("3. Exit System");
            System.out.print("Select choice (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                System.out.print("Enter Item Description: ");
                String description = scanner.nextLine();
                System.out.print("Enter Amount (INR): ");
                double amount = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Enter Department Category: ");
                String category = scanner.nextLine();

                saveExpenseToFile(description, amount, category);

            } else if (choice == 2) {
                readAuditLogs();
            } else if (choice == 3) {
                System.out.println("Shutting down the financial audit ledger. Goodbye!");
                break;
            } else {
                System.out.println("[ERROR] Invalid entry. Use values between 1 and 3.");
            }
        }
        scanner.close();
    }

    private static void saveExpenseToFile(String desc, double amt, String cat) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             PrintWriter pw = new PrintWriter(fw)) {
            
            pw.println(desc + "," + amt + "," + cat);
            System.out.println("[SUCCESS] Ledger updated successfully in " + FILE_NAME);
            
        } catch (IOException e) {
            System.out.println("[ERROR] Data Storage Error: Unable to write to disk file.");
        }
    }

    private static void readAuditLogs() {
        System.out.println("\n================ AUDIT LOG MATRIX ================");
        System.out.printf("%-20s %-12s %-15s\n", "Description", "Amount", "Category");
        System.out.println("--------------------------------------------------");
        
        double totalBudgetSpent = 0;
        
        try (FileReader fr = new FileReader(FILE_NAME);
             BufferedReader br = new BufferedReader(fr)) {
            
            String logLine;
            while ((logLine = br.readLine()) != null) {
                String[] segments = logLine.split(",");
                if (segments.length == 3) {
                    String desc = segments[0];
                    double amt = Double.parseDouble(segments[1]);
                    String cat = segments[2];
                    
                    System.out.printf("%-20s RS%-11.2f %-15s\n", desc, amt, cat);
                    totalBudgetSpent += amt;
                }
            }
            System.out.println("--------------------------------------------------");
            System.out.printf("Total Aggregated Expenditure: RS%.2f\n", totalBudgetSpent);
            
        } catch (IOException e) {
            System.out.println("[INFO] No prior financial logs detected. The database ledger is clear.");
        }
    }
}