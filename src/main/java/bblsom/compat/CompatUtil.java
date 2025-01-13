package bblsom.compat;

import net.minecraftforge.fml.common.Loader;

public abstract class CompatUtil {
	
	private static Boolean isAquaAcrobaticsLoaded = null;
	
	public static boolean isAquaAcrobaticsLoaded() {
		if(isAquaAcrobaticsLoaded == null) {
			isAquaAcrobaticsLoaded = Loader.isModLoaded("aquaacrobatics");
		}
		return isAquaAcrobaticsLoaded;
	}
}