package edu.utep.cs.cs4330.designpatterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Sample GoF design patterns. */
public class DesignPattern {

    /** Category of GoF design patterns. */
    public enum Category {
        CREATIONAL ("Creational"),
        STRUCTURAL ("Structural"),
        BEHAVIORAL ("Behavioral");

        public final String name;

        Category(String name) {
            this.name = name;
        }
    };

    /** Sample design patterns. */
    private static final List<DesignPattern> patterns;
    static {
        Object[][] data = new Object[][]{
                {"Adapter", Category.STRUCTURAL, "Match interfaces of different classes"},
                {"Composite", Category.STRUCTURAL, "A tree structure of simple and composite objects"},
                {"Factory method", Category.CREATIONAL, "Creates an instance of several derived classes"},
                {"Observer", Category.BEHAVIORAL, "A way of notifying change to a number of classes"},
                {"Singleton", Category.CREATIONAL, "A class of which only a single instance can exist"},
                {"Strategy", Category.BEHAVIORAL, "Encapsulate an algorithm inside a class"},
                {"Template method", Category.BEHAVIORAL, "Defer the exact steps of an algorithm to a subclass"},
        };
        patterns = new ArrayList<>(data.length);
        for (Object[] p: data) {
            patterns.add(new DesignPattern((String) p[0], (Category) p[1], (String) p[2]));
        }
    }

    /** Name of this pattern. */
    public final String name;

    /** Category of this pattern. */
    public final Category category;

    /** Description of this pattern. */
    public final String description;

    /** Create a new design pattern of the given name, category and description. */
    private DesignPattern(String name, Category category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
    }

    /** Return sample GoF design patterns. */
    public static List<DesignPattern> patterns() {
        return Collections.unmodifiableList(patterns);
    }

    /** Find a GoF design pattern that has the given name;
     * return null if no such pattern is found. */
    public static DesignPattern find(String name) {
        for (DesignPattern p: patterns) {
            if (p.name.equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    /** Return the names of sample GoF design patterns. */
    public static List<String> names() {
        List<String> names = new ArrayList<>(patterns.size());
        for (DesignPattern p: patterns) {
            names.add(p.name);
        }
        return names;
    }
}
