package net.yiran.morerequirement.data;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.data.AbstractUpdateDataPacket;

import java.util.Map;

public class MRUpdateDataPacket extends AbstractUpdateDataPacket {
    public MRUpdateDataPacket() {
    }

    public MRUpdateDataPacket(String directory, Map<ResourceLocation, JsonElement> data) {
        super(directory, data);
    }

    @Override
    public void handle(Player player) {
        MRDataManager.instance.onDataReceived(this.directory, this.data);
    }
}
