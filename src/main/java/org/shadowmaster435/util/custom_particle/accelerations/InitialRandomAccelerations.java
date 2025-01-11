package org.shadowmaster435.util.custom_particle.accelerations;

import com.google.gson.JsonObject;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.shadowmaster435.util.custom_particle.Vector3;
import org.shadowmaster435.util.custom_particle.misc.ParseHelper;

public record InitialRandomAccelerations(Vector3 cardinal_vel_min, Vector3 cardinal_vel_max, float angular_vel_min, float angular_vel_max) {

    /* Example
    * "random_parameters: {
    *   "cardinal_velocity": {
    *       "min": {
    *           "x": -1,
    *           "y": 0.5,
    *           "z": 1
    *       },
    *       "max": {
    *           "x": 1,
    *           "y": 2.5,
    *           "z": 3
    *       }
    *   },
    *   "angular_velocity" {
    *       "min": -5,
    *       "max": 2
    *   }
    * }
    *
    * */

    public static InitialRandomAccelerations parse(JsonObject object) {
        var cardinal_min = Vector3.ZERO;
        var cardinal_max = Vector3.ZERO;
        var angular_min = 0f;
        var angular_max = 0f;
        if (object.has("cardinal_velocity")) {
            var obj = object.get("cardinal_velocity").getAsJsonObject();
            cardinal_min = ParseHelper.parse_vec3(obj.get("min").getAsJsonObject());
            cardinal_max = ParseHelper.parse_vec3(obj.get("max").getAsJsonObject());
        }
        if (object.has("angular_velocity")) {
            var obj = object.get("angular_velocity").getAsJsonObject();
            angular_min = obj.get("min").getAsFloat();
            angular_max = obj.get("max").getAsFloat();
        }
        return new InitialRandomAccelerations(cardinal_min, cardinal_max, angular_min, angular_max);
    }
    public Vector3 sample_cardinal() {
        return Vector3.rand_between(cardinal_vel_min, cardinal_vel_max);
    }

    public float sample_angular() {
        return MathHelper.nextBetween(Random.createLocal(), angular_vel_min, angular_vel_max);
    }

}
