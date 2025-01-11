package org.shadowmaster435.util.custom_particle.accelerations;

import com.google.gson.JsonObject;
import org.shadowmaster435.util.custom_particle.Vector3;

public record Accelerations(ConstantAccelerations constant, AdditiveAccelerations additive, DampingAccelerations damping, TrigAccelerations trig, InitialRandomAccelerations random_accelerations) {

    /* Example
     * "accelerations": {
     *
         * "spawn_acceleration": {
         *   "cardinal_velocity": {
         *       "x": -1,
         *       "y": 0.5,
         *       "z": 1
         *   },
         *   "angular_velocity": 1
         * },
         * * "additive": {
         *   "cardinal_velocity": {
         *       "x": -1,
         *       "y": 0.5,
         *       "z": 1
         *   },
         *   "angular_velocity": 1
         * },
         * * "constant_acceleration": {
         *   "cardinal_velocity": {
         *       "x": -1,
         *       "y": 0.5,
         *       "z": 1
         *   },
         *   "angular_velocity": 1
         * },
         * * "damping_forces": {
         *   "cardinal_velocity": {
         *       "x": -1,
         *       "y": 0.5,
         *       "z": 1
         *   },
         *   "angular_velocity": 1
         * },
         * * "random_parameters: {
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
         * },
         *   * "trig": {
         *   "sine": {
         *       "x": 0,
         *       "y": 0.5,
         *       "z": 1
         *   },
         *   "cosine": {
         *       "x": 0.1,
         *       "y": 0.25,
         *       "z": 0.321
         *   }
         * }
     *   }
     * */




    public static Accelerations parse(JsonObject object) {
        var constant = new ConstantAccelerations(Vector3.ZERO, 0);
        var additive = new AdditiveAccelerations(Vector3.ZERO, 0);
        var damping = new DampingAccelerations(Vector3.ZERO, 1);
        var trig = new TrigAccelerations(Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO);
        var initial_random = new InitialRandomAccelerations(Vector3.ZERO, Vector3.ZERO, 0, 0);
        if (object.has("constant")) {
            var obj = object.get("constant").getAsJsonObject();
            constant = ConstantAccelerations.parse(obj);
        }
        if (object.has("additive")) {
            var obj = object.get("additive").getAsJsonObject();
            additive = AdditiveAccelerations.parse(obj);
        }
        if (object.has("trig")) {
            var obj = object.get("trig").getAsJsonObject();
            trig = TrigAccelerations.parse(obj);
        }
        if (object.has("damping_forces")) {
            var obj = object.get("damping_forces").getAsJsonObject();
            damping = DampingAccelerations.parse(obj);
        }
        if (object.has("initial_random")) {
            var obj = object.get("initial_random").getAsJsonObject();
            initial_random = InitialRandomAccelerations.parse(obj);
        }
        return new Accelerations(constant, additive, damping, trig, initial_random);
    }

    public static Accelerations get_default_fallback() {
        var constant = new ConstantAccelerations(Vector3.ZERO, 0);
        var additive = new AdditiveAccelerations(Vector3.ZERO, 0);
        var trig = new TrigAccelerations(Vector3.ZERO,Vector3.ZERO,Vector3.ZERO, Vector3.ZERO);
        var damping = new DampingAccelerations(Vector3.ONE, 1);

        var initial_random = new InitialRandomAccelerations(Vector3.ZERO, Vector3.ZERO, 0, 0);
        return new Accelerations(constant, additive, damping, trig, initial_random);
    }



}
