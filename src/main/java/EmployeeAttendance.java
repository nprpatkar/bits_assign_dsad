import java.io.*;
import java.util.Scanner;

public class EmployeeAttendance {

    EmployeeNode root;
    EmpBT empBT;
    Scanner input;

    EmployeeAttendance() {
        this.root = null;
        this.empBT = new EmpBT();
        this.input = new Scanner(System.in);
    }

    public static void main(String[] args) throws IOException {
        EmployeeAttendance employeeAttendance = new EmployeeAttendance();
        employeeAttendance.createBinaryTree();
        while(true) {
            System.out.println("\nChoose one of the options from:\n" +
                    "1. Get employee head count.\n" +
                    "2. Search employee.\n" +
                    "3. Get number of time employee entered office.\n" +
                    "4. Get most frequent visited employee.\n" +
                    "5. Print employees in range.\n" +
                    "6. Any other number to exit");
            System.out.println("Enter option:");
            int option = employeeAttendance.input.nextInt();
            if(option >=1 && option <=5){
                employeeAttendance.processOption(option);
            } else {
                break;
            }
        }
    }

    void createBinaryTree() throws IOException {
        File file = new File("input.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String empId;
        while(( empId = reader.readLine()) != null){
            root = empBT.readEmployees(root, Integer.parseInt(empId));
        }
        //empBT.inOrderTraversal(root);
        //System.out.println("\n");
        //empBT.levelOrderTraversal(root);
        //System.out.println("\n");
        //System.out.println("Root is " + root.empId);
        //System.out.println("Height if tree = " + empBT.height(root));

    }

    void processOption(int option){
        switch (option){
            case 1:
                int headCount = empBT.getHeadCount(root);
                System.out.println("Head count: " + headCount);
                break;
            case 2:
                System.out.println("Enter employee id:");
                int empId = this.input.nextInt();
                boolean isPresent = empBT.searchId(root, empId);
                if(isPresent){
                    System.out.println("Employee " + empId + " is present in organization.");
                } else {
                    System.out.println("Employee " + empId + " is not present in organization.");
                }
                break;
            case 3:
                System.out.println("Enter employee id:");
                empId = this.input.nextInt();
                int count = empBT.howOften(root, empId);
                System.out.println("Number of times employee " + empId + " entered the organization: " + count);
                break;
            case 4:
                EmployeeNode node = empBT.frequentVisitor(root);
                System.out.println("Employee " + node.empId + " is the most frequent visitor with "
                        + node.attCount + " visits.");
                break;
            case 5:
                System.out.println("Enter range of emp ids:");
                int empId1 = this.input.nextInt();
                int empId2 = this.input.nextInt();
                if(empId1 <= empId2) {
                    empBT.printRangePresentToFile(root, empId1, empId2);
                } else {
                    System.out.println("Incorrect input empId1 should be less than empId2");
                }
        }
    }
}
