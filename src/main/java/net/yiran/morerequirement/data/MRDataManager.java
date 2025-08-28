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
import se.mickelus.tetra.blocks.scroll.ScrollData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.yiran.morerequirement.MoreRequirement.NETWORK;
import static se.mickelus.tetra.data.DataManager.gson;

public class MRDataManager implements DataDistributor {
    public static MRDataManager instance;

    public DataStore<GroupRequirementStore> groupRequirement;
    public DataStore<ScrollData[]> scrolls;
    public List<DataStore<?>> dataStores=new ArrayList<>(10);

    public MRDataManager() {
        instance = this;
        groupRequirement = new DataStore<>(gson, MoreRequirement.MODID,"group", GroupRequirementStore.class,this);
        scrolls = new DataStore<>(gson, MoreRequirement.MODID,"scrolls", ScrollData[].class,this);
        groupRequirement.onReload(()->GroupRequirementStore.handler(groupRequirement.getData()));
        addDatastore(groupRequirement,scrolls);
    }

    public void addDatastore(DataStore<?>... datastore) {
        dataStores.addAll(List.of(datastore));
    }

    @SubscribeEvent(
            priority = EventPriority.LOWEST
    )
    public void addReloadListener(AddReloadListenerEvent event) {
        dataStores.forEach(event::addListener);
    }

    @SubscribeEvent
    public void playerConnected(PlayerEvent.PlayerLoggedInEvent event) {
        dataStores.forEach(dataStore -> dataStore.sendToPlayer((ServerPlayer)event.getEntity()));
    }


    public void onDataReceived(String directory, Map<ResourceLocation, String> data) {
        dataStores.stream()
                .filter(dataStore -> dataStore.getDirectory().equals(directory))
                .forEach(dataStore -> dataStore.loadFromPacket(data));
        /*if (groupRequirement.getDirectory().equals(directory))
            groupRequirement.loadFromPacket(data);*/
    }

    @Override
    public void sendToAll(String directory, Map<ResourceLocation, JsonElement> data) {
        NETWORK.sendToAllPlayers(new MRUpdateDataPacket(directory, data));
    }

    @Override
    public void sendToPlayer(ServerPlayer player, String directory, Map<ResourceLocation, JsonElement> data) {
        NETWORK.sendTo(new MRUpdateDataPacket(directory, data), player);
    }
}
