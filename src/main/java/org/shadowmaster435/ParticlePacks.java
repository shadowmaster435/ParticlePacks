package org.shadowmaster435;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ParticlePacks implements ModInitializer {

    public static final SimpleParticleType CUSTOM = FabricParticleTypes.simple();
    public static SpriteAtlasTexture provider;


    @Override
    public void onInitialize() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of("particlepacks", "custom"), CUSTOM);

    }
}
