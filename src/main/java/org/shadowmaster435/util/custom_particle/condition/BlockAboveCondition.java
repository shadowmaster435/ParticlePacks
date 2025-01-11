package org.shadowmaster435.util.custom_particle.condition;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public record BlockAboveCondition(ArrayList<Block> is, ArrayList<Block> isnt, boolean used) {

    /* Example
    * "below": {
    *   "is": ["minecraft:sponge", "somemod:someblock"],
    *   "isnt": ["minecraft:netherrack", "somemod:someotherblock"]
    * }
    * */

    public boolean test(World world, BlockPos pos) {
        if (!used) {
            return true;
        }
        var block = world.getBlockState(pos.up()).getBlock();
        var matches_is = is.stream().toList().contains(block);

        if (is.isEmpty()) {
            matches_is = true;
        }
        var matches_isnt = isnt.stream().toList().contains(block);

        return matches_is && !matches_isnt;
    }

    public static BlockAboveCondition parse(JsonObject json) {
        BlockAboveCondition default_fallback = new BlockAboveCondition(new ArrayList<>(), new ArrayList<>(), false);
        ArrayList<Block> is_result = new ArrayList<>();
        ArrayList<Block> isnt_result = new ArrayList<>();
        var has_either = json.has("is") || json.has("isnt");
        if (!has_either) {
            return default_fallback;
        } else {
            if (json.has("is")) {
                for (JsonElement entry_element : json.get("is").getAsJsonArray()) {
                    var entry = entry_element.getAsString();
                    var block = Registries.BLOCK.get(Identifier.of(entry));
                    is_result.add(block);
                }
            }
            if (json.has("isnt")) {
                for (JsonElement entry_element : json.get("isnt").getAsJsonArray()) {
                    var entry = entry_element.getAsString();
                    var block = Registries.BLOCK.get(Identifier.of(entry));
                    isnt_result.add(block);
                }
            }
        }
        return new BlockAboveCondition(is_result, isnt_result, true);
    }

    public static BlockAboveCondition get_default_fallback() {
        return new BlockAboveCondition(new ArrayList<>(), new ArrayList<>(), false);
    }

}
