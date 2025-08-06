package net.yiran.morerequirement.requirements;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import se.mickelus.tetra.module.schematic.CraftingContext;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirement;

import javax.annotation.Nullable;
import java.util.List;

public class TimeRequirement implements CraftingRequirement {
    public String time;

    @Override
    public boolean test(CraftingContext cxt) {
        Level level = cxt.world;
        if (level == null) return false;
        var timeOfDay = level.dayTime()%24000;
        return switch (time){
            case "day" -> isDaytime(timeOfDay);
            case "night" -> !isDaytime(timeOfDay);
            default -> false;
        };
    }

    public static boolean isDaytime(long timeOfDay) {
        return timeOfDay >= 23950 || timeOfDay < 12750;
    }

    @Nullable
    @Override
    public List<Component> getDescription() {
        return List.of(Component.translatable("more_requirement.holo.time_requirement", Component.translatable("more_requirement.holo.time_requirement."+time)));
    }
}
