package org.shadowmaster435.util.custom_particle.accelerations;

import com.google.gson.JsonObject;
import org.shadowmaster435.util.custom_particle.Vector3;
import org.shadowmaster435.util.custom_particle.misc.ParseHelper;

public record AdditiveAccelerations(Vector3 cardinal, float angular) {

    /* Example
     * "additive_acceleration": {
     *   "cardinal_velocity": {
     *       "x": -1,
     *       "y": 0.5,
     *       "z": 1
     *   },
     *   "angular_velocity": 1
     * }
     * */
    public static AdditiveAccelerations parse(JsonObject object) {
        var cardinal = Vector3.ZERO;
        var angular = 0f;
        if (object.has("cardinal_velocity")) {
            cardinal = ParseHelper.parse_vec3(object.get("cardinal_velocity").getAsJsonObject());
        }
        if (object.has("angular_velocity")) {
            angular = object.get("angular_velocity").getAsFloat();
        }
        return new AdditiveAccelerations(cardinal, angular);
    }

}
