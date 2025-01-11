package org.shadowmaster435.util.custom_particle.spawn;

import com.google.gson.JsonObject;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.shadowmaster435.util.custom_particle.condition.BiomeCondition;
import org.shadowmaster435.util.custom_particle.condition.BlockAboveCondition;

import java.util.ArrayList;

public record SpawnConditions(boolean is_solid, BlockAboveCondition above_condition, BiomeCondition biome_condition, boolean is_raining, boolean is_thundering) {



    /* Example
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
     * */

    public boolean test(World world, BlockPos pos) {
        var biome = biome_condition.test(world.getBiome(pos).getKey().orElseThrow());
        var solid = !is_solid || !world.getBlockState(pos).isSolidBlock(world, pos);
        var above = above_condition.test(world, pos);
        var rain = !is_raining || world.isRaining();
        var thunder = !is_thundering || world.isThundering();

        return biome && solid && above && rain && thunder;
    }

    public static SpawnConditions parse(JsonObject json) {
        var solid = true;
        var rain = false;
        var thunder = false;

        if (json.has("is_solid")) {
            solid = json.get("is_solid").getAsBoolean();
        }
        if (json.has("is_raining")) {
            rain = json.get("is_raining").getAsBoolean();
        }
        if (json.has("is_thundering")) {
            thunder = json.get("is_thundering").getAsBoolean();
        }
        BlockAboveCondition above = new BlockAboveCondition(new ArrayList<>(), new ArrayList<>(), false);
        if (json.has("below")) {
            above = BlockAboveCondition.parse(json.get("below").getAsJsonObject());
        }
        BiomeCondition biome = new BiomeCondition(new ArrayList<>(), new ArrayList<>(), false);
        if (json.has("biome")) {
            biome = BiomeCondition.parse(json.get("biome").getAsJsonObject());
        }
        return new SpawnConditions(solid, above, biome, rain, thunder);
    }





}
