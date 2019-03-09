package assignment2;

import java.util.Comparator;

public class MobileComparator implements Comparator<Mobile> {

    @Override
    public int compare(Mobile o1, Mobile o2) {
        if(o1.partManufactureTime != o2.partManufactureTime){
            return o1.partManufactureTime - o2.partManufactureTime;
        } else {
            return o2.assemblyTime - o1.assemblyTime;
        }
    }
}
