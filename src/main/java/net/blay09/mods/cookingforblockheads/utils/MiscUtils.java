package net.blay09.mods.cookingforblockheads.utils;

import java.util.List;
import java.util.stream.Collectors;

public class MiscUtils {

    public static <T> List<T> castList(List<?> list, Class<T> clazz) {
        return list.stream().map(clazz::cast).collect(Collectors.toList());
    }
}
