package org.shadowmaster435.util.custom_particle.spawn;

import com.google.gson.JsonObject;
import org.shadowmaster435.util.custom_particle.Vector3;
import org.shadowmaster435.util.custom_particle.misc.ParseHelper;

public record SpawnRangeParameters(Vector3 min_range, Vector3 max_range) {
    /*
     *   "range": {
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
     *   }
     * */


    public static SpawnRangeParameters parse(JsonObject object) {
        var min = Vector3.ZERO;
        var max = Vector3.ZERO;
        min = ParseHelper.parse_vec3(object.get("min").getAsJsonObject());
        max = ParseHelper.parse_vec3(object.get("max").getAsJsonObject());
        return new SpawnRangeParameters(min, max);
    }
}
