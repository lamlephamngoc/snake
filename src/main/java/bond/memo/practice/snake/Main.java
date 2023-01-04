package bond.memo.practice.snake;

import bond.memo.practice.snake.event.EventListener;
import bond.memo.practice.snake.event.EventManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Main {

    static int x = 20;
    static int y = 15;
    static int[][] maze = new int[x][y];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> show());
        mainSoundTrack();
    }

    private static void mainSoundTrack() {
        Thread t = new Thread(() -> {
            Sound mainTrack = new Sound();
            while (true) {
                mainTrack.setSound("/hip-hop-rock.wav");
                mainTrack.adjustVol(-10.0f);
                mainTrack.play();
                try {
                    Thread.sleep(80_000 + 100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();
    }


    private static void show() {
        // observe design pattern
        EventManager eventManager = new EventManager(EventListener.STOP,
                EventListener.EAT, EventListener.RESTART);
        Snake snake = new Snake(maze);
        eventManager.subscribe(EventListener.RESTART, snake);
        int row = y;
        int col = x;
        JFrame frame = new JFrame("Snake");


        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();
        contentPane.setPreferredSize(new Dimension(1200, 800));
        contentPane.add(panel);
        panel.setLayout(new GridLayout(row, col));
        JLabel[][] labels = new JLabel[row][col];

        RenderGame renderGame = new RenderGame(snake, row, col, panel, labels);
        eventManager.subscribe(EventListener.RESTART, renderGame);
        renderGame.reRenderGame();

        gameHeader(frame, eventManager);

        new GameController(frame, labels, snake, eventManager);
        FoodController foodController = new FoodController(labels, snake);
        PoisonController poisonController = new PoisonController(labels, snake);
        eventManager.subscribe(EventListener.STOP, foodController);
        eventManager.subscribe(EventListener.STOP, poisonController);
        eventManager.subscribe(EventListener.RESTART, foodController);
        eventManager.subscribe(EventListener.RESTART, poisonController);

        Thread foodControllerThread = new Thread(foodController);
        foodControllerThread.start();
        Thread poisonControllerThread = new Thread(poisonController);
        poisonControllerThread.start();


        frame.pack();
        frame.setVisible(true);
    }

    static class RenderGame implements EventListener {
        Snake snake;
        int row;
        int col;
        JPanel panel;
        JLabel[][] labels;

        public RenderGame(Snake snake, int row, int col, JPanel panel, JLabel[][] labels) {
            this.snake = snake;
            this.row = row;
            this.col = col;
            this.panel = panel;
            this.labels = labels;

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    labels[i][j] = new JLabel();
                    var l = labels[i][j];
                    l.setBorder(new LineBorder(Color.BLACK));
                    l.setBackground(SnakeEnum.BACKGROUND.getColor());
                    l.setOpaque(true);
                    if (snake.contains(j, i)) {
                        l.setBackground(SnakeEnum.SNAKE.getColor());
                    }
                    panel.add(l);
                }
            }
        }

        void reRenderGame() {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    var l = labels[i][j];
                    l.setBorder(new LineBorder(Color.BLACK));
                    l.setBackground(SnakeEnum.BACKGROUND.getColor());
//                    l.setOpaque(true);
                    if (snake.contains(j, i)) {
                        l.setBackground(SnakeEnum.SNAKE.getColor());
                    }
                }
            }
        }

        @Override
        public void update(String eventType, Object data) {
            reRenderGame();
        }
    }


    static void setFont(JLabel l) {
        l.setFont(new Font("arial", Font.PLAIN, 20));
        l.setForeground(Color.MAGENTA);
    }

    private static void gameHeader(JFrame frame, EventManager eventManager) {
        JLabel winCondition = new JLabel("Winner eat `" + GameController.WIN_SIZE + "` Food");
        JLabel timerTitle = new JLabel("Timer:");
        JLabel timer = new JLabel("00:00");
        JLabel guide = new JLabel("<html> Food is Green <br/> Poison is Black </html>");
        JLabel ateTitle = new JLabel("Ate:");
        JLabel ate = new JLabel("0");

        long start = System.currentTimeMillis();
        SetTimer setTimer = new SetTimer(start, timer);
        SetAte setAte = new SetAte((ate));
        eventManager.subscribe(EventListener.EAT, setAte);
        eventManager.subscribe(EventListener.RESTART, setAte);
        eventManager.subscribe(EventListener.STOP, setTimer);
        eventManager.subscribe(EventListener.RESTART, setTimer);
        Thread setTimerThread = new Thread(setTimer);
        setTimerThread.start();


        setFont(winCondition);
        setFont(timer);
        setFont(timerTitle);
        setFont(guide);
        setFont(ateTitle);
        setFont(ate);
        guide.setForeground(Color.RED);

        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 10, 0, 0);
        p.add(winCondition, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0;
        p.add(timerTitle, c);

        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0.5;
        p.add(timer, c);

        c.gridx = 3;
        c.gridy = 0;
        c.weightx = 0.5;
        p.add(guide, c);

        c.gridx = 4;
        c.gridy = 0;
        c.weightx = 0;
        p.add(ateTitle, c);

        c.gridx = 5;
        c.gridy = 0;
        c.weightx = 0;
        c.insets = new Insets(0, 0, 0, 10);
        p.add(ate, c);

        p.setPreferredSize(new Dimension(1200, 50));
        frame.add(p, BorderLayout.NORTH);
    }


}