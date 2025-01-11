package org.shadowmaster435.util.custom_particle;

import com.google.gson.JsonObject;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import org.shadowmaster435.ParticlePacks;
import org.shadowmaster435.util.custom_particle.accelerations.Accelerations;
import org.shadowmaster435.util.custom_particle.misc.ParticleParameters;
import org.shadowmaster435.util.custom_particle.misc.ParticleSpriteHandler;
import org.shadowmaster435.util.custom_particle.spawn.SpawnParameters;

import java.util.HashMap;

public record CustomParticleBehavior(String name, String type, Accelerations accelerations, SpawnParameters parameters, ParticleParameters particle_parameters) {


    public static CustomParticleBehavior parse(String name, JsonObject object) {
        var type = object.get("type").getAsString();
        var accelerations = Accelerations.get_default_fallback();
        var parameters = SpawnParameters.get_default_fallback();
        var particle_parameters = ParticleParameters.get_default_fallback();

        var sprite = object.get("sprite").getAsJsonObject();
        if (object.has("accelerations")) {
            accelerations = Accelerations.parse(object.get("accelerations").getAsJsonObject());
        }
        if (object.has("spawn_parameters")) {
            parameters = SpawnParameters.parse(object.get("spawn_parameters").getAsJsonObject());
        }
        if (object.has("particle_parameters")) {
            particle_parameters = ParticleParameters.parse(object.get("particle_parameters").getAsJsonObject());
        }
        var arr = sprite.get("frames").getAsJsonArray();
        HashMap<Integer, Sprite> frame_map = new HashMap<>();
        boolean is_random = false;
        if (sprite.has("random")) {
            is_random = sprite.get("random").getAsBoolean();
        }
        int frame_time = 1;
        if (sprite.has("frame_time")) {
            frame_time = Math.max(1, sprite.get("frame_time").getAsInt());
        }
        for (int i = 0; i < arr.size(); ++i) {
            var element = arr.get(i);
            var path = element.getAsString();
            frame_map.put(i, ParticlePacks.provider.getSprite(Identifier.of(path)));
        }
        CustomParticleRegistry.sprites.put(name, new ParticleSpriteHandler(frame_map, frame_time, is_random));

        return new CustomParticleBehavior(name, type, accelerations, parameters, particle_parameters);
    }

    /*Example Behavior
  {
  "accelerations": {
    "parameters": {
      "collides_with_world": false,
      "remove_on_ground": true,
      "max_age": 100,
      "horizontal_size": 0.1,
      "vertical_size": 0.025
    },
    "sprite": {
      "frame_time": 2,
      "frames": [
        "somepackname:a_particle_0",
        "somepackname:a_particle_1",
        "somepackname:a_particle_2"
      ],
      "random": true
    },
    "spawn_acceleration": {
      "cardinal_velocity": {
        "x": -1,
        "y": 0.5,
        "z": 1
      },
      "angular_velocity": 1
    },
    "additive": {
      "cardinal_velocity": {
        "x": -1,
        "y": 0.5,
        "z": 1
      },
      "angular_velocity": 1
    },
    "constant_acceleration": {
      "cardinal_velocity": {
        "x": -1,
        "y": 0.5,
        "z": 1
      },
      "angular_velocity": 1
    },
    "damping_forces": {
      "cardinal_velocity": {
        "x": -1,
        "y": 0.5,
        "z": 1
      },
      "angular_velocity": 1
    },
    "random_parameters": {
      "cardinal_velocity": {
        "min": {
          "x": -1,
          "y": 0.5,
          "z": 1
        },
        "max": {
          "x": 1,
          "y": 2.5,
          "z": 3
        }
      },
      "angular_velocity": {
        "min": -5,
        "max": 2
      }
    },
    "trig": {
      "sine": {
        "x": 0,
        "y": 0.5,
        "z": 1
      },
      "cosine": {
        "x": 0.1,
        "y": 0.25,
        "z": 0.321
      }
    }
  },
  "spawn_parameters": {
    "range": {
      "min": {
        "x": -1,
        "y": 0.5,
        "z": 1
      },
      "max": {
        "x": 1,
        "y": 2.5,
        "z": 3
      }
    },
    "spawn_conditions": {
      "is_air": true,
      "biome": {
        "is": [
          "minecraft:desert",
          "somemod:somebiome"
        ],
        "isnt": [
          "minecraft:jungle",
          "somemod:someotherbiome"
        ]
      }
    },
    "below": {
      "is": [
        "minecraft:sponge",
        "somemod:someblock"
      ],
      "isnt": [
        "minecraft:netherrack",
        "somemod:someotherblock"
      ]
    }
  },
  "animation": {
    "red": [
      [
        0,
        10,
        0,
        1
      ]
    ],
    "green": [
      [
        0,
        5,
        0,
        0.5
      ]
    ],
    "blue": [
      [
        0,
        15,
        1,
        0.75
      ]
    ],
    "alpha": [
      [
        0,
        20,
        0,
        1
      ]
    ],
    "scale": [
      [
        0,
        3,
        0,
        0.5
      ]
    ]
  }
}
    * */
}
