package com.iamanim0.businessplusmod.world;
import com.iamanim0.businessplusmod.BusinessPlusMod;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nullable;

import org.apache.logging.log4j.util.TriConsumer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class AccountWorldSavedData extends WorldSavedData {

    private static final String DATA_NAME = BusinessPlusMod.MOD_ID + "_AccountData";

    private final Map<UUID, PlayerAccount> accounts;
    private final Map<Integer, String> pinCodes;
    private int nextCardID;

    public AccountWorldSavedData() {
        super(DATA_NAME);
        accounts = new HashMap<>();
        pinCodes = new HashMap<>();
        nextCardID = 0;
    }

    public String getPlayerName(UUID player) {
        return accounts.get(player).getPlayerName();
    }

    public int getBalance(UUID player) {
        return accounts.get(player).getBalance();
    }

    public void setBalance(UUID player, int balance) {
        accounts.get(player).setBalance(balance);
        this.markDirty();
    }

    public void deposit(UUID player, int amount) {
        accounts.get(player).deposit(amount);
        this.markDirty();
    }

    public int withdraw(UUID player, int amount) {
        final int withdrawal = accounts.get(player).withdraw(amount);
        this.markDirty();
        return withdrawal;
    }

    public void createAccount(UUID player, String name) {
        if (accounts.containsKey(player))
            return;
        accounts.put(player, new PlayerAccount(name));
        this.markDirty();
    }

    public int getNextCardID() {
        this.markDirty();
        return this.nextCardID++;
    }

    public void setNextCardID(int nextCardID) {
        this.nextCardID = nextCardID;
        this.markDirty();
    }

    public String getCardPIN(int cardNumber) {
        return pinCodes.get(cardNumber);
    }

    public void setCardPIN(int cardNumber, String pinCode) {
        this.pinCodes.put(cardNumber, pinCode);
    }

    private <K, V> void readMap(@Nullable ListNBT listNBT, Map<K, V> map, Function<CompoundNBT, Tuple<K, V>> reader) {
        map.clear();
        if (listNBT == null)
            return;
        listNBT.forEach((nbt) -> {
            Tuple<K, V> entry = reader.apply((CompoundNBT) nbt);
            map.put(entry.getA(), entry.getB());
        });
    }

    private <K, V> ListNBT writeMap(Map<K, V> map, TriConsumer<CompoundNBT, K, V> writer) {
        ListNBT listNBT = new ListNBT();
        map.forEach((key, value) -> {
            CompoundNBT compoundNBT = new CompoundNBT();
            writer.accept(compoundNBT, key, value);
            listNBT.add(compoundNBT);
        });
        return listNBT;
    }

    @Override
    public void read(CompoundNBT nbt) {
        this.nextCardID = nbt.getInt("NextCardID");
        readMap((ListNBT) nbt.get("Accounts"), accounts, (compoundNBT) -> {
            final UUID player = compoundNBT.getUniqueId("Player");
            final String name = compoundNBT.getString("PlayerName");
            final int balance = compoundNBT.getInt("Balance");
            return new Tuple<>(player, new PlayerAccount(name, balance));
        });
        readMap((ListNBT) nbt.get("PinCodes"), pinCodes, (compoundNBT -> {
            final int cardNumber = compoundNBT.getInt("CardNumber");
            final String pinCode = compoundNBT.getString("PIN");
            return new Tuple<>(cardNumber, pinCode);
        }));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("NextCardID", this.nextCardID);
        compound.put("Accounts", writeMap(accounts, (compoundNBT, player, account) -> {
            compoundNBT.putUniqueId("Player", player);
            compoundNBT.putString("PlayerName", account.getPlayerName());
            compoundNBT.putInt("Balance", account.getBalance());
        }));
        compound.put("PinCodes", writeMap(pinCodes, (compoundNBT, cardNumber, pinCode) -> {
            compoundNBT.putInt("CardNumber", cardNumber);
            compoundNBT.putString("PIN", pinCode);
        }));
        return compound;
    }

    public static AccountWorldSavedData get(World world) {
        return get((ServerWorld) world);
    }

    public static AccountWorldSavedData get(ServerWorld world) {
        return world.getSavedData().getOrCreate(AccountWorldSavedData::new, DATA_NAME);
    }

    static class PlayerAccount {

        private String playerName;
        private int balance;

        public PlayerAccount(String playerName, int balance) {
            this.playerName = playerName;
            this.balance = balance;
        }

        public PlayerAccount(String playerName) {
            this(playerName, 50000);
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public void deposit(int amount) {
            this.balance += amount;
        }

        public int withdraw(int amount) {
            if (amount > balance) {
                amount = balance;
                balance = 0;
                return amount;
            }
            balance -= amount;
            return amount;
        }
    }
}
