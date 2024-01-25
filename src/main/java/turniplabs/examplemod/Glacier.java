package turniplabs.examplemod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.examplemod.commands.GlacierCommands;
import turniplabs.examplemod.events.GlacierEvents;
import turniplabs.examplemod.test.ChestOpenListener;
import turniplabs.examplemod.test.PlayerChatListener;
import turniplabs.examplemod.test.PlayerLoginListener;
import turniplabs.examplemod.test.TeleportTesting;
import turniplabs.examplemod.utils.GlacierSettings;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class Glacier implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "examplemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        LOGGER.info("ExampleMod initialized.");
		GlacierSettings.setNicknames(false);
		GlacierEvents.register(new PlayerLoginListener());
		GlacierEvents.register(new PlayerChatListener());
		GlacierEvents.register(new ChestOpenListener());
		GlacierCommands.register("test", new TeleportTesting());
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
