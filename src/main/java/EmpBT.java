import java.io.*;

public class EmpBT {

    BufferedWriter bufferedWriter;

    EmployeeNode readEmployees(EmployeeNode root, int empId){
        EmployeeNode newNode = new EmployeeNode(empId); //Create a node for the employee id
        EmployeeNode node = insert(root, newNode); //Insert the node in BST

        if(node == null){
            //Insert returns null if the empId was present in the tree
            //No need to run balancing if the node was present as only attCount was incremented
            return root;
        } else {
            node = fixViolations(node, newNode); //Balance the tree to fix any violations caused by inserting new node
            return node;
        }
    }

    int getHeadCount(EmployeeNode root){
        if(root == null){
            return 0;
        };
        int headCount = 1; //Count the current node
        headCount += getHeadCount(root.left); //Count the nodes in the left subtree
        headCount += getHeadCount(root.right); //Count the nodes in the right subtree
        return headCount;
    }

    boolean searchId(EmployeeNode root, int empId){
        if(root == null){
            return false; //If empId not found return false
        }

        if(root.empId == empId){
            return true; //If empId found return true
        } else if(empId < root.empId){
            //Search for empId in left subtree of the root
            return searchId(root.left, empId);
        } else {
            //Search for empId in the right subtree of the root
            return searchId(root.right, empId);
        }
    }

    int howOften(EmployeeNode root, int empId){
        if(root == null){
            return 0; //If empId not found return 0
        }

        if(root.empId == empId){
            //If empId found then return the number of times employee entered office
            return ((root.attCount/2) + (root.attCount%2));
        } else if(empId < root.empId){
            //Search for empId in the left subtree of the root
            return howOften(root.left, empId);
        } else {
            //Search for empId in the right subtree of the root
            return howOften(root.right, empId);
        }
    }

    EmployeeNode frequentVisitor(EmployeeNode root){
        //Initiate the root node as the frequent visitor and scan the complete tree to get the frequent visitor
        EmployeeNode tempFrequentVisitor = root;
        return frequentVisitor(root, tempFrequentVisitor);
    }

    EmployeeNode frequentVisitor(EmployeeNode root, EmployeeNode frequentVisitor){
        if(root == null){
            return frequentVisitor; //Terminating condition
        }

        EmployeeNode tempFrequentVisitor;

        //If root attCount is greater than the frequent visitor till now, then set the root as the new frequent visitor
        if(root.attCount > frequentVisitor.attCount){
            tempFrequentVisitor = root;
        } else {
            tempFrequentVisitor = frequentVisitor;
        }

        tempFrequentVisitor = frequentVisitor(root.left, tempFrequentVisitor); //Get the frequent visitor from left subtree
        tempFrequentVisitor = frequentVisitor(root.right, tempFrequentVisitor); //Ger the frequent visitor from right subtree

        return  tempFrequentVisitor;
    }

    void printRangePresentToFile(EmployeeNode root, int empId1, int empId2){
        File file = new File("output.txt");

        FileWriter fileWriter = null;
        try{
            file.createNewFile(); //Create output file
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            printRangePresent(root, empId1, empId2);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                bufferedWriter.flush();
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    void printRangePresent(EmployeeNode root, int empId1, int empId2) throws IOException{
        if(root == null){
            return; //Terminating condition
        }

        //If the empId1 is less than roots empId then visit the left subtree for scanning
        if(empId1 < root.empId){
            printRangePresent(root.left, empId1, empId2);
        }

        //If the roots empId is in the range print it to the file
        //TODO: What should be printed to file - attCount or number of times emp entered organization
        if(empId1 <= root.empId && root.empId <= empId2){
            bufferedWriter.write(root.empId + ", " + root.attCount + "\n");
        }

        //If roots empId is less than empId2 then visit the right subtree for scanning
        if(root.empId < empId2){
            printRangePresent(root.right, empId1, empId2);
        }
    }

    EmployeeNode insert(EmployeeNode root, EmployeeNode newNode){
        if(root == null){
            return newNode;
        }

        if(root.empId == newNode.empId){
            root.attCount += 1; //If the empId is already present in the tree increase the attCount
            return null; //Return null so that the tree need not be balanced
        } else if(newNode.empId < root.empId){
            //Insert the node at the left of the root
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
            //Insert the node to the right of the root
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
