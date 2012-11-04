package xk.xact.util;

import net.minecraft.src.*;

/**
 * Simple implementation of IInventory
 * @author Xhamolk_
 */
public class Inventory implements IInventory {

	/**
	 * The size of this IInventory.
	 * Consider this as the amount of stacks that can fit inside.
	 */
    protected int size;

	/**
	 * The name that describes this IInventory.
	 */
    private final String name;

	/**
	 * The array of stacks contained in this IInventory.
	 */
    private ItemStack[] internalInv;

	/**
	 * The maximum number of items that can fit on an empty stack.
	 */
    protected int maxStackSize = 64;


	/**
	 * Creates a new Inventory with the specified size and name.
	 * @param size the size of this inventory.
	 * @param name the name that describes this inventory.
	 */
    public Inventory(int size, String name){
        this.size = size;
        this.name = name;
        this.internalInv = new ItemStack[size];
    }

	/**
	 * Sets the new contents on the inventory, replacing the previous ones.
	 * @param contents the array containing all the contents.
	 */
	public void setContents(ItemStack[] contents) {
		this.size = contents.length;
		this.internalInv = contents;
	}


	public ItemStack[] getContents() {
		return this.internalInv.clone();
	}
    
    @Override
    public int getSizeInventory() {
        return size;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if( 0 <= slot && slot < size )
			return internalInv[slot];
		return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {
		if (internalInv[slot] == null)
			return null;
		if (internalInv[slot].stackSize > count)
			return internalInv[slot].splitStack(count);

		ItemStack retValue = internalInv[slot];
		internalInv[slot] = null;
        return retValue;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack retValue = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return retValue;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        if( 0 <= slot && slot < size )
			internalInv[slot] = itemStack;
    }

    @Override
    public String getInvName() {
        return name;
    }

    @Override
    public int getInventoryStackLimit() {
        return maxStackSize;
    }

    @Override
    public void onInventoryChanged() { }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
    }

    @Override
    public void openChest() { }

    @Override
    public void closeChest() { }


    ///////////////
    ///// NBT

    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList list = ((NBTTagCompound)compound.getTag("inv."+name)).getTagList("inventoryContents");

        for (int i=0; i<list.tagCount(); i++) {
            NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
            int index = tag.getInteger("index");
            internalInv[index] = ItemStack.loadItemStackFromNBT(tag);
        }
    }

    public void writeToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < internalInv.length; i++) {
            if ( internalInv[i] != null && internalInv[i].stackSize > 0) {
                NBTTagCompound tag = new NBTTagCompound();
                list.appendTag(tag);
                tag.setInteger("index", i);
                internalInv[i].writeToNBT(tag);
            }
        }
		NBTTagCompound ownTag = new NBTTagCompound();
			ownTag.setTag("inventoryContents", list);
		compound.setTag("inv."+name, ownTag);
    }
}