package com.mochi_753.clockwork_trials.common.world;

import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.util.ResourceLocationUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.time.Year;

public class ParadoxDamageSource extends DamageSource {
    private static final ResourceKey<DamageType> PARADOX =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "paradox"));

    public ParadoxDamageSource(LivingEntity attacker, LivingEntity victim) {
        super(attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(PARADOX), attacker);
    }

    @Override
    public @NotNull Component getLocalizedDeathMessage(LivingEntity pLivingEntity) {
        int currentYear = Year.now().getValue();
        int elapsed1 = currentYear - 2014; // Number of years since 1.7.10
        int elapsed2 = currentYear - 2017; // Number of years since 1.12.2

        String s = "death.attack." + this.getMsgId() + "." + pLivingEntity.getRandom().nextInt(10);
        return Component.translatable(s, pLivingEntity.getDisplayName(), elapsed1, elapsed2);
    }
}
