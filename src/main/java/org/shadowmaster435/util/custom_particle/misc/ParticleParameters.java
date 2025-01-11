package org.shadowmaster435.util.custom_particle.misc;

import com.google.gson.JsonObject;

public record ParticleParameters(boolean remove_on_ground, boolean collides_with_world, int max_age, float horizontal_size, float vertical_size, int amount) {

    /* Example For Section
    * ...
    * "parameters': {
    *   "collides_with_world": false,
    *   "remove_on_ground": true,
    *   "max_age": 100,
    *   "horizontal_size": 0.1,
    *   "vertical_size": 0.025
    * }
    * */
    public static ParticleParameters parse(JsonObject json) {
        var remove = true;
        var collide = true;
        var age = 1;
        var h_size = 0.05f;
        var v_size = 0.05f;
        var amount = 1;

        if (json.has("remove_on_ground")) {
            remove = json.get("remove_on_ground").getAsBoolean();
        }
        if (json.has("collides_with_world")) {
            collide = json.get("collides_with_world").getAsBoolean();
        }
        if (json.has("max_age")) {
            age = json.get("max_age").getAsInt();
        }
        if (json.has("horizontal_size")) {
            h_size = json.get("horizontal_size").getAsFloat();
        }
        if (json.has("vertical_size")) {
            v_size = json.get("vertical_size").getAsFloat();
        }
        if (json.has("amount")) {
            amount = json.get("amount").getAsInt();
        }
        return new ParticleParameters(remove, collide, age, h_size, v_size, amount);
    }

    public static ParticleParameters get_default_fallback() {
        return new ParticleParameters(true, true, 1, 0.05f, 0.05f, 1);
    }

}
