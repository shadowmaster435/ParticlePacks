package org.shadowmaster435.util.custom_particle.misc;

import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.shadowmaster435.util.custom_particle.CustomParticle;
import org.shadowmaster435.util.custom_particle.Timer;

import java.util.HashMap;

public class ParticleSpriteHandler {
    public final HashMap<Integer, Sprite> frames;
    public final int frame_time;
    public final Timer frame_timer;
    public final boolean is_random;

    public ParticleSpriteHandler(HashMap<Integer, Sprite> frames, int frame_time, boolean is_random) {
        this.frames = frames;
        this.frame_time = frame_time;
        frame_timer = new Timer();
        frame_timer.wait_ticks = frame_time;
        frame_timer.repeating = true;
        this.is_random = is_random;
    }


    public void update(CustomParticle particle) {
        if (frame_timer.timeout()) {
            if (is_random) {
                particle.frame = MathHelper.nextBetween(Random.createLocal(), 0, frames.size() - 1);
            } else {
                particle.frame = (particle.frame + 1) % frames.size();
            }
        }
        frame_timer.update();
    }

    public ParticleSpriteHandler duplicate() {
        return new ParticleSpriteHandler(frames, frame_time, is_random);
    }
    public Sprite get_frame(int frame) {
        return frames.get(frame);
    }
}
