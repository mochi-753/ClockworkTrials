package com.mochi_753.clockwork_trials.common.entity;

import com.mochi_753.clockwork_trials.common.world.ParadoxDamageSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomingProjectile extends Projectile {
    private static final RandomSource RANDOM = RandomSource.create();
    private static final int MAX_LIFE = 160;

    private final int color;
    private double speed;
    private double turnStrength;
    private LivingEntity shooter;
    private LivingEntity target;
    private int targetId;


    public HomingProjectile(EntityType<? extends HomingProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.color = 0x80000000 | RANDOM.nextInt(0x00FFFFFF + 1);

    }

    public HomingProjectile(EntityType<? extends HomingProjectile> pEntityType, Level pLevel, double speed, double turnStrength, LivingEntity shooter, LivingEntity target) {
        super(pEntityType, pLevel);
        this.color = 0x80000000 | RANDOM.nextInt(0x00FFFFFF + 1);
        this.speed = speed;
        this.turnStrength = turnStrength;
        this.shooter = shooter;
        this.target = target;
        if (target != null) targetId = target.getId();
        Vec3 look = shooter.getLookAngle();
        this.setDeltaMovement(look.x() * 0.2, look.y() * 0.2, look.z() * 0.2);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (this.tickCount > MAX_LIFE) {
                this.discard();
                return;
            }

            if (this.target == null && this.targetId != 0) {
                Entity entity = this.level().getEntity(this.targetId);
                if (entity instanceof LivingEntity) this.target = (LivingEntity) entity;
            }

            if (this.target == null || !this.target.isAlive() || this.target.isDeadOrDying()) {
                this.moveTowardAndUpdate(this.getDeltaMovement());
                return;
            }

            Vec3 targetPos = this.target.getEyePosition();
            Vec3 currentPos = new Vec3(this.getX(), this.getY(), this.getZ());
            Vec3 toTarget = targetPos.subtract(currentPos);
            Vec3 desiredDir = toTarget.normalize();

            Vec3 currentVel = this.getDeltaMovement();
            Vec3 desiredVel = desiredDir.scale(this.speed);

            Vec3 newVel = currentVel.add(desiredVel.subtract(currentVel).scale(turnStrength));
            if (newVel.lengthSqr() < 1e-6) newVel = desiredDir.scale(this.speed * 0.5);

            double newSpeed = newVel.length();
            if (newSpeed > 0) newVel = newVel.scale(this.speed / newSpeed);

            this.setDeltaMovement(newVel);
            this.moveTowardAndUpdate(newVel);
            this.updateRotationFromMotion(newVel);

            List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox(), livingEntity -> livingEntity != this.shooter);
            if (!entities.isEmpty()) {
                entities.forEach(livingEntity -> {
                    if (shooter != null && livingEntity != null) {
                        livingEntity.hurt(new ParadoxDamageSource(shooter, livingEntity), 6F);
                    }
                });
                this.discard();
            }
        }
    }

    private void moveTowardAndUpdate(Vec3 vel) {
        this.move(net.minecraft.world.entity.MoverType.SELF, vel);
    }

    private void updateRotationFromMotion(Vec3 vel) {
        double horizon = Math.sqrt(vel.x * vel.x + vel.z * vel.z);
        this.setXRot((float) (Math.atan2(vel.y, horizon) * (180D / Math.PI)));
        this.setYRot((float) (Math.atan2(vel.x, vel.z) * (180D / Math.PI)));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("TargetId", this.targetId);
        pCompound.putDouble("Speed", this.speed);
        pCompound.putDouble("TurnStrength", this.turnStrength);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("TargetId")) this.targetId = pCompound.getInt("TargetId");
        if (pCompound.contains("Speed")) this.speed = pCompound.getDouble("Speed");
        if (pCompound.contains("TurnStrength")) this.turnStrength = pCompound.getDouble("TurnStrength");
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public int getColor() {
        return this.color;
    }
}
