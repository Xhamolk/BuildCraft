package buildcraft.core.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.inventory.InventoryHelper;

// A Transactor according to the InventoryAPI standards.
public class TransactorDefault extends Transactor {

	protected IInventory inventory;
	protected InventoryHelper helper;

	public TransactorDefault(IInventory inventory) {
		this.inventory = inventory;
		this.helper = new InventoryHelper(inventory);
	}

	@Override
	public int inject(ItemStack stack, ForgeDirection orientation, boolean doAdd) {
		helper.setSide(orientation);

		if (!helper.canPlaceItemOnInventory(stack, false))
			return 0;

		if (doAdd) {
			return helper.addItemToInventory(stack);
		} else {
			int space = helper.getSpaceForItem(stack);
			if (space >= stack.stackSize)
				return stack.stackSize;
			else
				return space;
		}
	}

}
