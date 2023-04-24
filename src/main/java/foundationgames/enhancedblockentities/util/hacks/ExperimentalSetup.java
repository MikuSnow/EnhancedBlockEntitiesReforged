/*
 * This file is part of EBE Reforged - https://github.com/CCr4ft3r/EnhancedBlockEntitiesReforged
 * Copyright (C) 2023 FoundationGames and contributors: https://github.com/FoundationGames/EnhancedBlockEntities/tree/1.19/src/main/java/foundationgames/enhancedblockentitiesl
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package foundationgames.enhancedblockentities.util.hacks;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.client.resource.ExperimentalResourcePack;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.DyeColor;

import java.io.IOException;

public enum ExperimentalSetup {
    ;
    private static ResourceManager RESOURCES;

    public static void setup() {
        EBEConfig config = EnhancedBlockEntities.CONFIG;
        ResourceUtil.resetExperimentalPack();

        if (config.renderEnhancedChests && config.experimentalChests) {
            try {
                if (RESOURCES != null) setupChests(RESOURCES);
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error("Error loading experimental chests!", e);
                config.experimentalChests = false;
                config.save();
            }
        }
        if (config.renderEnhancedBeds && config.experimentalBeds) {
            try {
                if (RESOURCES != null) setupBeds(RESOURCES);
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error("Error loading experimental beds!", e);
                config.experimentalBeds = false;
                config.save();
            }
        }
        if (config.renderEnhancedSigns && config.experimentalSigns) {
            try {
                if (RESOURCES != null) setupSigns(RESOURCES);
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error("Error loading experimental signs!", e);
                config.experimentalSigns = false;
                config.save();
            }
        }
    }

    public static void setupChests(ResourceManager manager) throws IOException {
        ExperimentalResourcePack p = ResourceUtil.getExperimentalPack();

        ResourceHacks.addChestParticleTexture("chest", "entity/chest/normal", manager, p);
        ResourceHacks.addChestParticleTexture("trapped_chest", "entity/chest/trapped", manager, p);
        ResourceHacks.addChestParticleTexture("ender_chest", "entity/chest/ender", manager, p);
        ResourceHacks.addChestParticleTexture("christmas_chest", "entity/chest/christmas", manager, p);
    }

    public static void setupBeds(ResourceManager manager) throws IOException {
        ExperimentalResourcePack p = ResourceUtil.getExperimentalPack();

        for (var color : DyeColor.values()) {
            ResourceHacks.addBedParticleTexture(color.getName(), "entity/bed/" + color.getName(), manager, p);
        }
    }

    public static void setupSigns(ResourceManager manager) throws IOException {
        ExperimentalResourcePack p = ResourceUtil.getExperimentalPack();

        ResourceHacks.addSignParticleTexture("oak", "entity/signs/oak", manager, p);
        ResourceHacks.addSignParticleTexture("birch", "entity/signs/birch", manager, p);
        ResourceHacks.addSignParticleTexture("spruce", "entity/signs/spruce", manager, p);
        ResourceHacks.addSignParticleTexture("jungle", "entity/signs/jungle", manager, p);
        ResourceHacks.addSignParticleTexture("acacia", "entity/signs/acacia", manager, p);
        ResourceHacks.addSignParticleTexture("dark_oak", "entity/signs/dark_oak", manager, p);
        ResourceHacks.addSignParticleTexture("mangrove", "entity/signs/mangrove", manager, p);
        ResourceHacks.addSignParticleTexture("crimson", "entity/signs/crimson", manager, p);
        ResourceHacks.addSignParticleTexture("warped", "entity/signs/warped", manager, p);
    }

    public static void cacheResources(ResourceManager resources) {
        RESOURCES = resources;
    }
}