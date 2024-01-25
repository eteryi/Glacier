package turniplabs.examplemod.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GlacierEvents {
	private static HashMap<Class, ArrayList<EventListener>> events = new HashMap<>();
	private static ArrayList<EventListener> getArrayListEventsFor(EventListener event) {
		if (events.get(event.getEvent()) == null) events.put(event.getEvent(), new ArrayList<>());
		return events.get(event.getEvent());
	}
	private static ArrayList<EventListener> getArrayListEventsFor(Class o) {
		if (events.get(o) == null) events.put(o, new ArrayList<>());
		return events.get(o);
	}
	public static void register(EventListener event) {
		ArrayList<EventListener> list = getArrayListEventsFor(event);
		list.add(event);
	}

	public static EventListener[] getEventsFor(Class o) {
		ArrayList<EventListener> list = getArrayListEventsFor(o);
		EventListener[] events = new EventListener[list.size()];
		events = list.toArray(events);
		return events;
	}

	public static void runEventsFor(Class o, ServerEvent object) {
		Arrays.stream(GlacierEvents.getEventsFor(o)).forEach(it -> {
			if (it.getEvent() == o) {
				it.run(object);
			}
		});
	}
}
