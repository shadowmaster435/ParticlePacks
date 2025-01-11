package org.shadowmaster435.util.custom_particle.spawn;

import com.google.gson.JsonObject;
import org.shadowmaster435.util.custom_particle.Vector3;
import org.shadowmaster435.util.custom_particle.condition.BiomeCondition;
import org.shadowmaster435.util.custom_particle.condition.BlockAboveCondition;

public record SpawnParameters(SpawnConditions conditions, SpawnRangeParameters range_parameters) {
    /* Example
     * "spawn_parameters": {
         * *   "range": {
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
         * "spawn_conditions": {
         * "is_air": true,
         * * "biome": {
         *   "is": ["minecraft:desert", "somemod:somebiome"],
         *   "isnt": ["minecraft:jungle", "somemod:someotherbiome"]
         * }
         * },
         * *   "range": {
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
     * }
     * */

    public static SpawnParameters parse(JsonObject object) {
        var conditions = new SpawnConditions(false, BlockAboveCondition.get_default_fallback(), BiomeCondition.get_default_fallback(), false, false);
        var range_parameters = new SpawnRangeParameters(Vector3.ZERO, Vector3.ZERO);
        if (object.has("spawn_conditions")) {
            conditions = SpawnConditions.parse(object.get("spawn_conditions").getAsJsonObject());
        }
        if (object.has("spawn_range")) {
            range_parameters = SpawnRangeParameters.parse(object.get("spawn_range").getAsJsonObject());
        }
        return new SpawnParameters(conditions, range_parameters);
    }

    public static SpawnParameters get_default_fallback() {
        var conditions = new SpawnConditions(false, BlockAboveCondition.get_default_fallback(), BiomeCondition.get_default_fallback(), false, false);
        var range_parameters = new SpawnRangeParameters(Vector3.ZERO, Vector3.ONE);
        return new SpawnParameters(conditions, range_parameters);
    }

}
