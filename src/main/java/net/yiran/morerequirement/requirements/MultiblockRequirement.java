package net.yiran.morerequirement.requirements;

import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;
import se.mickelus.tetra.module.schematic.CraftingContext;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirement;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MultiblockRequirement implements CraftingRequirement {
    public Map<Block, Integer> blocks;
    public int x = 3;
    public int y = 2;
    public int z = 3;

    @Override
    public boolean test(CraftingContext cxt) {
        Level level = cxt.world;
        if (level == null) return false;
        BlockPos pos = cxt.pos;
        if (pos == null) return false;
        AABB aabb = new AABB(pos).inflate(x, y, z);
        Map<Block, Integer> map = level.getBlockStatesIfLoaded(aabb).map(BlockBehaviour.BlockStateBase::getBlock).collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(i -> 1)));
        for (Map.Entry<Block, Integer> entry : blocks.entrySet()) {
            if (map.getOrDefault(entry.getKey(), 0) < entry.getValue()) return false;
        }
        return true;
    }

    @Nullable
    @Override
    public List<Component> getDescription() {
        ImmutableList.Builder<Component> builder = ImmutableList.builder();
        int size = blocks.size();
        int index = 0;
        builder.add(Component.translatable("more_requirement.holo.multiblock_requirement", x*2+1, y*2+1, z*2+1));
        for (Map.Entry<Block, Integer> entry : blocks.entrySet()) {
            index++;

            if (index == size) {
                builder.add(Component.literal( " §8└§r " ).append(entry.getKey().getName()).append(" x"+entry.getValue()));
            }else {
                builder.add(Component.literal( " §8├§r " ).append(entry.getKey().getName()).append(" x"+entry.getValue()));
            }
        }
        return builder.build();
        /*
        ImmutableList.Builder<Component> builder = ImmutableList.builder();
        Set<Map.Entry<Block, Integer>>  entrys = blocks.entrySet();
        builder.add(Component.literal(I18n.get("tetra.holo.or_requirement")).withStyle(ChatFormatting.GRAY));

        for(int i = 0; i <entrys.size(); ++i) {
            List<Component> description = this.requirements[i].getDescription();
            if (description != null) {
                for(int j = 0; j < description.size(); ++j) {
                    if (j == 0) {
                        builder.add(Component.literal(i == this.requirements.length - 1 ? " §8└§r " : " §8├§r ").append((Component)description.get(j)));
                    } else {
                        builder.add(Component.literal(" §8│§r ").append((Component)description.get(j)));
                    }
                }
            }
        }

        return builder.build();*/
    }
}
