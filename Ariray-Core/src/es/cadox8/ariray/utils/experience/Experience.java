package es.cadox8.ariray.utils.experience;

import es.cadox8.ariray.datatype.Formula;

import java.util.Map;

public class Experience {

    public int getXPtoNextLevel(float base, int level, Formula formula) {
        switch (formula) {
            case EXPONENTIAL:

                return 0;
            case LINEAR:

                return 0;

            default:
                return -1;
        }
    }

    public int getLevelFromXP(float xp, Formula formula) {
        switch (formula) {
            case EXPONENTIAL:

                return 0;
            case LINEAR:

                return 0;

            default:
                return -1;
        }
    }
}
