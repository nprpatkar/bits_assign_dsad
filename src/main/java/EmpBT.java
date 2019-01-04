public class EmpBT {

    EmployeeNode readEmployees(EmployeeNode root, int empId){
        EmployeeNode newNode = new EmployeeNode(empId);
        EmployeeNode node = insert(root, newNode);

        if(node == null){
            return root;
        } else {
            node = fixViolations(node, newNode);
            return node;
        }
    }

    int getHeadCount(EmployeeNode root){
        return 0;
    }

    boolean searchId(EmployeeNode root, int empId){
        return false;
    }

    int howOften(EmployeeNode root, int empId){
        return 0;
    }

    EmployeeNode frequentVisitor(EmployeeNode root){
        return null;
    }

    void printRangePresent(EmployeeNode root, int empId1, int empId2){

    }

    EmployeeNode insert(EmployeeNode root, EmployeeNode newNode){
        if(root == null){
            return newNode;
        }

        if(root.empId == newNode.empId){
            root.attCount += 1; //If the empId is already present in the tree increae the attCount
            return null; //Return null so that the tree need not be balanced
        } else if(newNode.empId < root.empId){
            EmployeeNode node = insert(root.left, newNode);
            if(node != null){
                //A new node is added and hence pointers need to be updated
                root.left = node;
                node.parent = root;
                return root;
            } else {
                return null; //Return null so that the tree need not be balanced
            }
        } else {
            EmployeeNode node = insert(root.right, newNode);
            if(node != null){
                //A new node is added and hence pointers need to be updated
                root.right = node;
                node.parent = root;
                return root;
            } else {
                return null; //Return null so that the tree need not be balanced
            }
        }
    }

    EmployeeNode fixViolations(EmployeeNode root, EmployeeNode node){
        EmployeeNode parent;
        EmployeeNode grandParent;

        while(node != root && node.color != EmployeeNode.Color.BLACK && node.parent.color == EmployeeNode.Color.RED){
            parent = node.parent;
            grandParent = node.parent.parent;

            if(parent == grandParent.left){
                EmployeeNode uncle = grandParent.right;

                if(uncle != null && uncle.color == EmployeeNode.Color.RED){
                    grandParent.color = EmployeeNode.Color.RED;
                    parent.color = EmployeeNode.Color.BLACK;
                    uncle.color = EmployeeNode.Color.BLACK;
                    node = grandParent;
                } else {
                    if(node == parent.right){
                        root = rotateLeft(root, parent);
                        node = parent;
                        parent = node.parent;
                    }

                    root = rotateRight(root, grandParent);
                    swapColor(parent, grandParent);
                    node = parent;
                }
            } else {
                EmployeeNode uncle = grandParent.left;

                if(uncle != null && uncle.color == EmployeeNode.Color.RED){
                    grandParent.color = EmployeeNode.Color.RED;
                    parent.color = EmployeeNode.Color.BLACK;
                    uncle.color = EmployeeNode.Color.BLACK;
                    node = grandParent;
                } else {
                    if(node == parent.left){
                        root = rotateRight(root, parent);
                        node = parent;
                        parent = node.parent;
                    }

                    root = rotateLeft(root, grandParent);
                    swapColor(parent, grandParent);
                    node = parent;
                }
            }
        }

        root.color = EmployeeNode.Color.BLACK;
        return root;
    }

    EmployeeNode rotateLeft(EmployeeNode root, EmployeeNode node){
        EmployeeNode rightNode = node.right;

        node.right = rightNode.left;

        if(node.right != null){
            node.right.parent = node;
        }

        rightNode.parent = node.parent;

        if(node.parent == null){
            root = rightNode;
        } else if( node == node.parent.left){
            node.parent.left = rightNode;
        } else {
            node.parent.right = rightNode;
        }

        rightNode.left = node;
        node.parent = rightNode;
        return root;

    }

    EmployeeNode rotateRight(EmployeeNode root, EmployeeNode node){
        EmployeeNode leftNode = node.left;

        node.left = leftNode.right;

        if (node.left != null)
            node.left.parent = node;

        leftNode.parent = node.parent;

        if(node.parent == null){
            root = leftNode;
        } else if(node == node.parent.left){
            node.parent.left = leftNode;
        } else {
            node.parent.right = leftNode;
        }

        leftNode.right = node;
        node.parent = leftNode;

        return root;
    }

    void swapColor(EmployeeNode node1, EmployeeNode node2){
        EmployeeNode.Color color = node1.color;
        node1.color = node2.color;
        node2.color = color;
    }

    void inOrderTraversal(EmployeeNode root){
        if(root == null){
            return;
        }
        inOrderTraversal(root.left);
        System.out.println(root.empId + "::" + root.attCount + "::" + root.color);
        inOrderTraversal(root.right);
    }

    int height(EmployeeNode root){
        if(root == null){
            return 0;
        }
        return Math.max(height(root.left), height(root.right)) + 1;
    }

}
