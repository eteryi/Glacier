package cross.glacier.config;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class GlacierConfig {
	private final Gson gson;

	private final File file;
	private Config config;

	private boolean autoSave;
	private static class Config {
		public final HashMap<String, Object> values;

		public Config() {
			this.values = new HashMap<>();
		}
	}
	public GlacierConfig(File f) {
		this(f, false);
	}

	public GlacierConfig(File f, boolean autoSave) {
		this.gson = new Gson();
		this.autoSave = autoSave;
		this.file = f;
		load();
	}

	// Loads the specified file into the HashMap
	// If not found create JSONFile

	public String[] getKeys() {
		String[] arr = new String[config.values.keySet().size()];
		int i = 0;
		for (String key : config.values.keySet()) {
			arr[i] = key;
			i++;
		}
		return arr;
	}
	private void load() {
		if (!file.exists()) {
			create();
			return;
		}

        try (FileReader reader = new FileReader(file)) {
			this.config = gson.fromJson(reader, GlacierConfig.Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	private void create() {
        try {
            this.file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		this.config = new Config();
    }

	public Object get(String key, Class expected) {
		return gson.fromJson(gson.toJsonTree(config.values.get(key)).getAsJsonObject(), expected);
	}

	public Object getOrDefault(String key, Object o) {
		boolean shouldSave = !config.values.containsKey(key);
        config.values.putIfAbsent(key, o);
		if (shouldSave) autoSave();
		return config.values.get(key);
	}

	public void set(String key, Object o) {
		config.values.put(key, o);
		if (autoSave) autoSave();
	}

	public void autoSave() {
		Runnable r = this::save;
		Thread t = new Thread(r);
		t.start();
	}

	// Saves the entire HashMap of data to the file in JSON format.
	public void save() {
		try (FileWriter writer = new FileWriter(this.file)) {
			writer.write(gson.toJson(this.config));
			writer.flush();
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
