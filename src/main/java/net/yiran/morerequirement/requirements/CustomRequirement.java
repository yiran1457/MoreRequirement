package net.yiran.morerequirement.requirements;

import net.minecraft.network.chat.Component;
import se.mickelus.tetra.module.schematic.CraftingContext;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirement;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class CustomRequirement implements CraftingRequirement {
    public static Predicate<CraftingContext> consumer = cxt -> true;

    public static void setCustomFunction(Predicate<CraftingContext> cxt) {
        CustomRequirement.consumer = cxt;
    }

    @Override
    public boolean test(CraftingContext craftingContext) {
        return consumer.test(craftingContext);
    }

    @Nullable
    @Override
    public List<Component> getDescription() {
        return CraftingRequirement.super.getDescription();
    }
}
