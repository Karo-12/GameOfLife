package life;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class GameOfLife extends JFrame {

    Game game;
    MatrixView mv;
    boolean isPaused = false;
    private final Object lock = new Object();

    public GameOfLife() {
        super("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        initComponents();
        setLayout(new BorderLayout());
        setVisible(true);

    }
    private void initComponents(){
        Game game = new Game(50, 5);
        JLabel generationLabel = new JLabel("Generation #" );
        generationLabel.setBounds(40,20, 100,30);
        generationLabel.setName("GenerationLabel");
        add(generationLabel, BorderLayout.NORTH);

        JLabel aliveLabel = new JLabel("Alive: " );
        aliveLabel.setBounds(40,40, 100,30);
        aliveLabel.setName("AliveLabel");
        getContentPane().add(aliveLabel, BorderLayout.NORTH);

        JToggleButton playtogglebutton = new JToggleButton();
        playtogglebutton.setName("PlayToggleButton");
        playtogglebutton.setText("Pause");
        playtogglebutton.setBounds(200, 30, 70, 30);
        add(playtogglebutton);
        playtogglebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(isPaused == false) {
                    isPaused = true;
                    playtogglebutton.setText("Resume");
                } else {
                    isPaused = false;
                    playtogglebutton.setText("Pause");
                    synchronized (lock) {
                        if(isPaused == false) {
                            lock.notifyAll();
                        }
                    }

                }
            }
        });
        JButton resetButton = new JButton();
        resetButton.setName("ResetButton");
        resetButton.setText("Reset");
        resetButton.setBounds(300, 30, 70, 30);
        add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                new GameOfLife();
            }
        });

        mv = new MatrixView(game);
        add(mv, BorderLayout.CENTER);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                    int counter = 0;
                    while (true) {
                        synchronized (lock) {
                            if(isPaused == true) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        game.doGeneration();
                        aliveLabel.setText("Alive: " + game.currentGeneration.aliveCells());
                        generationLabel.setText("Generation #" + counter);
                        counter++;
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                repaint();
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ie) {
                            System.out.println("Interrupted");
                        }
                    }
                }
        });
        t.start();

    }
}


class MatrixView extends JPanel {
    Game game;

    public MatrixView(Game game) {
        setBackground(Color.white);
        setSize(300, 300);
        setBounds(40, 100, 401, 401);
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRectangles(g);
    }

    public void drawRectangles(Graphics g) {
        int size = 400 / game.n;
        for (int i = 0; i < game.n; i++) {
            for (int j = 0; j < game.n; j++) {
                if (game.currentGeneration.matrix.get(i).row.get(j).equals("O")) {
                    g.setColor(Color.gray);
                    g.fillRect(0 + size * i, 0 + size * j, size, size);
                }
                g.setColor(Color.black);
                g.drawRect(0 + size * i, 0 + size * j, size, size);
            }
        }
    }

}
