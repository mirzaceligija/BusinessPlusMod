package com.iamanim0.businessplusmod.common.containers;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import java.util.Objects;

public class ContainerHelper {

    public static <T extends Container> String getUnlocalizedText(ContainerType<T> containerType) {
        return String.format("container.%s.%s",
                Objects.requireNonNull(containerType.getRegistryName()).getNamespace(),
                containerType.getRegistryName().getPath()
        );
    }

    public static String getUnlocalizedText(String text) {
        return String.format("container.%s.%s", BusinessPlusMod.MOD_ID, text);
    }
}
