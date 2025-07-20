package net.yiran.morerequirement.sorter;


import com.google.gson.JsonElement;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;

import static se.mickelus.tetra.gui.stats.data.StatRegistry.*;

public class MyStatRegistry {
    public static void init(FMLClientSetupEvent event) {
        registerStatGetter("mr:item", MyStatRegistry::ItemGetter);
    }

    public static IStatGetter ItemGetter(JsonElement json){
        ItemData itemData = gson.fromJson(json, ItemData.class);
        return new StatGetterItem(itemData.stat, itemData.items, itemData.tag);
    }

    public static record ItemData(IStatGetter stat,String[] items,String tag){
        public ItemData(IStatGetter stat, String[] items, String tag){
            this.stat = stat;
            this.items = items;
            this.tag = tag;
        }
    }
}
