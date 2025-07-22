package net.yiran.morerequirement.requirements;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import se.mickelus.tetra.module.schematic.CraftingContext;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirement;

import javax.annotation.Nullable;
import java.util.List;

public class DimensionRequirement implements CraftingRequirement {
    public ResourceLocation dimension;
    @Override
    public boolean test(CraftingContext cxt) {
        Level level = cxt.world;
        if(level == null) return false;
        return level.dimension().location().equals(dimension);
    }

    @Nullable
    @Override
    public List<Component> getDescription() {
        return List.of(Component.translatable("more_requirement.holo.dimension_requirement", Component.translatable(dimension.toString())));
    }
}
