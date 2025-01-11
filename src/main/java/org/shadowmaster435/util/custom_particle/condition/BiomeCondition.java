package org.shadowmaster435.util.custom_particle.condition;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;

public record BiomeCondition(ArrayList<String> is, ArrayList<String> isnt, boolean used) {
    /*
    * Example
    * "biome": {
    *   "is": ["minecraft:desert", "somemod:somebiome"],
    *   "isnt": ["minecraft:jungle", "somemod:someotherbiome"]
    * }
    * */
    public boolean test(RegistryKey<Biome> biome) {
        if (!used) {
            return true;
        }
        var str = biome.getValue().toString();
        var matches_is = is.stream().toList().contains(str);

        var matches_isnt = isnt.stream().toList().contains(str);
        if (is.isEmpty()) {
            matches_isnt = true;
        }
        return matches_is && !matches_isnt;
    }


    public static BiomeCondition parse(JsonObject json) {
        BiomeCondition default_fallback = new BiomeCondition(new ArrayList<>(), new ArrayList<>(), false);

        ArrayList<String> is_result = new ArrayList<>();
        ArrayList<String> isnt_result = new ArrayList<>();
        var has_either = json.has("is") || json.has("isnt");
        if (!has_either) {
            return default_fallback;
        } else {
            if (json.has("is")) {
                for (JsonElement entry_element : json.get("is").getAsJsonArray()) {
                    is_result.add(entry_element.getAsString());
                }
            }
            if (json.has("isnt")) {
                for (JsonElement entry_element : json.get("isnt").getAsJsonArray()) {
                    isnt_result.add(entry_element.getAsString());
                }
            }
        }
        return new BiomeCondition(is_result, isnt_result, true);
    }
    public static BiomeCondition get_default_fallback() {
        return new BiomeCondition(new ArrayList<>(), new ArrayList<>(), false);
    }
}
