package org.shadowmaster435.util.custom_particle;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class ParticleUtil {


    public static BlockState get_blockstate_at(BlockPos pos) {
        var world = get_world();
        return world.getBlockState(pos);
    }


    public static boolean can_rain() {
        var player = MinecraftClient.getInstance().player;
        return MinecraftClient.getInstance().world.getBiome(player.getBlockPos()).value().getPrecipitation(player.getBlockPos(), get_world().getSeaLevel()) == Biome.Precipitation.RAIN;
    }

    public static boolean can_rain(Vector3 pos) {
        return MinecraftClient.getInstance().world.getBiome(pos.to_blockpos()).value().getPrecipitation(pos.to_blockpos(), get_world().getSeaLevel()) == Biome.Precipitation.RAIN;
    }
    public static RegistryEntry<Biome> get_biome_at(BlockPos pos) {
        return get_world().getBiome(pos);
    }


    public static boolean is_biome_at(BlockPos pos, RegistryKey<Biome> biome) {
        return get_biome_at(pos).matchesKey(biome);
    }

    public static Vector3 get_player_pos_plus_vel(float multipier) {
        var player = get_player();
        var yvel = player.getVelocity().y;
        if (player.isOnGround()) {
            yvel = 0;
        }
        var result = new Vector3(player.getVelocity().x, yvel, player.getVelocity().z).multiply(multipier);
        return new Vector3(player.getPos().add(result));
    }

    public static Vector3 get_player_pos_plus_vel() {
        var player = get_player();
        var yvel = player.getVelocity().y;
        if (player.isOnGround()) {
            yvel = 0;
        }
        var result = new Vector3(player.getVelocity().x, yvel, player.getVelocity().z);
        return new Vector3(player.getPos().add(result));
    }
    public static ClientPlayerEntity get_player() {
        return MinecraftClient.getInstance().player;
    }
    public static ClientWorld get_world() {
        return MinecraftClient.getInstance().world;
    }
}
