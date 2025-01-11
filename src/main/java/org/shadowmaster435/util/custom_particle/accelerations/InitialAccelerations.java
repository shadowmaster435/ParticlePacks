package org.shadowmaster435.util.custom_particle.accelerations;

import com.google.gson.JsonObject;
import org.shadowmaster435.util.custom_particle.Vector3;
import org.shadowmaster435.util.custom_particle.misc.ParseHelper;

public record InitialAccelerations(Vector3 cardinal_vel, float angular_vel) {

    /* Example
    * "spawn_acceleration": {
    *   "cardinal_velocity": {
    *       "x": -1,
    *       "y": 0.5,
    *       "z": 1
    *   },
    *   "angular_velocity": 1
    * }
    * */

    public static InitialAccelerations parse(JsonObject object) {
        var cardinal = Vector3.ZERO;
        var angular = 0f;
        if (object.has("cardinal_velocity")) {
            System.out.println( ParseHelper.parse_vec3(object.get("cardinal_velocity").getAsJsonObject()));

            cardinal = ParseHelper.parse_vec3(object.get("cardinal_velocity").getAsJsonObject());
        }
        if (object.has("angular_velocity")) {
            angular = object.get("angular_velocity").getAsFloat();
        }
        return new InitialAccelerations(cardinal, angular);
    }

}
