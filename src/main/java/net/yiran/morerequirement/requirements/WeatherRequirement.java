package net.yiran.morerequirement.requirements;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import se.mickelus.tetra.module.schematic.CraftingContext;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirement;

import javax.annotation.Nullable;
import java.util.List;

public class WeatherRequirement implements CraftingRequirement {
    public String weather;
    @Override
    public boolean test(CraftingContext cxt) {
        Level level = cxt.world;
        if(level == null) return false;
        return switch (weather) {
            case "clear" -> !level.isRaining() && !level.isThundering();
            case "rain" -> level.isRaining();
            case "thunder" -> level.isThundering();
            default -> false;
        };
    }

    @Nullable
    @Override
    public List<Component> getDescription() {
        return List.of(Component.translatable("more_requirement.holo.weather_requirement", Component.translatable("more_requirement.holo.weather_requirement."+weather)));
    }
}
