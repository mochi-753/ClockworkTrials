package com.mochi_753.clockwork_trials.common.entity;

import com.mochi_753.clockwork_trials.common.register.ModEntities;
import com.mochi_753.clockwork_trials.common.register.ModSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SummonEntity extends Entity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SummonEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<SummonEntity> summonEntityAnimationState) {
        summonEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.summon.main", Animation.LoopType.PLAY_ONCE));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.level().playSound(null, this.blockPosition(), ModSoundEvents.TICKTOCK.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > 200) {
            TimekeeperEntity entity = new TimekeeperEntity(ModEntities.TIMEKEEPER_ENTITY.get(), this.level());
            entity.setPos(this.position());
            this.level().addFreshEntity(entity);
            this.level().playSound(null, this.blockPosition(), ModSoundEvents.BELL_RINGS.get(), SoundSource.MASTER, 1.5F, 1.0F);
            this.discard();
        }
    }
}
