package cross.glacier.events;

public interface ServerEvent {
	void setCancelled(boolean b);
	boolean isCancelled();
}
