package bond.memo.practice.snake;

import bond.memo.practice.snake.event.EventListener;

import javax.swing.*;

public class SetAte implements EventListener {
    private final JLabel ate;

    public SetAte(JLabel ate) {
        this.ate = ate;
    }

    @Override
    public void update(String eventType, Object data) {
        if (eventType.equals(EventListener.EAT)) {
            ate.setText(data.toString());
        } else if (eventType.equals(EventListener.RESTART)) {
            ate.setText("0");
        }
    }
}
