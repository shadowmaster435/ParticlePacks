package org.shadowmaster435.util.custom_particle;

import com.google.gson.JsonObject;
import org.shadowmaster435.util.custom_particle.misc.ParticleSpriteHandler;

import java.util.HashMap;

public class CustomParticleRegistry {


    public static HashMap<String, CustomParticleBehavior> behaviors = new HashMap<>();
    public static HashMap<String, ParticleSpriteHandler> sprites = new HashMap<>();

    public static void register(String file_name, JsonObject json) {
        var name_arr = file_name.replace(".json", "").split("/");
        var name = name_arr[name_arr.length - 1];
        var behavior = CustomParticleBehavior.parse(name, json);

        behaviors.put(name, behavior);
    }





}
