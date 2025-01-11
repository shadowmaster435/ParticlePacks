package org.shadowmaster435.util.custom_particle.accelerations;

import com.google.gson.JsonObject;
import org.shadowmaster435.util.custom_particle.Vector3;
import org.shadowmaster435.util.custom_particle.misc.ParseHelper;

public record DampingAccelerations(Vector3 cardinal_vel, float angular_vel) {

    /* Example
     * "damping_forces": {
     *   "cardinal_velocity": {
     *       "x": 0.125,
     *       "y": 0.9,
     *       "z": 1
     *   },
     *   "angular_velocity": 0.5
     * }
     * */

    public static DampingAccelerations parse(JsonObject object) {
        var cardinal = Vector3.ZERO;
        var angular = 0f;
        if (object.has("cardinal_velocity")) {
            cardinal = ParseHelper.parse_vec3(object.get("cardinal_velocity").getAsJsonObject());
        }
        if (object.has("angular_velocity")) {
            angular = object.get("angular_velocity").getAsFloat();
        }
        return new DampingAccelerations(cardinal, angular);
    }

}
