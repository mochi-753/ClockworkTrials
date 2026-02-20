package com.mochi_753.clockwork_trials.common.entity;

import com.mochi_753.clockwork_trials.client.util.SoundUtils;
import com.mochi_753.clockwork_trials.common.ClockworkTrialsConfig;
import com.mochi_753.clockwork_trials.common.register.ModEntities;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Set;

public class TimekeeperEntity extends PathfinderMob implements GeoEntity, RangedAttackMob {
    private static final Set<ResourceKey<DamageType>> IMMUNE_DAMAGE = Set.of(
            DamageTypes.CRAMMING,
            DamageTypes.DROWN,
            DamageTypes.DRY_OUT,
            DamageTypes.FALL,
            DamageTypes.FALLING_ANVIL,
            DamageTypes.FALLING_BLOCK,
            DamageTypes.FALLING_STALACTITE,
            DamageTypes.HOT_FLOOR,
            DamageTypes.IN_FIRE,
            DamageTypes.IN_WALL,
            DamageTypes.LAVA,
            DamageTypes.ON_FIRE,
            DamageTypes.STALAGMITE,
            DamageTypes.UNATTRIBUTED_FIREBALL,
            DamageTypes.WITHER
    );
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public TimekeeperEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    public static AttributeSupplier setAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 400D)
                .add(Attributes.ATTACK_DAMAGE, 10F)
                .add(Attributes.ATTACK_SPEED, 1F)
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.FLYING_SPEED, 1.0F)
                .add(Attributes.FOLLOW_RANGE, 64D)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true));

        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(7, new RangedAttackGoal(this, 1.0, 40, 15F));

        super.registerGoals();
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        target.invulnerableTime = 0;

        HomingProjectile projectile = new HomingProjectile(ModEntities.HOMING_PROJECTILE.get(), this.level(), 0.6, 0.15, this, target);
        projectile.setPos(this.position());
        this.level().addFreshEntity(projectile);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<TimekeeperEntity> theTimekeeperEntityAnimationState) {
        theTimekeeperEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.the_timekeeper.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.setPersistenceRequired();

        if (this.level().isClientSide()) {
            SoundUtils.playBGM(this);
        } else {
            this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16), living -> !(living instanceof TimekeeperEntity)).forEach(living -> {
                BeamEntity beam = new BeamEntity(this.level(), this);
                beam.setPos(living.position());
                this.level().addFreshEntity(beam);
            });
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        for (ResourceKey<DamageType> type : IMMUNE_DAMAGE) {
            if (pSource.is(type)) return true;
        }
        return super.isInvulnerableTo(pSource);
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);
    }

    @Override
    public void tick() {
        if (this.level().isClientSide()) {
            super.tick();
        } else {
            for (int i = 0; i < ClockworkTrialsConfig.COMMON.entityTickSpeedMultiplier.get(); i++) {
                super.tick();

                if (this.level() instanceof ServerLevel serverLevel) {
                    if (serverLevel.getChunkSource().chunkMap.entityMap.get(this.getId()) != null) {
                        ServerEntity serverEntity = serverLevel.getChunkSource().chunkMap.entityMap.get(this.getId()).serverEntity;
                        serverEntity.sendChanges();
                    }
                }
            }
        }
    }
}
