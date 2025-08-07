package niko.nikomod.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import niko.nikomod.NikoMod;
import niko.nikomod.screen.custom.SanvilScreenHandler;

public class ModScreenHandlers {
    public static final ScreenHandlerType<SanvilScreenHandler> SANVIL_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(NikoMod.MOD_ID, "sanvil_screen_handler"),
                    new ExtendedScreenHandlerType<>(
                            (syncId, inventory, pos) ->
                                    new SanvilScreenHandler(syncId, inventory, ScreenHandlerContext.create(inventory.player.getWorld(), pos)),
                            BlockPos.PACKET_CODEC
                    )
            );


    public static void registerScreenHandlers(){
        NikoMod.LOGGER.info("Registering Screen handlers for " + NikoMod.MOD_ID);
    }
}
