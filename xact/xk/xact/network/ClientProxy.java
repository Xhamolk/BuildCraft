package xk.xact.network;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.input.Keyboard;
import xk.xact.XActMod;
import xk.xact.config.KeyBindingHandler;
import xk.xact.gui.client.ChipRenderer;

public class ClientProxy extends CommonProxy {

	@SideOnly(Side.CLIENT)
	public static GuiScreen getCurrentScreen() {
		return Minecraft.getMinecraft().currentScreen;
	}

	public void registerRenderInformation() {
		// Textures
		MinecraftForgeClient.preloadTexture( XActMod.TEXTURE_ITEMS );
		MinecraftForgeClient.preloadTexture( XActMod.TEXTURE_BLOCKS );

		// Custom IItemRenderer
		MinecraftForgeClient.registerItemRenderer( XActMod.itemRecipeEncoded.shiftedIndex, new ChipRenderer() );
	}

	public void registerKeyBindings() {
		KeyBindingRegistry.registerKeyBinding( new KeyBindingHandler(
				new KeyBinding[] {
					new KeyBinding( "xact.clear", Keyboard.KEY_DOWN ),
					new KeyBinding( "xact.load", Keyboard.KEY_UP ),
					new KeyBinding( "xact.prev", Keyboard.KEY_LEFT ),
					new KeyBinding( "xact.next", Keyboard.KEY_RIGHT ),
					new KeyBinding( "xact.delete", Keyboard.KEY_DELETE ),
					new KeyBinding( "xact.reveal", Keyboard.KEY_LSHIFT )
				}, new boolean[] {
					false,
					false,
					false,
					false,
					false,
					false
		} ) );
	}
}
