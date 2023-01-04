package bond.memo.practice.snake.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    private Map<String, List<EventListener>> listeners = new HashMap<>();

    public EventManager(String... operation) {
        for (String s : operation) {
            this.listeners.put(s, new ArrayList<>());
        }
    }

    public void subscribe(String eventType, EventListener eventListener) {
        this.listeners.get(eventType).add(eventListener);
    }

    public void unsubscribe(String eventType, EventListener eventListener) {
        this.listeners.get(eventType).remove(eventListener);
    }

    public void notify(String eventType, Object data) {
        List<EventListener> eventListeners = this.listeners.get(eventType);
        for (EventListener e : eventListeners) {
            e.update(eventType, data);
        }
    }
}
