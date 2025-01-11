package org.shadowmaster435.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import org.shadowmaster435.ParticlePacks;
import org.shadowmaster435.util.custom_particle.CustomParticle;
import org.shadowmaster435.util.custom_particle.ModResourceLoader;

public class ParticlePacksClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ParticlePacks.CUSTOM, CustomParticle.Factory::new);
        ModResourceLoader.register();
    }
}
