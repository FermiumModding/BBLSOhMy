package bblsom;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import bblsom.handlers.ModRegistry;
import bblsom.proxy.CommonProxy;

@Mod(modid = BBLSOhMy.MODID, version = BBLSOhMy.VERSION, name = BBLSOhMy.NAME, dependencies = "required:fermiumbooter")
public class BBLSOhMy {
    public static final String MODID = "bblsom";
    public static final String VERSION = "1.1.3";
    public static final String NAME = "BBLSOhMy";
    public static final Logger LOGGER = LogManager.getLogger();
	
    @SidedProxy(clientSide = "bblsom.proxy.ClientProxy", serverSide = "bblsom.proxy.CommonProxy")
    public static CommonProxy PROXY;
	
	@Instance(MODID)
	public static BBLSOhMy instance;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRegistry.init();
        BBLSOhMy.PROXY.preInit();
    }
}