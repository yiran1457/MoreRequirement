package net.yiran.morerequirement.requirements;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import se.mickelus.tetra.module.schematic.CraftingContext;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirement;

import javax.annotation.Nullable;
import java.util.List;

public class SeeSkyRequirement implements CraftingRequirement {
    @Override
    public boolean test(CraftingContext cxt) {
        Level level = cxt.world;
        if (level == null) return false;
        BlockPos pos = cxt.pos;
        if (pos == null) return false;
        return  isExposedToSky(pos, level);
    }
    public static boolean isExposedToSky(BlockPos pos,Level level) {
        BlockPos.MutableBlockPos mut = pos.above().mutable();
        while (mut.getY() < level.getMaxBuildHeight()) {
            BlockState state = level.getBlockState(mut);
            if (state.isAir()) {
                mut.move(Direction.UP);
                continue;
            }
            if (state.blocksMotion()) {
                return false;
            }
            mut.move(Direction.UP);
        }
        return true;
    }
    @Nullable
    @Override
    public List<Component> getDescription() {
        return List.of(Component.translatable("more_requirement.holo.see_sky_requirement"));
    }
}
