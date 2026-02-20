package com.mochi_753.clockwork_trials.common.entity;

import com.mochi_753.clockwork_trials.common.register.ModEntities;
import com.mochi_753.clockwork_trials.common.register.ModSoundEvents;
import com.mochi_753.clockwork_trials.common.world.ParadoxDamageSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public class BeamEntity extends Entity {
    private static final RandomSource RANDOM = RandomSource.create();
    private int color;
    private LivingEntity owner;

    public BeamEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BeamEntity(Level level, LivingEntity owner) {
        super(ModEntities.BEAM_ENTITY.get(), level);
        this.owner = owner;
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.color = RANDOM.nextInt(0, 0x00FFFFFF);
        this.level().playSound(null, this.blockPosition(), ModSoundEvents.BEAM01.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) return;
        if (this.tickCount > 200) this.discard();

        if (this.tickCount == 80) {
            this.level().playSound(null, this.blockPosition(), ModSoundEvents.BEAM02.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
        }
        if (this.tickCount > 80 && this.tickCount < 180) {
            List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox(), living -> !(living instanceof TimekeeperEntity));
            if (this.owner != null) {
                entities.forEach(living -> {
                    living.invulnerableTime = 0;
                    living.hurt(new ParadoxDamageSource(this.owner, living), 1);
                });
            }
        }
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }

    public int getColor() {
        return this.color;
    }
}
