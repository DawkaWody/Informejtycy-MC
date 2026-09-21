package daw.ka.informejtycy.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AlwaysGlintItem extends Item {
    public AlwaysGlintItem(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
