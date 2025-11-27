package net.yiran.morerequirement.core.mixins;

import net.yiran.morerequirement.MoreRequirement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.mickelus.tetra.TetraMod;

@Mixin(TetraMod.class)
public class TetraModMixin {
    @Inject(method = "<init>",at = @At("RETURN"))
    private void init(CallbackInfo ci){
        MoreRequirement.onModLoaded();
    }
}
