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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

// TODO Make huevo dance, add custom sounds, etc.

/**
 * @author Steven
 */
public class Huevo extends TamableAnimal implements AnimatedEntity, PollenEntity {
    public static final AnimationState WALK = new AnimationState(20, new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.walk"));
    public static final AnimationState IDLE = new AnimationState(40, new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.idle"));
    public static final AnimationState FALL = new AnimationState(45, new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.fall"));
    public static final AnimationState DANCE = new AnimationState(40, new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.dance"));
    private static final AnimationState[] ANIMATIONS = Stream.of(WALK, IDLE, FALL, DANCE).toArray(AnimationState[]::new);

    private static final EntityDataAccessor<Integer> DATA_CLOTHING_COLOR = SynchedEntityData.defineId(Huevo.class, EntityDataSerializers.INT);

    private final AnimationEffectHandler effectHandler;
    private AnimationState animationState;
    private int animationTick;

    private boolean dancingHuevo;
    private BlockPos jukebox;

    public Huevo(EntityType<? extends Huevo> entityType, Level level) {
        super(entityType, level);
        this.setTame(false);
        this.xpReward = 35;
        this.effectHandler = new AnimationEffectHandler(this);
        this.animationState = AnimationState.EMPTY;
    }

    @Override
    protected void registerGoals() {// TODO: Roll Around Goal (aka panda)
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
//        this.targetSelector.addGoal(1, (new Huevo.HuevoHurtByTargetGoal(this)).setAlertOthers());
    }

    @Override
    public boolean isImmobile() {
        return !this.isNoAnimationPlaying();
    }

    @Override
    public void tick() {
        super.tick();
        this.animationTick();
    }

    @Override
    public void setAnimationState(AnimationState state) {
        this.onAnimationStop(this.animationState);
        this.animationState = state;
        this.setAnimationTick(0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CLOTHING_COLOR, DyeColor.RED.getId());
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

    public boolean isHuevoDancing() {
        return this.dancingHuevo;
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
}