package life;

import java.io.IOException;
import java.util.Scanner;

public class Game {
    Map currentGeneration;
    Map nextGeneration;
    int n;
    int g;

    public Game(int n, int g) {
        this.n = n;
        this.g = g;
        currentGeneration = new Map(n);
        nextGeneration = (Map) currentGeneration.clone();
    }
    public void start() {
        doGeneration();
    }
    public void doGeneration() {
            nextGeneration = currentGeneration.oneGeneration();
            currentGeneration = (Map)nextGeneration.clone();
            //System.out.println("Generation #"+i);
            //System.out.println("Alive: "+ currentGeneration.aliveCells());
            //printUniverse();
    }
    public void printUniverse() {
        if(g > 0) {
            nextGeneration.printMap();
        } else {
            currentGeneration.printMap();
        }
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}