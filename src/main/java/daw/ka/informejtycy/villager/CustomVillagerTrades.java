package daw.ka.informejtycy.villager;

import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

public class CustomVillagerTrades {
	public static final TradeOffer STICKY_NOTES_TRADE = new TradeOffer(
			new TradedItem(Items.EMERALD, 2),
			new ItemStack(CustomItems.STICKY_NOTES, 15),
			80, 5, 0.04f
	);

	public static void registerAll() {
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 1, factories ->
				factories.add((world, entity, random) -> STICKY_NOTES_TRADE)
		);
	}
}
