package com.gmail.zariust.otherdrops.event;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.junit.Test;

import com.gmail.zariust.common.Verbosity;
import com.gmail.zariust.otherdrops.OtherDropsConfig;
import com.gmail.zariust.otherdrops.options.Action;
import com.gmail.zariust.otherdrops.subject.BlockTarget;

public class CustomDropTest {

	@Test
	public void testIsWorld() {
		World thisWorld = getTestWorld_TestWorld();
		World notThisWorld = getTestWorld_SecondWorld();	
		
		CustomDrop customDrop = new SimpleDrop(new BlockTarget(), Action.BREAK);
		Map <World, Boolean> worlds = new HashMap<World, Boolean>();
		
		// Test with a true match
		worlds.put(null, false); // ALL = false
		worlds.put(thisWorld, true);
		
		customDrop.setWorlds(worlds);
		assertTrue(customDrop.isWorld(thisWorld));

		// Test with a negative condition
		worlds.put(thisWorld, false); // -TestWorld
		worlds.put(null, true); // ALL = true (this gets set true for negative conditions)
		
		customDrop.setWorlds(worlds);
		assertTrue(customDrop.isWorld(notThisWorld));

		// Test with a false match
		worlds.put(null, false); // ALL = false
		worlds.put(notThisWorld, true); // [SecondWorld]
		customDrop.setWorlds(worlds);
		
		assertFalse(customDrop.isWorld(thisWorld)); // should not match "TestWorld"
	}

	@Test
	public void testIsRegion() {
		CustomDrop customDrop = new SimpleDrop(new BlockTarget(), Action.BREAK);
		
		// needs verbosity
		OtherDropsConfig.setVerbosity(Verbosity.EXTREME);
		
		Map<String, Boolean> areas = new HashMap<String, Boolean>();
		areas.put("testinside", true);
		areas.put("testinside1", true);
		areas.put("testinside2", true);
		//areas.put(null, false); // means not "all" or "any" condition
		customDrop.setRegions(areas);
		
		Set<String> inRegions = new HashSet<String>();
		inRegions.add("realregion");
		inRegions.add("realregion1");
		inRegions.add("testinside2");
		
		// test a position match
		assertTrue(customDrop.isRegion(inRegions));

		// test a negative match - this should fail as we are inside the region
		areas.put("-testinside2", false);
		//areas.put(null, true); // set true on negative conditions
		customDrop.setRegions(areas);
		assertFalse(customDrop.isRegion(inRegions));
	}

	
	
	
	
	
	private World getTestWorld_TestWorld() {
		// TODO Auto-generated method stub
		return new World() {
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "TestWorld";
			}
	
			@Override
			public void sendPluginMessage(Plugin arg0, String arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Set<String> getListeningPluginChannels() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean unloadChunkRequest(int arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean unloadChunkRequest(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean unloadChunk(int arg0, int arg1, boolean arg2, boolean arg3) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean unloadChunk(int arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean unloadChunk(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean unloadChunk(Chunk arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public LightningStrike strikeLightningEffect(Location arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public LightningStrike strikeLightning(Location arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public LivingEntity spawnCreature(Location arg0, CreatureType arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Arrow spawnArrow(Location arg0, Vector arg1, float arg2, float arg3) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T extends Entity> T spawn(Location arg0, Class<T> arg1)
					throws IllegalArgumentException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void setWeatherDuration(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setTime(long arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setThundering(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setThunderDuration(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setStorm(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean setSpawnLocation(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void setSpawnFlags(boolean arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setPVP(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setKeepSpawnInMemory(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setFullTime(long arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setDifficulty(Difficulty arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setAutoSave(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void save() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean regenerateChunk(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean refreshChunk(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void playEffect(Location arg0, Effect arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void playEffect(Location arg0, Effect arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean loadChunk(int arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void loadChunk(int arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void loadChunk(Chunk arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isThundering() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isChunkLoaded(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isChunkLoaded(Chunk arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isAutoSave() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasStorm() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public WorldType getWorldType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public File getWorldFolder() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getWeatherDuration() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public UUID getUID() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getTime() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getThunderDuration() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getTemperature(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Location getSpawnLocation() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getSeed() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getSeaLevel() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public List<BlockPopulator> getPopulators() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Player> getPlayers() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean getPVP() {
				// TODO Auto-generated method stub
				return false;
			}
						
			@Override
			public int getMaxHeight() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Chunk[] getLoadedChunks() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<LivingEntity> getLivingEntities() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean getKeepSpawnInMemory() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public long getId() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getHumidity(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getHighestBlockYAt(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getHighestBlockYAt(Location arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Block getHighestBlockAt(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Block getHighestBlockAt(Location arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ChunkGenerator getGenerator() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getFullTime() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Environment getEnvironment() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Entity> getEntities() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ChunkSnapshot getEmptyChunkSnapshot(int arg0, int arg1,
					boolean arg2, boolean arg3) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Difficulty getDifficulty() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Chunk getChunkAt(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Chunk getChunkAt(Block arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Chunk getChunkAt(Location arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getBlockTypeIdAt(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getBlockTypeIdAt(Location arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Block getBlockAt(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Block getBlockAt(Location arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Biome getBiome(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean getAllowMonsters() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean getAllowAnimals() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean generateTree(Location arg0, TreeType arg1,
					BlockChangeDelegate arg2) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean generateTree(Location arg0, TreeType arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Item dropItemNaturally(Location arg0, ItemStack arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Item dropItem(Location arg0, ItemStack arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean createExplosion(double arg0, double arg1, double arg2,
					float arg3, boolean arg4) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean createExplosion(double arg0, double arg1, double arg2,
					float arg3) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean createExplosion(Location arg0, float arg1, boolean arg2) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean createExplosion(Location arg0, float arg1) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

	private World getTestWorld_SecondWorld() {
		// TODO Auto-generated method stub
		return new World() {
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "SecondWorld";
			}
	
			@Override
			public void sendPluginMessage(Plugin arg0, String arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Set<String> getListeningPluginChannels() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean unloadChunkRequest(int arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean unloadChunkRequest(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean unloadChunk(int arg0, int arg1, boolean arg2, boolean arg3) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean unloadChunk(int arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean unloadChunk(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean unloadChunk(Chunk arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public LightningStrike strikeLightningEffect(Location arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public LightningStrike strikeLightning(Location arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public LivingEntity spawnCreature(Location arg0, CreatureType arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Arrow spawnArrow(Location arg0, Vector arg1, float arg2, float arg3) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T extends Entity> T spawn(Location arg0, Class<T> arg1)
					throws IllegalArgumentException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void setWeatherDuration(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setTime(long arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setThundering(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setThunderDuration(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setStorm(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean setSpawnLocation(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void setSpawnFlags(boolean arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setPVP(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setKeepSpawnInMemory(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setFullTime(long arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setDifficulty(Difficulty arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setAutoSave(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void save() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean regenerateChunk(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean refreshChunk(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void playEffect(Location arg0, Effect arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void playEffect(Location arg0, Effect arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean loadChunk(int arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void loadChunk(int arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void loadChunk(Chunk arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isThundering() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isChunkLoaded(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isChunkLoaded(Chunk arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isAutoSave() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasStorm() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public WorldType getWorldType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public File getWorldFolder() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getWeatherDuration() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public UUID getUID() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getTime() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getThunderDuration() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getTemperature(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Location getSpawnLocation() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getSeed() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getSeaLevel() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public List<BlockPopulator> getPopulators() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Player> getPlayers() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean getPVP() {
				// TODO Auto-generated method stub
				return false;
			}
						
			@Override
			public int getMaxHeight() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Chunk[] getLoadedChunks() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<LivingEntity> getLivingEntities() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean getKeepSpawnInMemory() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public long getId() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getHumidity(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getHighestBlockYAt(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getHighestBlockYAt(Location arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Block getHighestBlockAt(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Block getHighestBlockAt(Location arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ChunkGenerator getGenerator() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getFullTime() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Environment getEnvironment() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Entity> getEntities() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ChunkSnapshot getEmptyChunkSnapshot(int arg0, int arg1,
					boolean arg2, boolean arg3) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Difficulty getDifficulty() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Chunk getChunkAt(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Chunk getChunkAt(Block arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Chunk getChunkAt(Location arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getBlockTypeIdAt(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getBlockTypeIdAt(Location arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Block getBlockAt(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Block getBlockAt(Location arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Biome getBiome(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean getAllowMonsters() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean getAllowAnimals() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean generateTree(Location arg0, TreeType arg1,
					BlockChangeDelegate arg2) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean generateTree(Location arg0, TreeType arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Item dropItemNaturally(Location arg0, ItemStack arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Item dropItem(Location arg0, ItemStack arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean createExplosion(double arg0, double arg1, double arg2,
					float arg3, boolean arg4) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean createExplosion(double arg0, double arg1, double arg2,
					float arg3) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean createExplosion(Location arg0, float arg1, boolean arg2) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean createExplosion(Location arg0, float arg1) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	
	}

}
