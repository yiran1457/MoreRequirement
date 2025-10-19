package net.yiran.morerequirement.requirements;

import net.minecraft.advancements.AdvancementList;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import se.mickelus.tetra.craftingeffect.condition.CraftingEffectCondition;
import se.mickelus.tetra.module.schematic.CraftingContext;
import se.mickelus.tetra.module.schematic.UpgradeSchematic;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirement;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class AdvancementRequirement implements CraftingRequirement, CraftingEffectCondition {
    public ResourceLocation advancement;

    @Override
    public boolean test(ResourceLocation[] unlocks, ItemStack upgradedStack, String slot, boolean isReplacing, Player player, ItemStack[] materials, Map<ToolAction, Integer> tools, UpgradeSchematic schematic, Level world, BlockPos pos, BlockState blockState) {
        if (player instanceof ServerPlayer serverPlayer) {
            return serverPlayer.getAdvancements().getOrStartProgress(serverPlayer.getServer().getAdvancements().getAdvancement(advancement)).isDone();
        } else {
            return Minecraft.getInstance().getConnection().getAdvancements().getAdvancements().get(advancement) != null;
        }
    }

    @Override
    public boolean test(CraftingContext cxt) {
        AdvancementList advancements = Minecraft.getInstance().getConnection().getAdvancements().getAdvancements();
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
