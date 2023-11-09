package com.github.stachelbeere1248.zombiesutils.game.sla;

import com.github.stachelbeere1248.zombiesutils.game.Map;

@SuppressWarnings("SpellCheckingInspection")
public class QuickSLA {
    public static void mogi_a() {
        SLA.instance = new SLA(Map.BAD_BLOOD);
        SLA.instance.rotate(3);
        SLA.instance.setOffset(new int[]{-3,35,-9});
    }
    public static void ghxula() {
        SLA.instance = new SLA(Map.DEAD_END);
        //TODO
    }
    public static void ghxulaGarden() {
        SLA.instance = new SLA(Map.DEAD_END);
        //TODO
    }
}
