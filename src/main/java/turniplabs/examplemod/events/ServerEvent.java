package turniplabs.examplemod.events;

public interface ServerEvent {
	void setCancelled(boolean b);
	boolean isCancelled();
}
