package daw.ka.informejtycy.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AlwaysGlintItem extends Item {
    public AlwaysGlintItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
