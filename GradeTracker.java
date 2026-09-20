
import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    double assignmentScore;
    double examScore;
    double finalGrade;
    String status;

    public Student(String name, double assignmentScore, double examScore) {
        this.name = name;
        this.assignmentScore = assignmentScore;
        this.examScore = examScore;
        calculateFinalGrade();
    }

    private void calculateFinalGrade() {
        this.finalGrade = (this.assignmentScore * 0.40) + (this.examScore * 0.60);
        this.status = (this.finalGrade >= 50.0) ? "PASS" : "FAIL";
    }
}

public class GradeTracker {
    public static void main(String[] args) {
        ArrayList<Student> studentList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=============================================");
        System.out.println(" AI/ML Academic Performance Tracker ");
        System.out.println("=============================================");
        
        studentList.add(new Student("Rahul Kumar", 78.5, 82.0));
        studentList.add(new Student("Priya Sharma", 45.0, 48.0));
        studentList.add(new Student("Amit Singh", 90.0, 95.0));

        while (true) {
            System.out.println("\n--- Dashboard Menu ---");
            System.out.println("1. View Student Performance Matrix");
            System.out.println("2. Add New Student Entry");
            System.out.println("3. Exit System");
            System.out.print("Select an option (1-3): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                System.out.printf("\n%-15s %-15s %-12s %-12s %-8s\n", "Name", "Assignment", "Exam Score", "Final Grade", "Status");
                System.out.println("-----------------------------------------------------------------");
                double classTotal = 0;
                for (Student s : studentList) {
                    System.out.printf("%-15s %-15.1f %-12.1f %-12.1f %-8s\n", 
                                      s.name, s.assignmentScore, s.examScore, s.finalGrade, s.status);
                    classTotal += s.finalGrade;
                }
                System.out.println("-----------------------------------------------------------------");
                System.out.printf("Class Average Grade Metric: %.2f%%\n", (classTotal / studentList.size()));
                
            } else if (choice == 2) {
                System.out.print("Enter Student Full Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Assignment Score (0-100): ");
                double assign = scanner.nextDouble();
                System.out.print("Enter Exam Score (0-100): ");
                double exam = scanner.nextDouble();
                
                studentList.add(new Student(name, assign, exam));
                System.out.println("[SUCCESS] Performance records updated inside the matrix.");
                
            } else if (choice == 3) {
                System.out.println("Shutting down the performance tracking system. Goodbye!");
                break;
            } else {
                System.out.println("[ERROR] Invalid selection. Please enter a number between 1 and 3.");
            }
        }
        scanner.close();
    }
}