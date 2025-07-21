package net.yiran.morerequirement.requirements;

import net.minecraft.network.chat.Component;
import se.mickelus.tetra.module.schematic.CraftingContext;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirement;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomRequirement implements CraftingRequirement {
    public static CustomFunction DEFAULT = cxt -> true;
    public static Map<String,CustomFunction> functions = new HashMap<>();
    public String key;

    public static void registerCustomFunction(String key,CustomFunction cxt) {
        functions.put(key,cxt);
    }

    @Override
    public boolean test(CraftingContext craftingContext) {
        return functions.getOrDefault(key,DEFAULT).test(craftingContext);
    }

    @Nullable
    @Override
    public List<Component> getDescription() {
        return List.of(Component.translatable("more_requirement.holo.custom_requirement"+key));
    }

    @FunctionalInterface
    public interface CustomFunction{
        boolean test(CraftingContext craftingContext);
    }
}
