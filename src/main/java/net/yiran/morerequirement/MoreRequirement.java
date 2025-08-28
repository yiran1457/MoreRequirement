package net.yiran.morerequirement;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.yiran.morerequirement.data.MRDataManager;
import net.yiran.morerequirement.data.MRUpdateDataPacket;
import net.yiran.morerequirement.requirements.*;
import net.yiran.morerequirement.requirements.grouprequirement.GroupRequirement;
import net.yiran.morerequirement.sorter.MyStatRegistry;
import org.slf4j.Logger;
import se.mickelus.mutil.network.PacketHandler;
import se.mickelus.tetra.module.schematic.requirement.CraftingRequirementDeserializer;

@Mod(MoreRequirement.MODID)
public class MoreRequirement {
    public static final String MODID = "morerequirement";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static IEventBus ModEventBus;
    public static PacketHandler NETWORK;

    public MoreRequirement() {
        ModEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEventBus.addListener(this::commonSetup);
        NETWORK = new PacketHandler(MODID,"data","1");
        if(FMLEnvironment.dist == Dist.CLIENT){
            MyStatRegistry.init();
        }
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,Config.SPEC);
        MinecraftForge.EVENT_BUS.register(new MRDataManager());

        CreativeTabHandler.init();

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

    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        NETWORK.registerPacket(MRUpdateDataPacket.class, MRUpdateDataPacket::new);
    }

}
