package net.yiran.morerequirement;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.yiran.morerequirement.requirements.AdvancementRequirement;
import net.yiran.morerequirement.requirements.BiomeRequirement;
import net.yiran.morerequirement.requirements.CustomRequirement;
import net.yiran.morerequirement.requirements.MultiblockRequirement;
import org.slf4j.Logger;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirementDeserializer;

@Mod(MoreRequirement.MODID)
public class MoreRequirement {
    public static final String MODID = "morerequirement";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MoreRequirement() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CraftingRequirementDeserializer.registerSupplier("mr:advancement", AdvancementRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:biome", BiomeRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:mbd", MultiblockRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:custom", CustomRequirement.class);
    }
}
