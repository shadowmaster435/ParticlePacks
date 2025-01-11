package org.shadowmaster435.util.custom_particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;
import org.shadowmaster435.util.custom_particle.misc.ParticleSpriteHandler;

public class CustomParticle extends ParticleBase {




    public int frame = 0;
    private int last_frame = 0;
    public String particle_name = "missing";

    public CustomParticleBehavior behavior;

    public Vector3 random_cardinal_velocity = Vector3.ZERO;
    public float random_angular_velocity = 0;

    public ParticleSpriteHandler sprite_handler;
    public CustomParticle(ClientWorld world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        maxAge = 10000;
    }

    public void init() {
        this.behavior = CustomParticleRegistry.behaviors.getOrDefault(particle_name, null);
        this.random_cardinal_velocity = behavior.accelerations().random_accelerations().sample_cardinal();
        this.angular_velocity += behavior.accelerations().constant().angular();
        this.random_angular_velocity += behavior.accelerations().random_accelerations().sample_angular();
        collidesWithWorld = behavior.particle_parameters().collides_with_world();
        maxAge = behavior.particle_parameters().max_age();
        setBoundingBoxSpacing(behavior.particle_parameters().horizontal_size(), behavior.particle_parameters().vertical_size());
        sprite_handler = CustomParticleRegistry.sprites.get(particle_name).duplicate();
        scale = 0.125f;
        setSprite(sprite_handler.get_frame(0));
        alpha = 1.0f;
    }

    @Override
    public void tick() {
        super.tick();
        try {
            var constant_cardinal = behavior.accelerations().constant().cardinal();
            var trig_cardinal = new Vector3(get_sine().add(get_cosine()));
            var additive_cardinal = behavior.accelerations().additive().cardinal().mul(age);
            var damping_cardinal = behavior.accelerations().damping().cardinal_vel();
            var constant_angular = behavior.accelerations().constant().angular();
            var additive_angular = behavior.accelerations().additive().angular() * age;
            var damping_angular = behavior.accelerations().damping().angular_vel();
            var damped_cardinal = damping_cardinal.div(age + 1);
            var damped_angular = (damping_angular / (age + 1));
            if (damping_angular == 0) {
                damped_angular = 1;
            }
            if (damping_cardinal.x == 0) {
                damped_cardinal = damped_cardinal.with_x(1);
            }
            if (damping_cardinal.y == 0) {
                damped_cardinal = damped_cardinal.with_y(1);
            }
            if (damping_cardinal.z == 0) {
                damped_cardinal = damped_cardinal.with_z(1);
            }
            set_velocity(new Vector3(new Vector3(constant_cardinal.add(random_cardinal_velocity).add(trig_cardinal).add(additive_cardinal)).multiply(damped_cardinal)));
            angular_velocity = (constant_angular + random_angular_velocity + additive_angular) * damped_angular;
            sprite_handler.update(this);
            if (last_frame != frame) {
                setSprite(sprite_handler.get_frame(frame));
                last_frame = frame;

            }
            if ((behavior.particle_parameters().remove_on_ground() && onGround) || age >= maxAge) {
                markDead();
            }

        } catch (Exception ignored) {
            markDead();
            System.out.println("null particle");
        }
    }

    public Vector3 get_sine() {
        var amp = behavior.accelerations().trig().sine_amplitude();
        var spd = behavior.accelerations().trig().sine_speed();
        var x = (Math.sin(age * (spd.x)) * (amp.x * spd.x));
        var y = (Math.sin(age * (spd.y)) * (amp.y * spd.y));
        var z = (Math.sin(age * (spd.z)) * (amp.z * spd.z));
        if (Double.isNaN(x)) {
            x = 0;
        }
        if (Double.isNaN(y)) {
            y = 0;
        }
        if (Double.isNaN(z)) {
            z = 0;
        }
        return new Vector3(x, y, z);
    }

    public Vector3 get_cosine() {
        var amp = behavior.accelerations().trig().cosine_amplitude();
        var spd = behavior.accelerations().trig().cosine_speed();
        var x = (Math.cos(age * (spd.x)) * (amp.x * spd.x));
        var y = (Math.cos(age * (spd.y)) * (amp.y * spd.y));
        var z = (Math.cos(age * (spd.z)) * (amp.z * spd.z));
        if (Double.isNaN(x)) {
            x = 0;
        }
        if (Double.isNaN(y)) {
            y = 0;
        }
        if (Double.isNaN(z)) {
            z = 0;
        }
        return new Vector3(x, y, z);
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final FabricSpriteProvider spriteProvider;
        public Factory(FabricSpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            CustomParticle particle = new CustomParticle(world, new Vector3(x, y, z), spriteProvider);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }

}
