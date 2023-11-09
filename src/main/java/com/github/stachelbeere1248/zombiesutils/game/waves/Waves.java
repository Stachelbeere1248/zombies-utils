package com.github.stachelbeere1248.zombiesutils.game.waves;

import com.github.stachelbeere1248.zombiesutils.game.Map;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("DuplicatedCode")
public class Waves {
    private static final byte[][] deadEndWaveTimes = {{10,20},{10,20},{10,20,35},{10,20,35},{10,22,37},{10,22,44},{10,25,47},{10,25,50},{10,22,38},{10,24,45},{10,25,48},{10,25,50},{10,25,50},{10,25,45},{10,25,46},{10,24,47},{10,24,47},{10,24,47},{10,24,47},{10,24,49},{10,23,44},{10,23,45},{10,23,42},{10,23,43},{10,23,43},{10,23,36},{10,24,44},{10,24,42},{10,24,42},{10,24,45}},
            badBloodWaveTimes = {{10,22},{10,22},{10,22},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,22,34},{10,24,38},{10,24,38},{10,22,34},{10,24,38},{10,22,34}},
            alienArcadiumWaveTimes = {{10,13,16,19},{10,14,18,22},{10,13,16,19},{10,14,17,21,25,28},{10,14,18,22,26,30},{10,14,19,23,28,32},{10,15,19,23,27,31},{10,15,20,25,30,35},{10,14,19,23,28,32},{10,16,22,27,33,38},{10,16,21,27,32,38},{10,16,22,28,34,40},{10,16,22,28,34,40},{10,16,21,26,31,36},{10,17,24,31,38,46},{10,16,22,27,33,38},{10,14,19,23,28,32},{10,14,19,23,28,32},{10,14,18,22,26,30},{10,15,21,26,31,36},{10,14,19,23,28,32},{10,14,19,23,28,34},{10,14,18,22,26,30},{10,14,19,23,28,32},{10},{10,23,36},{10,22,34},{10,20,30},{10,24,38},{10,22,34},{10,22,34},{10,21,32},{10,22,34},{10,22,34},{10},{10,22,34},{10,20,31},{10,22,34},{10,22,34},{10,22,34,37,45},{10,21,32},{10,22,34},{10,13,22,25,34,37},{10,22,34},{10,22,34,35},{10,21,32,35},{10,20,30},{10,20,30,33},{10,21,32},{10,22,34,37},{10,20,30,33},{10,22,34,37},{10,22,34,37},{10,20,32,35,39},{10,16,22,28,34,40},{10,14,18},{10,14,18},{10,22,34,37,38},{10,14,18,22,26,30},{10,20,30,33},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,27,32},{10,14,18,22,27,32},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{10,14,18,22,26,30},{5},{5},{5},{5},{5}}
    ;
    private static final short[] deadEndRoundTimesSum = {0,20,40,75,110,147,191,238,288,326,371,419,469,519,564,610,657,704,751,798,847,891,936,978,1021,1064,1100,1144,1186,1228,1273},
            badBloodRoundTimesSum = {0,22,44,66,100,134,168,202,236,270,304,338,372,406,440,474,508,542,576,610,644,678,712,746,780,814,852,890,924,962,996},
            alienArcadiumRoundTimesSum = {0,19,41,60,88,118,150,181,216,248,286,324,364,404,440,486,524,556,588,618,654,686,720,750,782,792,828,862,892,930,964,998,1030,1064,1098,1108,1142,1173,1207,1241,1286,1318,1352,1389,1423,1458,1493,1523,1556,1588,1625,1658,1695,1732,1771,1811,1829,1847,1885,1915,1948,1978,2008,2038,2068,2098,2128,2158,2188,2218,2248,2278,2308,2338,2368,2400,2432,2462,2492,2522,2552,2582,2612,2642,2672,2702,2732,2762,2792,2822,2852,2882,2912,2942,2972,3002,3032,3062,3092,3122,3152,3157,3162,3167,3172,3177}
    ;



    public static byte[] get(@NotNull Map map, byte round) {
        byte[] waves;
        switch (map) {
            case DEAD_END:
                waves = deadEndWaveTimes[round - 1];
                break;
            case BAD_BLOOD:
                waves = badBloodWaveTimes[round - 1];
                break;
            case ALIEN_ARCADIUM:
                waves = alienArcadiumWaveTimes[round - 1];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + map);
        }
        return waves;
    }
    public static short getSum(@NotNull Map map, byte round) {
        short sum;
        switch (map) {
            case DEAD_END:
                sum = deadEndRoundTimesSum[round - 1];
                break;
            case BAD_BLOOD:
                sum = badBloodRoundTimesSum[round - 1];
                break;
            case ALIEN_ARCADIUM:
                sum = alienArcadiumRoundTimesSum[round - 1];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + map);
        }
        return sum;
    }
    public static byte getLastWave(@NotNull Map map, byte round) {
        byte[] aByte = get(map, round);
        return aByte[aByte.length-1];
    }

}