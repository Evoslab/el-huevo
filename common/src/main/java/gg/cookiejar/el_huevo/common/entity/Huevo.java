package gg.cookiejar.el_huevo.common.entity;

import gg.cookiejar.el_huevo.core.ElHuevo;
import gg.moonflower.pollen.api.entity.PollenEntity;
import gg.moonflower.pollen.api.util.NbtConstants;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimatedEntity;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimationEffectHandler;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Steven
 */
public class Huevo extends TamableAnimal implements AnimatedEntity, PollenEntity {
    public static final AnimationState WALK = new AnimationState(20, new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.walk"));
    public static final AnimationState IDLE = new AnimationState(40, new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.idle"));
    public static final AnimationState FALL = new AnimationState(40, new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.fall"));
    public static final AnimationState DANCE = new AnimationState(40, new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.dance"));
    public static final AnimationState SIT = new AnimationState(10, new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.sit"));
    private static final AnimationState[] ANIMATIONS = Stream.of(WALK, IDLE, FALL, DANCE, SIT).toArray(AnimationState[]::new);

    private static final EntityDataAccessor<Integer> DATA_CLOTHING_COLOR = SynchedEntityData.defineId(Huevo.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(Huevo.class, EntityDataSerializers.BYTE);

    private final AnimationEffectHandler effectHandler;
    private AnimationState animationState;
    private int animationTick;

    private boolean dancingHuevo;
    private BlockPos jukebox;


    private static final int FLAG_ROLL = 4;
    public static final int TOTAL_ROLL_STEPS = 32;
    public int rollCounter;
    private Vec3 rollDelta;
    private float rollAmount;
    private float rollAmountO;

    public Huevo(EntityType<? extends Huevo> entityType, Level level) {
        super(entityType, level);
        this.setTame(false);
        this.xpReward = 35;
        this.effectHandler = new AnimationEffectHandler(this);
        this.animationState = AnimationState.EMPTY;
        this.moveControl = new Huevo.HuevoMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new PanicGoal(this, 2.2D));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(11, new Huevo.HuevoRollGoal(this));
    }

    @Override
    public boolean isImmobile() {
        return !this.isNoAnimationPlaying();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isRolling()) {
            this.handleRoll();
        } else {
            this.rollCounter = 0;
        }

        this.updateRollAmount();
        this.animationTick();
    }

    @Override
    public void setAnimationState(AnimationState state) {
        this.onAnimationStop(this.animationState);
        this.animationState = state;
        this.setAnimationTick(0);
    }

    private void updateRollAmount() {
        this.rollAmountO = this.rollAmount;
        if (this.isRolling()) {
            this.rollAmount = Math.min(1.0F, this.rollAmount + 0.15F);
        } else {
            this.rollAmount = Math.max(0.0F, this.rollAmount - 0.19F);
        }
    }

    public float getRollAmount(float f) {
        return Mth.lerp(f, this.rollAmountO, this.rollAmount);
    }

    private void handleRoll() {
        ++this.rollCounter;
        if (this.rollCounter > 32) {
            this.roll(false);
        } else {
            if (!this.level.isClientSide) {
                Vec3 vec3 = this.getDeltaMovement();
                if (this.rollCounter == 1) {
                    float f = this.getYRot() * 0.017453292F;
                    float g = this.isBaby() ? 0.1F : 0.2F;
                    this.rollDelta = new Vec3(vec3.x + (double)(-Mth.sin(f) * g), 0.0D, vec3.z + (double)(Mth.cos(f) * g));
                    this.setDeltaMovement(this.rollDelta.add(0.0D, 0.27D, 0.0D));
                } else if ((float)this.rollCounter != 7.0F && (float)this.rollCounter != 15.0F && (float)this.rollCounter != 23.0F) {
                    this.setDeltaMovement(this.rollDelta.x, vec3.y, this.rollDelta.z);
                } else {
                    this.setDeltaMovement(0.0D, this.onGround ? 0.27D : vec3.y, 0.0D);
                }
            }

        }
    }

    public boolean isRolling() {
        return this.getFlag(4);
    }

    public boolean isDancing() {
        return this.dancingHuevo;
    }

    public void roll(boolean bl) {
        this.setFlag(4, bl);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_FLAGS, (byte)0);
        this.entityData.define(DATA_CLOTHING_COLOR, DyeColor.RED.getId());
    }

    private boolean getFlag(int i) {
        return (this.entityData.get(DATA_ID_FLAGS) & i) != 0;
    }

    private void setFlag(int i, boolean bl) {
        byte b = this.entityData.get(DATA_ID_FLAGS);
        if (bl) {
            this.entityData.set(DATA_ID_FLAGS, (byte)(b | i));
        } else {
            this.entityData.set(DATA_ID_FLAGS, (byte)(b & ~i));
        }

    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("ClothingColor", (byte)this.getClothingColor().getId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("ClothingColor", NbtConstants.ANY_NUMERIC))
            this.setClothingColor(DyeColor.byId(compound.getInt("ClothingColor")));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.random.nextInt(3) == 0)
            return this.isTame() && this.getHealth() < 10.0F ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
        else
            return SoundEvents.WOLF_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.WOLF_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.85F;
    }

    public boolean canPerformAction() {
        return !this.isRolling();
    }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(18.0D);
            this.setHealth(20.0F);
        } else
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(8.5D);
    }

    @Override
    public void aiStep() {
        if (this.jukebox == null || !this.jukebox.closerThan(this.position(), 3.46D) || !this.level.getBlockState(this.jukebox).is(Blocks.JUKEBOX)) {
            this.dancingHuevo = false;
            this.jukebox = null;
        }

        super.aiStep();
    }

    @Override
    public void setRecordPlayingNearby(BlockPos blockPos, boolean bl) {
        this.jukebox = blockPos;
        this.dancingHuevo = bl;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();
        if (this.level.isClientSide) {
            boolean bl = this.isOwnedBy(player) || this.isTame() || itemStack.is(Items.EGG) && !this.isTame();
            return bl ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else {
            if (this.isTame()) {
                if (itemStack.is(Items.EGG) && this.getHealth() < this.getMaxHealth()) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }

                    this.heal((float) Objects.requireNonNull(item.getFoodProperties()).getNutrition());
                    this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
                    return InteractionResult.SUCCESS;
                }

                if (!(item instanceof DyeItem)) {
                    InteractionResult interactionResult = super.mobInteract(player, hand);
                    if ((!interactionResult.consumesAction() || this.isBaby()) && this.isOwnedBy(player)) {
                        this.setOrderedToSit(!this.isOrderedToSit());
                        this.jumping = false;
                        this.navigation.stop();
                        this.setTarget(null);
                        return InteractionResult.SUCCESS;
                    }

                    return interactionResult;
                }

                DyeColor dyeColor = ((DyeItem)item).getDyeColor();
                if (dyeColor != this.getClothingColor()) {
                    this.setClothingColor(dyeColor);
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }

                    return InteractionResult.SUCCESS;
                }
            } else if (itemStack.is(Items.EGG)) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }

                if (this.random.nextInt(3) == 0) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte)6);
                }

                return InteractionResult.SUCCESS;
            }

            return super.mobInteract(player, hand);
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        AnimatedEntity.setAnimation(this, FALL);
        //TODO: fix this
        return super.hurt(damageSource, f);
    }

    @Nullable
    @Override
    public LivingEntity getLastHurtByMob() {
        return super.getLastHurtByMob();
    }

    public DyeColor getClothingColor() {
        return DyeColor.byId(this.entityData.get(DATA_CLOTHING_COLOR));
    }

    public void setClothingColor(DyeColor clothingColor) {
        this.entityData.set(DATA_CLOTHING_COLOR, clothingColor.getId());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return true;
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    @Override
    public int getAnimationTick() {
        return this.animationTick;
    }

    @Override
    public AnimationState getAnimationState() {
        return this.animationState;
    }

    @Override
    public AnimationEffectHandler getAnimationEffects() {
        return effectHandler;
    }

    @Override
    public void setAnimationTick(int animationTick) {
        this.animationTick = animationTick;
    }


    @Override
    public AnimationState getIdleAnimationState() {
        return IDLE;
    }

    @Override
    public AnimationState[] getAnimationStates() {
        return ANIMATIONS;
    }

    static class HuevoMoveControl extends MoveControl {
        private final Huevo huevo;

        public HuevoMoveControl(Huevo huevo) {
            super(huevo);
            this.huevo = huevo;
        }

        public void tick() {
            if (this.huevo.canPerformAction()) {
                super.tick();
            }
        }
    }

    static class HuevoRollGoal extends Goal {
        private final Huevo huevo;

        public HuevoRollGoal(Huevo huevo) {
            this.huevo = huevo;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        }

        public boolean canUse() {
            if (this.huevo.onGround) {
                if (!this.huevo.canPerformAction()) {
                    return false;
                } else {
                    float f = this.huevo.getYRot() * 0.017453292F;
                    int i = 0;
                    int j = 0;
                    float g = -Mth.sin(f);
                    float h = Mth.cos(f);
                    if ((double)Math.abs(g) > 0.5D) {
                        i = (int)((float)i + g / Math.abs(g));
                    }

                    if ((double)Math.abs(h) > 0.5D) {
                        j = (int)((float)j + h / Math.abs(h));
                    }

                    if (this.huevo.level.getBlockState(this.huevo.blockPosition().offset(i, -1, j)).isAir()) {
                        return true;
                    } else {
                        return this.huevo.random.nextInt(reducedTickDelay(300)) == 1;
                    }
                }
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void start() {
            this.huevo.roll(true);
        }

        public boolean isInterruptable() {
            return false;
        }
    }
}

//TODO: also i think they need custom sounds
// what if they didnt bark
// and instead they
// did some sniffing noises
// and stuff like that
// and when they get hit they would have somewhat the same sound