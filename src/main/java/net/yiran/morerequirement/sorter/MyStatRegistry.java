package net.yiran.morerequirement.sorter;


import com.google.gson.JsonElement;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;

import static se.mickelus.tetra.gui.stats.data.StatRegistry.gson;
import static se.mickelus.tetra.gui.stats.data.StatRegistry.registerStatGetter;

public class MyStatRegistry {
    public static void init() {
        registerStatGetter("mr:item", MyStatRegistry::ItemGetter);
    }

    public static IStatGetter ItemGetter(JsonElement json){
        ItemData itemData = gson.fromJson(json, ItemData.class);
        return new StatGetterItem(itemData.stat, itemData.items, itemData.tag);
    }

    public record ItemData(IStatGetter stat, String[] items, String tag){
        public ItemData(IStatGetter stat, String[] items, String tag){
            this.stat = stat;
            this.items = items;
            this.tag = tag;
        }
    }
}
