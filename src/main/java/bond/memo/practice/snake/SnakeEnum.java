package bond.memo.practice.snake;

import java.awt.*;

public enum SnakeEnum {
    SNAKE(Color.RED),
    SNAKE_HEAD(Color.MAGENTA),
    BACKGROUND(Color.LIGHT_GRAY),
    FOOD(Color.GREEN),
    POISON(Color.BLACK);

    private Color color;

    SnakeEnum(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
