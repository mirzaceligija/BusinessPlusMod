package com.iamanim0.businessplusmod;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.iamanim0.businessplusmod.common.capability.CapabilityHandler;
import com.iamanim0.businessplusmod.common.capability.capability.CreditCardInfo;
import com.iamanim0.businessplusmod.common.capability.capability.ICreditCardInfo;
import com.iamanim0.businessplusmod.common.capability.storage.CreditCardStorage;
import com.iamanim0.businessplusmod.core.init.BlockInit;
import com.iamanim0.businessplusmod.core.init.ContainerTypeInit;
import com.iamanim0.businessplusmod.core.init.ItemInit;
import com.iamanim0.businessplusmod.core.init.TileEntityTypeInit;
import com.iamanim0.businessplusmod.network.packet.ModPacketHandler;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BusinessPlusMod.MOD_ID)
public class BusinessPlusMod
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "businessplusmod";
    public static final ItemGroup BUSINESSPLUS_GROUP = new BusinessPlusGroup("businessplustab");
    
    public BusinessPlusMod() {
    	
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	
    	bus.addListener(this::setup);
    	
    	ItemInit.ITEMS.register(bus);
    	BlockInit.BLOCKS.register(bus);
    	TileEntityTypeInit.TILE_ENTITY_TYPES.register(bus);
    	ContainerTypeInit.CONTAINER_TYPES.register(bus);
    	
    	MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
    	

    	// Register the setup method for mod loading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for mod loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for mod loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for mod loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        
        CapabilityManager.INSTANCE.register(ICreditCardInfo.class, new CreditCardStorage(), CreditCardInfo::new);
        ModPacketHandler.registerMessages();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("businessplusmod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
    
    public static class BusinessPlusGroup extends ItemGroup {

		public BusinessPlusGroup(String label) {
			super(label);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public ItemStack createIcon() {
			return ItemInit.DOLLAR_500.get().getDefaultInstance();
		}

		@Override
		public void fill(NonNullList<ItemStack> items) {
			// TODO Auto-generated method stub			
			items.add(ItemInit.CREDIT_CARD_ITEM.get().getDefaultInstance());
			items.add(ItemInit.WALLET_ITEM.get().getDefaultInstance());
			
			items.add(ItemInit.CENT_1.get().getDefaultInstance());
			items.add(ItemInit.CENT_2.get().getDefaultInstance());
			items.add(ItemInit.CENT_5.get().getDefaultInstance());
			items.add(ItemInit.CENT_10.get().getDefaultInstance());
			items.add(ItemInit.CENT_20.get().getDefaultInstance());
			items.add(ItemInit.CENT_50.get().getDefaultInstance());
			items.add(ItemInit.DOLLAR_1.get().getDefaultInstance());
			items.add(ItemInit.DOLLAR_2.get().getDefaultInstance());
			items.add(ItemInit.DOLLAR_5.get().getDefaultInstance());
			items.add(ItemInit.DOLLAR_10.get().getDefaultInstance());
			items.add(ItemInit.DOLLAR_20.get().getDefaultInstance());
			items.add(ItemInit.DOLLAR_50.get().getDefaultInstance());
			items.add(ItemInit.DOLLAR_100.get().getDefaultInstance());
			items.add(ItemInit.DOLLAR_500.get().getDefaultInstance());
			items.add(ItemInit.DOLLAR_10000.get().getDefaultInstance());
			items.add(ItemInit.DOLLAR_50000.get().getDefaultInstance());
			items.add(ItemInit.DOLLAR_1000000.get().getDefaultInstance());
			
			items.add(ItemInit.LICENCE_ITEM.get().getDefaultInstance());
			items.add(ItemInit.VAULT_CHEST_BLOCK.get().getDefaultInstance());
			items.add(ItemInit.MONEY_PILE_BLOCK.get().getDefaultInstance());
			items.add(ItemInit.FIRST_BLOCK.get().getDefaultInstance());
			items.add(ItemInit.ATM.get().getDefaultInstance());
			items.add(ItemInit.MARKETPLACE.get().getDefaultInstance());
			items.add(ItemInit.MARKET.get().getDefaultInstance());
			//super.fill(items);
		}
    }
}
