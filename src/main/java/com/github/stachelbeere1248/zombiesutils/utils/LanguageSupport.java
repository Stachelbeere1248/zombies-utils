package com.github.stachelbeere1248.zombiesutils.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("SpellCheckingInspection")
public class LanguageSupport {
    private static final EnumMap<Languages, Pattern> scoreboardRound = scoreboardRoundMap();
    private static final EnumMap<Languages, Pattern> scoreboardMap = scoreboardMapMap();

    public static boolean zombies_utils$isLoss(@NotNull String input) {
        final String[] words = {
                "§cGame Over!",
                "§cPartie terminée!",
                "§cDas Spiel ist vorbei!"
        };
        return Arrays.asList(words).contains(input);
    }

    public static boolean zombies_utils$isWin(@NotNull String input) {
        final String[] words = {
                "§aYou Win!",
                "§aVous avez gagné!",
                "§aDu gewinnst!"
        };
        return Arrays.asList(words).contains(input);
    }

    public static boolean containsHard(@NotNull String input) {
        final String[] words = {
                "Hard Difficulty",
                "Difficulté Hard",
                "Hard Schwierigkeitsgrad",
                "困难",
                "困難"
        };
        return Arrays.stream(words).anyMatch(input::contains);
    }

    public static boolean containsRIP(@NotNull String input) {
        final String[] words = {
                "RIP Difficulty",
                "Difficulté RIP",
                "RIP Schwierigkeitsgrad",
                "安息"
        };
        return Arrays.stream(words).anyMatch(input::contains);
    }

    private static @NotNull EnumMap<Languages, Pattern> scoreboardRoundMap() {
        final EnumMap<Languages, Pattern> newMap = new EnumMap<>(Languages.class);
        newMap.put(Languages.EN, Pattern.compile("Round ([0-9]{1,3})"));
        newMap.put(Languages.FR, Pattern.compile("Manche ([0-9]{1,3})"));
        newMap.put(Languages.DE, Pattern.compile("Runde ([0-9]{1,3})"));
        return newMap;
    }

    private static @NotNull EnumMap<Languages, Pattern> scoreboardMapMap() {
        final EnumMap<Languages, Pattern> newMap = new EnumMap<>(Languages.class);
        newMap.put(Languages.EN, Pattern.compile("Map:.*(Dead End|Bad Blood|Alien Arcadium)"));
        newMap.put(Languages.FR, Pattern.compile("Carte:.*(Dead End|Bad Blood|Alien Arcadium)"));
        newMap.put(Languages.DE, Pattern.compile("Karte:.*(Dead End|Bad Blood|Alien Arcadium)"));
        return newMap;
    }

    public static Pattern roundPattern(Languages lang) {
        return scoreboardRound.get(lang);
    }

    public static Pattern mapPattern(Languages lang) {
        return scoreboardMap.get(lang);
    }

    public enum Languages {
        EN,
        FR,
        DE;
        public static String @NotNull [] list() {
            List<String> langs = new ArrayList<>();
            for (Languages language: Languages.values()
                 ) {
                langs.add(language.toString());
            }
            return langs.toArray(new String[1]);
        }
    }
}
