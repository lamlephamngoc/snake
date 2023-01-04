package bond.memo.practice.snake;

import bond.memo.practice.snake.event.EventListener;
import bond.memo.practice.snake.event.EventManager;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static javax.swing.JOptionPane.YES_NO_OPTION;

public class GameController {

    public static final int WIN_SIZE = 50;
    private JLabel[][] labels;
    private Snake snake;
    private final int row;
    private final int col;
    private final Sound sound;
    private final JFrame frame;
    private final EventManager eventManager;

    public GameController(JFrame frame, JLabel[][] labels, Snake snake, EventManager eventManager) {
        this.labels = labels;
        this.row = labels.length;
        this.col = labels[0].length;
        this.snake = snake;
        this.frame = frame;
        this.eventManager = eventManager;
        setControl(frame);
        sound = new Sound();

    }

    public void die() {
        sound.setSound("/very-infectious-laugh.wav");
        sound.play();
        eventManager.notify(EventListener.STOP, null);
        askForRestart("You Lose !!! Lêu Lêu");
    }

    public void setControl(JFrame frame) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                Snake.SnakePosition head = snake.getHead();
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (head.getRow() - 1 > 0
                            && labels[head.getRow() - 1][head.getCol()].getBackground() == SnakeEnum.FOOD.getColor()) {
                        // eat
                        new SnakeEatUp().eat();
                    } else if (head.getRow() - 1 > 0
                            && labels[head.getRow() - 1][head.getCol()].getBackground() == SnakeEnum.POISON.getColor()
                    ) {
                        die();
                    } else {
                        new SnakeMoveUp().move();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (head.getRow() + 1 < labels.length &&
                            labels[head.getRow() + 1][head.getCol()].getBackground() == SnakeEnum.FOOD.getColor()) {
                        // eat
                        new SnakeEatDown().eat();
                    } else if (head.getRow() + 1 < labels.length &&
                            labels[head.getRow() + 1][head.getCol()].getBackground() == SnakeEnum.POISON.getColor()) {
                        die();
                    } else {
                        new SnakeMoveDown().move();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (head.getCol() + 1 < labels[0].length &&
                            labels[head.getRow()][head.getCol() + 1].getBackground() == SnakeEnum.FOOD.getColor()) {
                        // eat
                        new SnakeEatRight().eat();
                    } else if (head.getCol() + 1 < labels[0].length &&
                            labels[head.getRow()][head.getCol() + 1].getBackground() == SnakeEnum.POISON.getColor()) {
                        die();
                    } else {
                        new SnakeMoveRight().move();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (head.getCol() - 1 > 0 &&
                            labels[head.getRow()][head.getCol() - 1].getBackground() == SnakeEnum.FOOD.getColor()) {
                        // eat
                        new SnakeEatLeft().eat();
                    } else if (head.getCol() - 1 > 0 &&
                            labels[head.getRow()][head.getCol() - 1].getBackground() == SnakeEnum.POISON.getColor()) {
                        die();
                    } else {
                        new SnakeMoveLeft().move();
                    }
                }
            }
        });
    }

    private abstract class SnakeMove {

        protected abstract boolean moveDirection();

        public void move() {
            if (moveDirection()) {
                Snake.SnakePosition head = snake.getHead();
                labels[head.getRow()][head.getCol()]
                        .setBackground(SnakeEnum.SNAKE.getColor());
                Snake.SnakePosition tail = snake.getTail();
                labels[tail.getRow()][tail.getCol()]
                        .setBackground(SnakeEnum.BACKGROUND.getColor());
                snake.removeTail();
            }
        }
    }

    private class SnakeMoveUp extends SnakeMove {

        @Override
        protected boolean moveDirection() {
            Snake.SnakePosition head = snake.getHead();
            if (head.getRow() > 0) {
                snake.moveUp();
                return true;
            }
            return false;
        }
    }

    private class SnakeMoveDown extends SnakeMove {
        @Override
        protected boolean moveDirection() {
            Snake.SnakePosition head = snake.getHead();
            if (head.getRow() < row - 1) {

                snake.moveDown();
                return true;
            }
            return false;
        }
    }

    private class SnakeMoveRight extends SnakeMove {
        @Override
        protected boolean moveDirection() {
            Snake.SnakePosition head = snake.getHead();
            if (head.getCol() < col - 1) {

                snake.moveRight();
                return true;
            }
            return false;
        }
    }

    private class SnakeMoveLeft extends SnakeMove {
        @Override
        protected boolean moveDirection() {
            Snake.SnakePosition head = snake.getHead();
            if (head.getCol() > 0) {
                snake.moveLeft();
                return true;
            }
            return false;
        }
    }

    private abstract class SnakeEat {
        public void eat() {
            eatDirection();
            snake.eat();
            eventManager.notify(EventListener.EAT, snake.getAte() + "");
            Snake.SnakePosition head = snake.getHead();
            labels[head.getRow()][head.getCol()]
                    .setBackground(SnakeEnum.SNAKE.getColor());
            sound.setSound("/food.wav");
            sound.adjustVol(1f);
            sound.play();
            if (snake.getAte() >= WIN_SIZE) {
                eventManager.notify(EventListener.STOP, null);
                JOptionPane.showMessageDialog(frame, "You Win !!!");
                askForRestart("You Win !!! Congrats");
            }
        }

        abstract void eatDirection();
    }

    private void askForRestart(String title) {
        int i = JOptionPane.showConfirmDialog(frame, "Chơi lại ko ?", title, YES_NO_OPTION);
        // yes
        if (i == 0) {
            eventManager.notify(EventListener.RESTART, null);
        } else {
//            JOptionPane.showMessageDialog(frame, "Con chóa này!!!");
            System.exit(0);
        }
    }

    private class SnakeEatUp extends SnakeEat {

        @Override
        void eatDirection() {
            snake.moveUp();
        }
    }

    private class SnakeEatDown extends SnakeEat {

        @Override
        void eatDirection() {
            snake.moveDown();
        }
    }

    private class SnakeEatRight extends SnakeEat {

        @Override
        void eatDirection() {
            snake.moveRight();
        }
    }

    private class SnakeEatLeft extends SnakeEat {

        @Override
        void eatDirection() {
            snake.moveLeft();
        }
    }

}
