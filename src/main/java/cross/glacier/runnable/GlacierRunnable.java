package cross.glacier.runnable;

import java.util.HashMap;
import java.util.UUID;

public class GlacierRunnable {
	private static final HashMap<UUID, GlacierRunnable> runnables = new HashMap<>();
	private final UUID id;
	private final GlacierTask runnable;

	private Thread thread;

	public static void stopTask(UUID uuid) {
		if (runnables.get(uuid) != null) runnables.get(uuid).stop();
	}

	public static void forceStopAll() {
		for (GlacierRunnable i : runnables.values()) {
			i.stop();
		}
	}

	public GlacierRunnable(GlacierTask r) {
		this.runnable = r;
		UUID id = UUID.randomUUID();

		while (runnables.containsKey(id)) {
			id = UUID.randomUUID();
		}
		this.id = id;
		runnables.put(id, this);
	}

	public UUID getID() {
		return this.id;
	}

	public void runLater(long ticksLater) {
		if (this.thread != null) {
			System.out.println("Tried running more times the same runnable");
			return;
		}
		long[] arr = new long[]{ticksLater, System.currentTimeMillis()};
		this.thread = new Thread(() -> {
			while (true) {
				if (arr[1] + (arr[0] * 50) <= System.currentTimeMillis()) {
					this.runnable.run(this);
					break;
				}
			}
		});
		thread.start();
	}

	public void runTimer(long ticksPerCycle) {
		if (this.thread != null) {
			System.out.println("Tried running more times the same runnable");
			return;
		}
		long[] arr = new long[]{ticksPerCycle, System.currentTimeMillis()};
		this.thread = new Thread(() -> {
			while (true) {
				if (arr[1] + (arr[0] * 50) <= System.currentTimeMillis()) {
					this.runnable.run(this);
					arr[1] = System.currentTimeMillis();
				}
			}
		});
		thread.start();
	}

	public void stop() {
		if (this.thread == null) {
			System.out.println("Runnable hasn't started");
			return;
		}
		this.thread.stop();
	}
}
