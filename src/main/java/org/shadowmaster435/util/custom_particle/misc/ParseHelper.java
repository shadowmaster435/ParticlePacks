package org.shadowmaster435.util.custom_particle.misc;

import com.google.gson.JsonObject;
import org.shadowmaster435.util.custom_particle.Vector3;

public class ParseHelper {


    public static Vector3 parse_vec3(JsonObject object) {
        var result = Vector3.ZERO;
        var is_all = object.has("all");
        var has_x = object.has("x");
        var has_y = object.has("y");
        var has_z = object.has("z");
        if (is_all) {
            return new Vector3(object.get("all").getAsFloat());
        } else {
            if (has_x) {
                result = result.with_x(object.get("x").getAsFloat());
            }
            if (has_y) {
                result = result.with_y(object.get("y").getAsFloat());
            }
            if (has_z) {
                result = result.with_z(object.get("z").getAsFloat());
            }
        }
        return result;
    }

}
