package cross.glacier;

import cross.glacier.test.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cross.glacier.commands.GlacierCommands;
import cross.glacier.events.GlacierEvents;
import cross.glacier.utils.GlacierSettings;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class Glacier implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "glacier";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        LOGGER.info("ExampleMod initialized.");
		GlacierSettings.setNicknames(false);
		GlacierEvents.register(new PlayerLoginListener());
		GlacierEvents.register(new PlayerChatListener());
		GlacierEvents.register(new ChestOpenListener());
		GlacierEvents.register(new PlayerHurtListener());
		GlacierEvents.register(new BlockDigListener());
		GlacierEvents.register(new CompassInteract());
		GlacierEvents.register(new PlayerLogoffListener());
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
