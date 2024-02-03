package mrp_v2.concreteconversion;

import mrp_v2.concreteconversion.event.ConcreteEvents;
import mrp_v2.concreteconversion.server.Config;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.LevelResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConcreteConversion implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerWorldEvents.LOAD.register(this::worldLoadEvent);
        ServerTickEvents.START_WORLD_TICK.register((level) -> ConcreteEvents.SERVER_LEVEL_TICK.post().handle(level));
        ConcreteConversionCommon.init();
    }

    public void worldLoadEvent(MinecraftServer server, ServerLevel level) {
        Path worldSave = Path.of(server.getWorldPath(LevelResource.ROOT).toString(), "serverconfig");
        try {
            Files.createDirectories(worldSave);
        } catch (IOException e) {}
        Config.init(new File(worldSave.toFile(), ConcreteConversionCommon.ID + "-server.json"));
        ConcreteEvents.SERVER_LEVEL_TICK.post().handle(level);
    }
}
