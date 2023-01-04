package bond.memo.practice.snake;

import bond.memo.practice.snake.event.EventListener;

import java.util.*;

public class Snake implements EventListener {
    private LinkedList<SnakePosition> body;
    private int ate;
    private int[][] maze;

    public Snake(int[][] maze) {
        // random first display
        init(maze);
        this.maze = maze;
    }

    private void init(int[][] maze) {
        this.ate = 0;
        Random rand = new Random();
        int y = rand.nextInt(maze.length - 1);
        int x = rand.nextInt(maze[0].length - 1);
        System.out.println(x + " " + y);
        body = new LinkedList<>();
        body.add(new SnakePosition(y, x));
    }

    public void moveUp() {
        SnakePosition head = getHead();
        body.add(new SnakePosition(head.getCol(), head.getRow() - 1));
    }

    public void moveDown() {
        SnakePosition head = getHead();
        body.add(new SnakePosition(head.getCol(), head.getRow() + 1));
    }

    public void moveRight() {
        SnakePosition head = getHead();
        body.add(new SnakePosition(head.getCol() + 1, head.getRow()));
    }

    public void moveLeft() {
        SnakePosition head = getHead();
        body.add(new SnakePosition(head.getCol() - 1, head.getRow()));
    }

    public void eatUp() {
        SnakePosition head = getHead();
        body.add(new SnakePosition(head.getCol(), head.getRow() - 1));
    }

    /**
     * use HashSet for improve performance
     *
     * @return
     */
    public boolean contains(int col, int row) {
        return this.body.contains(new SnakePosition(col, row));
    }

    public LinkedList<SnakePosition> getBody() {
        return body;
    }

    public SnakePosition getHead() {
        return body.getLast();
    }

    public SnakePosition getTail() {
        return body.getFirst();
    }

    public void removeTail() {
        body.removeFirst();
    }

    public int length() {
        return body.size();
    }

    public void eat() {
        ate++;
    }

    public int getAte() {
        return ate;
    }

    @Override
    public void update(String eventType, Object data) {
        init(this.maze);
    }

    public static class SnakePosition {
        private int col;
        private int row;

        public void up() {
            if (row > 0) row--;
        }

        public void down() {
            row++;
        }

        public void right() {
            col++;
        }

        public void left() {
            if (col > 0) col--;
        }

        public SnakePosition(int col, int row) {
            this.col = col;
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public int getRow() {
            return row;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SnakePosition that = (SnakePosition) o;
            return col == that.col && row == that.row;
        }

        @Override
        public int hashCode() {
            return Objects.hash(col, row);
        }
    }
}
