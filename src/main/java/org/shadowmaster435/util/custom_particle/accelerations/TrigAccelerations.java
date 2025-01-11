package org.shadowmaster435.util.custom_particle.accelerations;

import com.google.gson.JsonObject;
import org.shadowmaster435.util.custom_particle.Vector3;
import org.shadowmaster435.util.custom_particle.misc.ParseHelper;

public record TrigAccelerations(Vector3 sine_speed, Vector3 sine_amplitude, Vector3 cosine_speed, Vector3 cosine_amplitude) {


    /* Example
    * "trig": {
    *   "sine": {
    *       "x": 0,
    *       "y": 0.5,
    *       "z": 1
    *   },
    *   "cosine": {
    *       "x": 0.1,
    *       "y": 0.25,
    *       "z": 0.321
    *   },
    * */
    public static TrigAccelerations parse(JsonObject object) {
        var sin = Vector3.ZERO;
        var sin_amplitude = Vector3.ZERO;
        
        var cos = Vector3.ZERO;
        var cos_amplitude = Vector3.ZERO;
        
        if (object.has("sine")) {
            var obj = object.get("sine").getAsJsonObject();

            if (obj.has("speed")) {
                sin = ParseHelper.parse_vec3(obj.get("speed").getAsJsonObject());
            }
            if (obj.has("amplitude")) {

                sin_amplitude = ParseHelper.parse_vec3(obj.get("amplitude").getAsJsonObject());
            }
        }
        if (object.has("cosine")) {
            var obj = object.get("cosine").getAsJsonObject();
            if (obj.has("speed")) {
                cos = ParseHelper.parse_vec3(obj.get("speed").getAsJsonObject());
            }
            if (obj.has("amplitude")) {
                cos_amplitude = ParseHelper.parse_vec3(obj.get("amplitude").getAsJsonObject());
            }

        }
        return new TrigAccelerations(sin, sin_amplitude, cos, cos_amplitude);
    }

}
