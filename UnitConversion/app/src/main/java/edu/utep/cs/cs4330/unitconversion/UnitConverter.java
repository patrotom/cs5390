package edu.utep.cs.cs4330.unitconversion;

public class UnitConverter {

    public enum Mode {
        FEET_TO_METER ("Feet", "Meters", 0.3048),
        INCH_TO_CENTIMETER ("Inches", "Centimeters", 2.56),
        POUND_TO_GRAM ("Pounds", "Grams", 453.592);

        private final String fromUnit;
        private final String toUnit;
        private final double rate;

        Mode(String fromUnit, String toUnit, double rate) {
            this.fromUnit = fromUnit;
            this.toUnit = toUnit;
            this.rate = rate;
        }
    };

    /** Current conversion mode specifying the input/output units. */
    private Mode mode;

    public UnitConverter() {
        mode = Mode.FEET_TO_METER;
    }

    /** Change the conversion mode. */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /** Convert a value measured in the current input units to the output units. */
    public double convert(Double x) {
        return x * mode.rate;
    }

    /** Return the input unit. E.g., "Feet". */
    public String fromUnit() {
        return mode.fromUnit;
    }

    /** Return the output unit. E.g., "Meters". */
    public String toUnit() {
        return mode.toUnit;
    }
}
