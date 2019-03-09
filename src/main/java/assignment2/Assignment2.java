package assignment2;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assignment2 {

    List<Mobile> mobileList;

    public Assignment2() {
        this.mobileList = new ArrayList<>();
    }

    void readInput() throws IOException{
        File file = new File("inputPS1.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String data;
        while ((data = reader.readLine()) != null) {
            String[] tokens = data.split("/");
            if (tokens.length != 3) {
                throw new IOException("Invalid input " + data + ". Valid input format is <mobile id>/<pm>/<a>");
            }
            Mobile mobile = new Mobile(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            mobileList.add(mobile);
        }
    }

    void findOptimalProductionSequence(){
        Collections.sort(mobileList, new MobileComparator());
        int totalManufactureTime = 0;
        int assemblyTimeLeft = 0;
        for(Mobile m : mobileList){
            System.out.println(m.toString());
            int waitTime = 0;
            if(totalManufactureTime == 0){
                //This is for the first mobile
                totalManufactureTime += m.partManufactureTime;
                assemblyTimeLeft += m.assemblyTime;
                System.out.println("totalManufactureTime=" + totalManufactureTime + ", assemblyTimeLeft=" +
                        assemblyTimeLeft + ", waitTime=" + waitTime);
            } else {
                if(assemblyTimeLeft < m.partManufactureTime){
                    waitTime = m.partManufactureTime - assemblyTimeLeft;
                    assemblyTimeLeft = 0;
                } else {
                    assemblyTimeLeft -= m.partManufactureTime;
                }
                totalManufactureTime += m.partManufactureTime;
                int assemblyTimeLeftBefore = assemblyTimeLeft;
                assemblyTimeLeft += m.assemblyTime;
                System.out.println("totalManufactureTime=" + totalManufactureTime
                        + ", assemblyTimeLeftBefore=" + assemblyTimeLeftBefore
                        + ", assemblyTimeLeft=" + assemblyTimeLeft + ", waitTime=" + waitTime);
            }
        }
        totalManufactureTime += assemblyTimeLeft;

        System.out.println("Production Time=" + totalManufactureTime);
    }

    public static void main(String[] args) throws IOException{
        Assignment2 app = new Assignment2();
        app.readInput();
        app.findOptimalProductionSequence();
    }
}
