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
package foundationgames.enhancedblockentities.mixin;

import com.google.common.collect.Lists;
import foundationgames.enhancedblockentities.util.EBEUtil;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import foundationgames.enhancedblockentities.util.hacks.ExperimentalSetup;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.FallbackResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(SimpleReloadableResourceManager.class)
public abstract class LifecycledResourceManagerImplMixin {
    @Shadow
    @Final
    private Map<String, FallbackResourceManager> namespacedPacks;

    @Shadow @Final private List<PackResources> packs = Lists.newArrayList();;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void enhanced_bes$injectRRP(PackType p_10878_, CallbackInfo ci) {
        var pack = new ArrayList<>(packs);

        int idx = 0;
        if (pack.size() > 0) do {
            idx++;
        } while (idx < pack.size() && !EBEUtil.isVanillaResourcePack(pack.get(idx - 1)));
        pack.add(idx, ResourceUtil.getPack());
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void enhanced_bes$injectExperimentalPack(PackType p_10878_, CallbackInfo ci) {
        ExperimentalSetup.cacheResources((ResourceManager) this);
        ExperimentalSetup.setup();

        addPack(p_10878_, ResourceUtil.getExperimentalPack());
    }

    private void addPack(PackType type, PackResources pack) {
        for (var namespace : pack.getNamespaces(type)) {
            this.namespacedPacks.computeIfAbsent(namespace, n -> new FallbackResourceManager(type, n)).add(pack);
        }
    }
}