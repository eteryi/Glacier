package cross.glacier.events;

public interface EventListener<T extends ServerEvent> {
	void run(T event);
	Class<T> getEvent();
}
