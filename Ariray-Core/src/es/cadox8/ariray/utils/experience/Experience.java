package es.cadox8.ariray.utils.experience;

import es.cadox8.ariray.datatype.Formula;

import java.util.Map;

public class Experience {

    public int getXPtoNextLevel(int level, Formula formula) {
        return this.processStandardXPToNextLevel(level, formula);
    }

    private int processStandardXPToNextLevel(int level, Formula formulaType) {
        Map<Integer, Integer> experienceMapRef = formulaType == Formula.LINEAR ? experienceNeededStandardLinear : experienceNeededStandardExponential;

        if(!experienceMapRef.containsKey(level)) {
            int experienceSum = 0;
            int retroIndex = (level * 10) + 1;

            //Sum the range of levels in Retro that this Standard level would represent
            for(int x = retroIndex; x < (retroIndex + 10); x++) {
                //calculateXPNeeded doesn't cache results so we use that instead of invoking the Retro XP methods to avoid memory bloat
                experienceSum += calculateXPNeeded(x, formulaType);
            }

            experienceMapRef.put(level, experienceSum);
        }

        return experienceMapRef.get(level);
    }
}
