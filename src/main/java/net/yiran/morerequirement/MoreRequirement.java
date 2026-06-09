package net.yiran.morerequirement;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.yiran.morerequirement.craftefffect.CustomCraftingEffectOutcome;
import net.yiran.morerequirement.data.MRDataManager;
import net.yiran.morerequirement.data.MRUpdateDataPacket;
import net.yiran.morerequirement.requirements.*;
import net.yiran.morerequirement.requirements.grouprequirement.GroupRequirement;
import net.yiran.morerequirement.sorter.MyStatRegistry;
import org.slf4j.Logger;
import se.mickelus.mutil.network.PacketHandler;
import se.mickelus.tetra.craftingeffect.CraftingEffectRegistry;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirementDeserializer;

@SuppressWarnings("removal")
@Mod(MoreRequirement.MODID)
public class MoreRequirement {
    public static final String MODID = "morerequirement";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static IEventBus ModEventBus;
    public static PacketHandler NETWORK;

    public MoreRequirement() {
        ModEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEventBus.addListener(this::commonSetup);
        NETWORK = new PacketHandler(MODID, "data", "1");
        if (FMLEnvironment.dist == Dist.CLIENT) {
            MyStatRegistry.init();
        }
        MinecraftForge.EVENT_BUS.register(MRDataManager.instance);
    }

    public static void onModLoaded() {
        CraftingRequirementDeserializer.registerSupplier("mr:group", GroupRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:advancement", AdvancementRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:biome", BiomeRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:custom", CustomRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:dimension", DimensionRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:entities", EntitiesRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:height", HeightRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:moon_phase", MoonPhaseRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:mbd", MultiblockRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:potion", PotionEffectRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:other_module", OtherModuleRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:see_sky", SeeSkyRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:time", TimeRequirement.class);
        CraftingRequirementDeserializer.registerSupplier("mr:weather", WeatherRequirement.class);
        CraftingEffectRegistry.registerConditionType("mr:advancement", AdvancementRequirement.class);
        CraftingEffectRegistry.registerConditionType("mr:biome", BiomeRequirement.class);
        CraftingEffectRegistry.registerConditionType("mr:custom", CustomRequirement.class);
        CraftingEffectRegistry.registerConditionType("mr:dimension", DimensionRequirement.class);
        CraftingEffectRegistry.registerConditionType("mr:entities", EntitiesRequirement.class);
        CraftingEffectRegistry.registerConditionType("mr:height", HeightRequirement.class);
        CraftingEffectRegistry.registerConditionType("mr:moon_phase", MoonPhaseRequirement.class);
        CraftingEffectRegistry.registerConditionType("mr:mbd", MultiblockRequirement.class);
        CraftingEffectRegistry.registerConditionType("mr:potion", PotionEffectRequirement.class);
        CraftingEffectRegistry.registerConditionType("mr:see_sky", SeeSkyRequirement.class);
        CraftingEffectRegistry.registerConditionType("mr:time", TimeRequirement.class);
        CraftingEffectRegistry.registerConditionType("mr:wrap_target", WarpTargetItemRequirement.class);
        CraftingEffectRegistry.registerConditionType("mr:weather", WeatherRequirement.class);
        CraftingEffectRegistry.registerEffectType("mr:custom", CustomCraftingEffectOutcome.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(MoreRequirement::onModLoaded);
        NETWORK.registerPacket(MRUpdateDataPacket.class, MRUpdateDataPacket::new);
    }

}
