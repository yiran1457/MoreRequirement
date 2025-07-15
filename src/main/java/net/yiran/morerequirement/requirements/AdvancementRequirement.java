package net.yiran.morerequirement.requirements;

import net.minecraft.advancements.AdvancementList;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import se.mickelus.tetra.module.schematic.CraftingContext;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirement;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class AdvancementRequirement implements CraftingRequirement {
    public ResourceLocation advancement;

    @Override
    public boolean test(CraftingContext cxt) {
        AdvancementList advancements = Objects.requireNonNull(Minecraft.getInstance().getConnection()).getAdvancements().getAdvancements();
        return advancements.get(advancement) != null;
    }

    @Nullable
    @Override
    public List<Component> getDescription() {
        if (!advancement.getNamespace().equals("minecraft"))
            return List.of(Component.translatable("more_requirement.holo.advancement_requirement",
                    Component.translatable(advancement.getNamespace() + ".advancements." + advancement.getPath().replace("/", ".") + ".title")
            ));
        else
            return List.of(Component.translatable("more_requirement.holo.advancement_requirement",
                    Component.translatable("advancements." + advancement.getPath().replace("/", ".") + ".title")
            ));
    }
}
