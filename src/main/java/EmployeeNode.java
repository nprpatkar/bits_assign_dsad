public class EmployeeNode {

    enum Color {BLACK, RED};

    int empId, attCount;
    EmployeeNode left, right, parent;
    Color color;

    public EmployeeNode(int empId) {
        this.empId = empId;
        this.attCount = 1;
        this.color = Color.RED;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}
