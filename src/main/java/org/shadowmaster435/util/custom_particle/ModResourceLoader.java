package org.shadowmaster435.util.custom_particle;

import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.InputStream;

public class ModResourceLoader {

    public static void register() {

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return Identifier.of("particlepacks", "particle_pack");
            }

            @Override
            public void reload(ResourceManager manager) {
                CustomParticleRegistry.behaviors.clear();
                CustomParticleRegistry.sprites.clear();
                for(Identifier id : manager.findResources("particles/custom", path -> path.getPath().endsWith(".json")).keySet().stream().toList()) {
                    try(InputStream stream = manager.getResource(id).orElseThrow().getInputStream()) {
                        var gson = JsonParser.parseString(new String(stream.readAllBytes())).getAsJsonObject();


                        CustomParticleRegistry.register(id.getPath(), gson);
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
    }

}
