package org.shadowmaster435.util.custom_particle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.HashMap;

public class BindableTimerQueue {

    public Timer full_length_timer;
    public HashMap<Integer, BindableTimer> timers = new HashMap<>();

    public BindableTimerQueue(HashMap<Integer, BindableTimer> timers) {
        this.timers = timers;
    }


    public static BindableTimerQueue from_json(JsonArray array, String field_name) {
        HashMap<Integer, BindableTimer> timers = new HashMap<>();
        var max_time = -1;
        var full_length_timer = new Timer();
        for (JsonElement element : array) {
            // example [
                // ...
                // [start_time, length, min, max]
                // [start_time, length, min, max]
                // ...
            // ]
            var start_time = element.getAsJsonArray().get(0).getAsInt();
            var timer_length = element.getAsJsonArray().get(1).getAsInt();
            var min_value = element.getAsJsonArray().get(2).getAsInt();
            var max_value = element.getAsJsonArray().get(3).getAsInt();
            max_time = Math.max(max_time, timer_length + start_time);
            var timer = new BindableTimer(field_name, true, min_value, max_value);
            timer.wait_ticks = timer_length;
            timers.put(start_time, timer);

        }
        full_length_timer.wait_ticks = max_time;
        var queue = new BindableTimerQueue(timers);
        queue.full_length_timer = full_length_timer;
        return queue;
    }

    public void update(Object instance) {
        for (int start_time : timers.keySet()) {
            var timer = timers.get(start_time);
            timer.update(instance);
            if (full_length_timer.wait_ticks == start_time) {
                timer.start();
            }
        }
    }

}
