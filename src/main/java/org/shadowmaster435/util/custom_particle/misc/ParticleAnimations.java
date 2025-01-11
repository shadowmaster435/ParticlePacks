package org.shadowmaster435.util.custom_particle.misc;

import com.google.gson.JsonObject;
import org.shadowmaster435.util.custom_particle.BindableTimerQueue;

import java.util.HashMap;

public record ParticleAnimations(BindableTimerQueue red, BindableTimerQueue green, BindableTimerQueue blue, BindableTimerQueue alpha, BindableTimerQueue scale) {


    /* Example
    * ...
    *"animation": {
    *   "red": [[0, 10, 0, 1]],
    *   "green": [[0, 5, 0, 0.5]],
    *   "blue": [[0, 15, 1, 0.75]]
    *   "alpha": [[0, 20, 0, 1]]
    *   "scale": [[0, 3, 0, 0.5]]
    * }
    * ...
    *  */

    public static ParticleAnimations parse(JsonObject object) {
        var red = new BindableTimerQueue(new HashMap<>());
        var green = new BindableTimerQueue(new HashMap<>());
        var blue = new BindableTimerQueue(new HashMap<>());
        var alpha = new BindableTimerQueue(new HashMap<>());
        var scale = new BindableTimerQueue(new HashMap<>());

        if (object.has("red")) {
            var obj = object.get("red").getAsJsonArray();
            red = BindableTimerQueue.from_json(obj, "red");
        }
        if (object.has("green")) {
            var obj = object.get("green").getAsJsonArray();
            green = BindableTimerQueue.from_json(obj, "green");
        }
        if (object.has("blue")) {
            var obj = object.get("blue").getAsJsonArray();
            blue = BindableTimerQueue.from_json(obj, "blue");
        }
        if (object.has("alpha")) {
            var obj = object.get("alpha").getAsJsonArray();
            alpha = BindableTimerQueue.from_json(obj, "alpha");
        }
        if (object.has("scale")) {
            var obj = object.get("scale").getAsJsonArray();
            scale = BindableTimerQueue.from_json(obj, "scale");
        }
        return new ParticleAnimations(red, green, blue, alpha, scale);
    }

}
