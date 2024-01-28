package cross.glacier;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class Glacier implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "glacier";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
		LOGGER.info("Glacier was initialized");
//		GlacierSettings.setResetNicknames(true);
//		GlacierEvents.register(new PlayerLoginListener());
//		GlacierEvents.register(new PlayerChatListener());
//		GlacierEvents.register(new ChestOpenListener());
//		GlacierEvents.register(new PlayerHurtListener());
//		GlacierEvents.register(new BlockDigListener());
//		GlacierEvents.register(new PlayerLogoffListener());
//		GlacierCommands.register("test", new TeleportTesting());
//		GlacierCommands.register("window", new DimensionTesting());
//		GlacierCommands.register("config", new ConfigTest());
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}
}
