package bond.memo.practice.snake;

import bond.memo.practice.snake.event.EventListener;

import javax.swing.*;
import java.time.LocalTime;

class SetTimer implements Runnable, EventListener {

    private long start;
    private final JLabel timer;
    private volatile boolean running = true;

    public SetTimer(long start, JLabel timer) {
        this.start = start;
        this.timer = timer;
    }

    @Override
    public void run() {
        while(true) {
            if (running) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long now = System.currentTimeMillis();
                String s = LocalTime.MIN.plusSeconds((now - start) / 1000).toString().substring(3);
                timer.setText(s);
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
            this.start = System.currentTimeMillis();
            timer.setText("00:00");
        }
    }
}
