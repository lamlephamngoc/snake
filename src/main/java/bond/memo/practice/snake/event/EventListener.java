package bond.memo.practice.snake.event;

public interface EventListener {
    String STOP = "stop";
    String EAT = "eat";

    String RESTART = "restart";
    void update(String eventType, Object data);
}
