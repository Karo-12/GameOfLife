package life;

import java.util.ArrayList;
import java.util.Random;

public class Row {

    public ArrayList<String> row;
    int n;
    Random rand;

    public Row(int n, Random rand) {
        this.n = n;
        this.row = new ArrayList<>();
        this.rand = rand;
        generateRow();
    }
    public Row(int n) {
        this.n = n;
        this.row = new ArrayList<>();
    }
    void generateRow() {

        for(int i = 0; i < n; i++) {
            if(rand.nextBoolean()) {
                row.add("O");
            } else {
                row.add(" ");
            }
        }
    }
    void printRow() {
        for(String s: row) {
            System.out.print(s);
        }
    }

    boolean isNull(Row r) {
        if(r == null) {
            return true;
        } else {
            return false;
        }
    }

}