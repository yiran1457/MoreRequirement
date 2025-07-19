package net.yiran.morerequirement.requirements;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import se.mickelus.tetra.module.schematic.CraftingContext;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirement;

import javax.annotation.Nullable;
import java.util.List;

public class HeightRequirement implements CraftingRequirement {
    public int min = -99;
    public int max = 333;
    public ResourceLocation dimension;

    @Override
    public boolean test(CraftingContext cxt) {
        BlockPos pos = cxt.pos;
        if (pos == null) return false;
        return pos.getY() >= min && pos.getY() <= max;
    }

    @Nullable
    @Override
    public List<Component> getDescription() {
        return List.of(Component.translatable("more_requirement.holo.height_requirement", min, max));
    }
}
