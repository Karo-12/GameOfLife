package life;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    public ArrayList<Row> matrix;
    int n;
    Random rand;

    public Map(int n) {
        this.n = n;
        this.matrix = new ArrayList<>();
        this.rand = new Random();
        generateMap();
    }
    public Map(int n, Random rand) {
        this.n = n;
        this.matrix = new ArrayList<>();
        this.rand = rand;
    }
    public Map(int n, ArrayList matrix, Random rand) {
        this.n = n;
        this.rand = rand;
        this.matrix = (ArrayList)matrix.clone();
    }
    public void generateMap() {
        for(int i = 0; i < n; i++) {
            Row r = new Row(n, rand);
            matrix.add(r);
        }
    }
    public void printMap() {
        for(Row r : matrix) {
            r.printRow();
            System.out.println();
        }
    }
    public Map oneGeneration() {
        Map newMap = new Map(n, rand);
        for(int i = 0; i < n; i++) {
            Row r = new Row(n);
            for(int j = 0; j < n; j++) {
                String newState = newState(j,i);
                r.row.add(newState);
            }
            newMap.matrix.add(r);
        }
        return newMap;
    }
    public boolean isInTheMap(int r, int c) {
        if(r >= 0 && r < n && c >= 0 && c < n) {
            return true;
        } else {
            return false;
        }
    }
    int aliveN(int r, int m) {
        String neighbourN = "";
        if(isInTheMap(r,m - 1)) {
            neighbourN = matrix.get(m - 1).row.get(r);
        } else {
            neighbourN = cellUp(r, 0);
        }
        if("O".equals(neighbourN)) {
            return 1;
        } else {
            return 0;
        }
    }
    int aliveNE(int r, int m) {
        String neighbourNE = "";
        if(isInTheMap(r + 1, m - 1)) {
            neighbourNE = matrix.get(m - 1).row.get(r + 1);
        } else if( r == n - 1 && m == 0) {
            neighbourNE = cellUpRight();
        } else if( m == 0) {
            neighbourNE = cellUp(r, 1);
        } else {
            neighbourNE = cellRight(m, - 1);
        }
        if("O".equals(neighbourNE)) {
            return 1;
        } else {
            return 0;
        }
    }
    int aliveNW (int r, int m) {
        String neighbourNW = "";
        if(isInTheMap(r - 1, m - 1)) {
            neighbourNW = matrix.get(m - 1).row.get(r - 1);
        } else if( r == 0 && m == 0) {
            neighbourNW = cellUpLeft();
        } else if( r == 0) {
            neighbourNW = cellLeft(m, -1);
        } else {
            neighbourNW = cellUp(r, - 1);
        }
        if("O".equals(neighbourNW)) {
            return 1;
        } else {
            return 0;
        }
    }
    int aliveW(int r, int m) {
        String neighbourW = "";
        if(isInTheMap(r - 1, m)) {
            neighbourW = matrix.get(m).row.get(r-1);
        } else {
            neighbourW = cellLeft(m,0);
        }
        if("O".equals(neighbourW)) {
            return 1;
        } else {
            return 0;
        }
    }
    int aliveE(int r, int m) {
        String neighbourE = "";
        if(isInTheMap(r + 1, m)) {
            neighbourE = matrix.get(m).row.get(r + 1);
        } else {
            neighbourE = cellRight(m,0);
        }
        if("O".equals(neighbourE)) {
            return 1;
        } else {
            return 0;
        }
    }
    int aliveS(int r, int m) {
        String neighbourS = "";
        if(isInTheMap(r, m + 1)) {
            neighbourS = matrix.get(m + 1).row.get(r);
        } else {
            neighbourS = cellDown(r,0);
        }
        if("O".equals(neighbourS)) {
            return 1;
        } else {
            return 0;
        }
    }
    int aliveSE (int r, int m) {
        String neighbourSE = "";
        if(isInTheMap(r + 1, m + 1)) {
            neighbourSE = matrix.get(m + 1).row.get(r + 1);
        } else if( r == n - 1 && m == n - 1) {
            neighbourSE = cellDownRight();
        } else if(r == n - 1) {
            neighbourSE = cellRight(m, 1);
        } else {
            neighbourSE = cellDown(r,1);
        }
        if("O".equals(neighbourSE)) {
            return 1;
        } else {
            return 0;
        }
    }
    int aliveSW (int r, int m) {
        String neighbourSW = "";
        if(isInTheMap(r - 1, m + 1)) {
            neighbourSW = matrix.get(m + 1).row.get(r - 1);
        } else if (r == 0 && m == n - 1) {
            neighbourSW = cellDownLeft();
        } else if(r == 0) {
            neighbourSW = cellLeft(m,1);
        } else {
            neighbourSW = cellDown(r, -1);
        }
        if("O".equals(neighbourSW)) {
            return 1;
        } else {
            return 0;
        }
    }

    public String cellUp(int r, int shiftR) {
        return matrix.get(n - 1).row.get(r + shiftR);
    }
    public String cellDown(int r, int shiftR) {
        return matrix.get(0).row.get(r + shiftR);
    }
    public String cellRight(int m, int shiftM) {
        return matrix.get(m + shiftM).row.get(0);
    }
    public String cellLeft(int m, int shiftM) {
        return matrix.get(m + shiftM).row.get(n - 1);
    }
    public String cellUpRight() {
        return matrix.get(n-1).row.get(0);
    }
    public String cellUpLeft() {
        return matrix.get(n-1).row.get(n-1);
    }
    public String cellDownRight() {
        return matrix.get(0).row.get(0);
    }
    public String cellDownLeft() {
        return matrix.get(0).row.get(n-1);
    }
    int aliveCells() {
        int alive = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if("O".equals(matrix.get(i).row.get(j))) {
                    alive ++;
                }
            }
        }
        return alive;
    }


    @Override
    public Object clone() {
        Map map = null;
        try {
            map = (Map) super.clone();
        } catch (CloneNotSupportedException e) {
            map = new Map(this.n, this.matrix, this.rand);
        }
        return map;
    }
    int aliveNeighbours(int r, int m) {
        return aliveN(r, m) + aliveE(r,m) + aliveNE(r,m) + aliveNW(r,m) + aliveW(r,m)
                + aliveS(r,m) + aliveSE(r, m) + aliveSW(r,m);
    }
    public String newState(int r, int m) {
        int alive = aliveNeighbours(r,m);
        String newState = "";
        String oldState = matrix.get(m).row.get(r);
        if(oldState.equals("O") && alive < 2 || alive > 3) {
            newState = " ";
        } else if(oldState.equals(" ") && alive == 3) {
            newState ="O";
        } else {
            newState = oldState;
        }
        return newState;
    }


}