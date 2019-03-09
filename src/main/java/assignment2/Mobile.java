package assignment2;

public class Mobile {

    int mobileId;
    int partManufactureTime;
    int assemblyTime;

    public Mobile(int mobileId, int partManufactureTime, int assemblyTime) {
        this.mobileId = mobileId;
        this.partManufactureTime = partManufactureTime;
        this.assemblyTime = assemblyTime;
    }

    @Override
    public String toString() {
        return "Mobile{" +
                "mobileId=" + mobileId +
                ", partManufactureTime=" + partManufactureTime +
                ", assemblyTime=" + assemblyTime +
                '}';
    }


}
