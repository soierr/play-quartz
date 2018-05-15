package play.quartz;

import java.io.Serializable;

public class Buffer implements Serializable {

    private int count = 0;

    private String row = "row";

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }
}
