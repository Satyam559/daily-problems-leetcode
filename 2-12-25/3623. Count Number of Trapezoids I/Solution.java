import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public int countTrapezoids(int[][] points) {
        long MOD = 1_000_000_007;
        Map<Integer, List<Integer>> pointsByY = new HashMap<>();

        // Group points by y-coordinate
        for (int[] point : points) {
            pointsByY.computeIfAbsent(point[1], k -> new ArrayList<>()).add(point[0]);
        }

        long totalTrapezoids = 0;
        long prevCombinations = 0; // Stores sum of nC2 from previous y-coordinates

        // Sort y-coordinates to ensure consistent order (though not strictly necessary for correctness)
        List<Integer> yCoords = new ArrayList<>(pointsByY.keySet());
        yCoords.sort(null); 

        for (int y : yCoords) {
            List<Integer> xCoords = pointsByY.get(y);
            long numPointsOnLine = xCoords.size();

            if (numPointsOnLine >= 2) {
                // Calculate nC2 for the current line
                long combinationsOnCurrentLine = (numPointsOnLine * (numPointsOnLine - 1) / 2) % MOD;

                // Add trapezoids formed with previous lines
                totalTrapezoids = (totalTrapezoids + (combinationsOnCurrentLine * prevCombinations) % MOD) % MOD;

                // Update prevCombinations for the next iteration
                prevCombinations = (prevCombinations + combinationsOnCurrentLine) % MOD;
            }
        }

        return (int) totalTrapezoids;
    }
}
