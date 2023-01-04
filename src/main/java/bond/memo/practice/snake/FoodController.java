package bond.memo.practice.snake;

import bond.memo.practice.snake.event.EventListener;

import javax.swing.*;
import java.util.Random;

/**
 * Random create food
 */
public class FoodController implements Runnable, EventListener {

    private final JLabel[][] labels;
    private final Snake snake;
    private volatile boolean running = true;

    public FoodController(JLabel[][] labels, Snake snake) {
        this.labels = labels;
        this.snake = snake;
    }

    @Override
    public void run() {
        while (true) {
            if (running) {
                int row = labels.length;
                int col = labels[0].length;

                Random rand = new Random();
                int randRow = rand.nextInt(row - 1);
                int randCol = rand.nextInt(col - 1);
                while (snake.contains(randCol, randRow)) {
                    rand = new Random();
                    randRow = rand.nextInt(row - 1);
                    randCol = rand.nextInt(col - 1);
                }
                labels[randRow][randCol].setBackground(SnakeEnum.FOOD.getColor());
                try {
                    Thread.sleep(5_00);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void update(String eventType, Object data) {
        if (eventType.equals(EventListener.STOP)) {
            running = false;
        } else if (eventType.equals(EventListener.RESTART)) {
            running = true;
        }
    }
}
