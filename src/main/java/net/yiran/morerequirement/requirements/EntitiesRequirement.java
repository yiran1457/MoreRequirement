package net.yiran.morerequirement.requirements;

import com.google.common.collect.ImmutableList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import se.mickelus.tetra.module.schematic.CraftingContext;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirement;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntitiesRequirement implements CraftingRequirement {
    public Map<ResourceLocation, Integer> entities;
    public int x = 5;
    public int y = 2;
    public int z = 5;

    @Override
    public boolean test(CraftingContext cxt) {
        Level level = cxt.world;
        if (level == null) return false;
        BlockPos pos = cxt.pos;
        if (pos == null) return false;
        AABB aabb = new AABB(pos).inflate(x, y, z);
        Registry<EntityType<?>> registry = level.registryAccess().registryOrThrow(Registries.ENTITY_TYPE);
        Map<ResourceLocation, Integer> map = level.getEntities(null,aabb )
                .stream()
                .map(Entity::getType)
                .map(registry::getKey)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(i -> 1)));
        for (Map.Entry<ResourceLocation, Integer> entry : entities.entrySet()) {
            if (map.getOrDefault(entry.getKey(), 0) < entry.getValue()) return false;
        }
        return true;
    }

    @Nullable
    @Override
    public List<Component> getDescription() {
        ImmutableList.Builder<Component> builder = ImmutableList.builder();
        int size = entities.size();
        int index = 0;
        builder.add(Component.translatable("more_requirement.holo.entities_requirement", x*2+1, y*2+1, z*2+1));
        for (Map.Entry<ResourceLocation, Integer> entry : entities.entrySet()) {
            index++;

            Component name = Component.translatable(Util.makeDescriptionId("entity", entry.getKey()));
            if (index == size) {
                builder.add(Component.literal( " §8└§r " ).append(name).append(" x"+entry.getValue()));
            }else {
                builder.add(Component.literal( " §8├§r " ).append(name).append(" x"+entry.getValue()));
            }
        }
        return builder.build();
    }
}
