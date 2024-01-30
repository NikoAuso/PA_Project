package it.unicam.cs.pa.environment.shapes;

import java.util.stream.IntStream;

@FunctionalInterface
public interface ShapeChecker {
    boolean checkParameters(String[] args);

    ShapeChecker DEFAULT_CHECKER = (args) ->
            (args.length > 2) &&                   // check if args as more than two parameters
                    (((args[1].equals("CIRCLE")) && (args.length == 5)) ||                  // check if args are intended to create a circle shape
                            ((args[1].equals("RECTANGLE")) && (args.length == 6)))          // check if args are intended to create a rectangle shape
                    && (IntStream.range(2, args.length).allMatch(i -> isDouble(args[i])));  // check if args are double


    private static boolean isDouble(String str) {
        try {
            return Double.isFinite(Double.parseDouble(str));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
