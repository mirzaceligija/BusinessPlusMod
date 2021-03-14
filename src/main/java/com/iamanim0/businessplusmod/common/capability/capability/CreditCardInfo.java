package com.iamanim0.businessplusmod.common.capability.capability;

import net.minecraft.world.World;
import java.util.UUID;

import com.iamanim0.businessplusmod.world.AccountWorldSavedData;

public class CreditCardInfo implements ICreditCardInfo {

    private UUID owner = UUID.randomUUID();
    private boolean hasOwner = false;
    private int cardNumber = -1;
    private int attemptsLeft = 3;

    @Override
    public void init(UUID owner, World world) {
        this.owner = owner;
        this.hasOwner = true;
        this.cardNumber = AccountWorldSavedData.get(world).getNextCardID();
    }

    @Override
    public boolean hasOwner() {
        return hasOwner;
    }

    @Override
    public void setHasOwner(boolean hasOwner) {
        this.hasOwner = hasOwner;
    }

    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    @Override
    public int getCardNumber() {
        return this.cardNumber;
    }

    @Override
    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    @Override
    public boolean hasAnyAttemptsLeft() {
        return attemptsLeft > 0;
    }

    @Override
    public void decreaseAttempts() {
        attemptsLeft--;
        if (attemptsLeft < 0)
            attemptsLeft = 0;
    }

    @Override
    public void resetAttempts() {
        attemptsLeft = 3;
    }
}
