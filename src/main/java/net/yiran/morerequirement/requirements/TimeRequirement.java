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
        return switch (time){
            case "day" -> level.isDay();
            case "night" -> level.isNight();
            default -> false;
        };
    }

    @Nullable
    @Override
    public List<Component> getDescription() {
        return List.of(Component.translatable("more_requirement.holo.time_requirement", Component.translatable("more_requirement.holo.time_requirement."+time)));
    }
}
