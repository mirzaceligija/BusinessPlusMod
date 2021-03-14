package com.iamanim0.businessplusmod.core.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage implements INBTSerializable<CompoundNBT> {
	
	public double energy2;

    public CustomEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        
        this.energy2 = this.energy;
    }

    protected void onEnergyChanged() {

    }

    public double getEnergy2() {
		return energy2;
	}
  

    public void setEnergy(double energy) {
        this.energy = (int) energy;
        this.energy2 = energy;
        onEnergyChanged();
    }

    public void addEnergy(double energy) {
        this.energy += energy;
        if (this.energy > getMaxEnergyStored()) {
            this.energy = getEnergyStored();
        }
        
        this.energy2 += energy;
        if (this.energy2 > getMaxEnergyStored()) {
            this.energy2 = getEnergyStored();
        }
        onEnergyChanged();
    }

    public void consumeEnergy(double energy) {
        this.energy -= energy;
        if (this.energy < 0) {
            this.energy = 0;
        }
        
        this.energy2 -= energy;
        if (this.energy2 < 0) {
            this.energy2 = 0.00;
        }
        onEnergyChanged();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("energy", getEnergyStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setEnergy(nbt.getInt("energy"));
    }

}
