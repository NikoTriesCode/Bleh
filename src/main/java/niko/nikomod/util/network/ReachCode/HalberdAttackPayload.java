package niko.nikomod.util.network.ReachCode;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import niko.nikomod.NikoMod;

public record HalberdAttackPayload(int targetId) implements CustomPayload {
    public static final Id<HalberdAttackPayload> ID =
            new Id<>(Identifier.of(NikoMod.MOD_ID, "halberd_attack"));

    public static final PacketCodec<RegistryByteBuf, HalberdAttackPayload> CODEC =
            PacketCodec.tuple(PacketCodecs.VAR_INT, HalberdAttackPayload::targetId, HalberdAttackPayload::new);

    @Override public Id<? extends CustomPayload> getId() { return ID; }
}
