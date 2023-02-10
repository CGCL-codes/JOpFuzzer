package com.creeperlwd.testmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class GoldBucket extends Item{

    public GoldBucket(int I) {
        this.setUnlocalizedName("GBucket");
        this.setTextureName("testmod:GBucket");
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
}