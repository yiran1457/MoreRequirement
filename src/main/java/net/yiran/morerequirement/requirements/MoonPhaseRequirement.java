package net.yiran.morerequirement.requirements;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import se.mickelus.tetra.module.schematic.CraftingContext;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirement;

import javax.annotation.Nullable;
import java.util.List;

public class MoonPhaseRequirement implements CraftingRequirement {
    public int moonPhase;

    @Override
    public boolean test(CraftingContext cxt) {
        Level level = cxt.world;
        if (level == null) return false;
        return level.getMoonPhase()+1 == moonPhase;
    }

    @Nullable
    @Override
    public List<Component> getDescription() {
        return List.of(Component.translatable("more_requirement.holo.moon_phase_requirement", Component.translatable("more_requirement.holo.moon_phase_requirement." +moonPhase)));
    }
}
