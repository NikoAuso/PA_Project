package it.unicam.cs.pa.environment.shapes;

/**
 * Functional interface for checking parameters required to create a shape.
 */
@FunctionalInterface
public interface ShapeChecker {
    /**
     * Checks whether the provided array of strings contains valid parameters for creating a shape.
     *
     * @param args The array of strings representing parameters.
     * @return true if the parameters are valid, false otherwise.
     */
    boolean checkParameters(String[] args);

    /**
     * Default implementation of the ShapeChecker interface.
     * This implementation checks if the number of parameters is correct and if they are of type double.
     */
    ShapeChecker DEFAULT_CHECKER = (args) ->
            (args.length > 2) &&                                                    // Check if args has more than two parameters
                    (((args[1].equals("CIRCLE")) && (args.length == 5)) ||          // Check if args are intended to create a circle shape
                            ((args[1].equals("RECTANGLE")) && (args.length == 6)))  // Check if args are intended to create a rectangle shape
                    && (isDouble(args));                                            // Check if args are double

    /**
     * Checks if the provided array of strings contains double values.
     *
     * @param args The array of strings to check.
     * @return true if all values in the array can be parsed as doubles, false otherwise.
     */
    private static boolean isDouble(String[] args) {
        for (int i = 2; i < args.length; i++) {
            try {
                if (!Double.isFinite(Double.parseDouble(args[i]))) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
