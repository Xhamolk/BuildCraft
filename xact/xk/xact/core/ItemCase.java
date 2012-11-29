package xk.xact.core;


import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import xk.xact.XActMod;
import xk.xact.util.Inventory;

import java.util.List;


public class ItemCase extends Item {

	public ItemCase(int itemID) {
		super(itemID);
		this.setItemName("chipCase");
		this.setIconIndex(2); // todo
		this.setMaxStackSize(1);
		this.setTextureFile(XActMod.TEXTURE_ITEMS);
		this.setCreativeTab(XActMod.xactTab);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
	    // Show how many chips are stored.
		if( itemStack == null || itemStack.stackTagCompound == null )
			return;

		Inventory internalInventory = new Inventory(30, "internalInventory");
		internalInventory.readFromNBT(itemStack.stackTagCompound);
		int amount = 0;
		for( ItemStack s : internalInventory.getContents() ){
			if( s != null )
				amount++;
		}
		list.add("Stored " + amount +"/60 chips.");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        itemStack.setItemDamage(1);
        player.openGui(XActMod.instance, 1, world, 0, 0, 0);
		return itemStack;
	}


}