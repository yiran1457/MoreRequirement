package net.yiran.morerequirement.data;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.yiran.morerequirement.MoreRequirement;
import net.yiran.morerequirement.requirements.grouprequirement.GroupRequirementStore;
import se.mickelus.mutil.data.DataDistributor;
import se.mickelus.mutil.data.DataStore;
import se.mickelus.tetra.TetraMod;

import java.util.Map;

import static se.mickelus.tetra.data.DataManager.gson;

public class MRDataManager implements DataDistributor {
    public static MRDataManager instance;

    public DataStore<GroupRequirementStore> groupRequirement;

    public MRDataManager() {
        instance = this;
        groupRequirement = new DataStore<>(gson, MoreRequirement.MODID,"group", GroupRequirementStore.class,this);
        groupRequirement.onReload(()->GroupRequirementStore.handler(groupRequirement.getData()));

    }


    @SubscribeEvent(
            priority = EventPriority.LOWEST
    )
    public void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(groupRequirement);
    }

    @SubscribeEvent
    public void playerConnected(PlayerEvent.PlayerLoggedInEvent event) {
        groupRequirement.sendToPlayer((ServerPlayer)event.getEntity());
    }


    public void onDataReceived(String directory, Map<ResourceLocation, String> data) {
        if (groupRequirement.getDirectory().equals(directory))
            groupRequirement.loadFromPacket(data);
    }

    @Override
    public void sendToAll(String directory, Map<ResourceLocation, JsonElement> data) {
        TetraMod.packetHandler.sendToAllPlayers(new MRUpdateDataPacket(directory, data));
    }

    @Override
    public void sendToPlayer(ServerPlayer player, String directory, Map<ResourceLocation, JsonElement> data) {
        TetraMod.packetHandler.sendTo(new MRUpdateDataPacket(directory, data), player);
    }
}
