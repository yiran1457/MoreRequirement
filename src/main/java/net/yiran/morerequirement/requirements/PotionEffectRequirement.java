package net.yiran.morerequirement.requirements;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import se.mickelus.tetra.module.schematic.CraftingContext;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirement;

import javax.annotation.Nullable;
import java.util.List;

public class PotionEffectRequirement implements CraftingRequirement {
    public MobEffect effect;
    public int duration=0;
    public int amplifier=0;

    @Override
    public boolean test(CraftingContext cxt) {
        Player player = cxt.player;
        if (player == null) return false;
        MobEffectInstance mobEffectInstance = player.getEffect(effect);
        return mobEffectInstance != null && mobEffectInstance.getAmplifier() >= amplifier && mobEffectInstance.getDuration() >= duration;
    }

    @Nullable
    @Override
    public List<Component> getDescription() {
        return List.of(Component.translatable("more_requirement.holo.potion_effect_requirement", effect.getDisplayName(),amplifier+1, duration/20));
    }
}
