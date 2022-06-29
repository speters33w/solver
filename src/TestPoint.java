import solver.Point;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Arrays;
import java.util.Random;

public class TestPoint {

    boolean debugging = false;

    public TestPoint() {
        this(false);
    }

    public TestPoint(boolean debugging) {
        this.debugging = debugging;
    }

    public static void main(String[] args) {
        System.out.println("********** POINT TESTS **********\n");
        Random random = new Random();
        int a;
        int b;
        // If a or b is 0, shape created will be a line.
        // if (a == b || a == -b) this can cause human confusion in test results.
        do {
            a = random.nextInt(19) - 9;
            b = random.nextInt(19) - 9;
        } while (a == 0 || b == 0 || a == b || a == -b);
        System.out.println("a = " + a + ", b = " + b + "\n");
        TestPoint testPoint = new TestPoint();


        testPoint.testConstructors(a, b);
//        testPoint.testSetters(a, b);
//        testPoint.testTranslate(a, b);
//        testPoint.testReflect(a, b);
//        testPoint.testReflectX(a, b);
//        testPoint.testReflectY(a, b);
//        testPoint.testReflectNegative(a, b);
//        testPoint.testClone(a, b);
    }

    /**
     * Adds left padding spaces to a string as No-Break SPaces (\u00A0).
     *
     * @param spaces integer, the number of spaces to add.
     * @return String with the number of NBSPs specified.
     */
    public String padLeft(int spaces) {
        StringBuilder pad = new StringBuilder();
        for (int i = 0; i < spaces; i++) {
            pad.append(" ");
        }
        return pad.toString();
    }

    /**
     * Creates a JFreeChart XYSeries from a Points[] array.
     *
     * @param pointsArray The Points[] array to add to the XYSeries.
     * @param key         The key value to assign to the series.
     *                    This will be displayed at the bottom of the chart with a color key.
     * @return The created XYSeries
     */
    public XYSeries xySeries(Point[] pointsArray, String key) {
        final XYSeries points = new XYSeries(key, false, true);
        for (Point point : pointsArray) {
            points.add(point.getX(), point.getY());
        }
        return points;
    }

    public Point[] createShape(int a, int b, boolean printPoints) {
        // Create a Points[] array shape for the JFreeChart XYChart chart display.
        // Point creation includes reference points.
        Point[] originalPointsArray = {new Point(a, b, new Point(a, b)),
                new Point(2 * a, 2 * b, new Point(2 * a, 2 * b)),
                new Point(4 * a, b, new Point(4 * a, b)),
                new Point(a, b, new Point(a, b))};
        if (printPoints) {
            System.out.println("Point[] points = {new Point(" + a + "," + b + "),");
            System.out.println(padLeft(16) + "new Point(" + (2 * a) + "," + (2 * b) + "),");
            System.out.println(padLeft(16) + "new Point(" + (4 * a) + "," + b + "),");
            System.out.println(padLeft(16) + "new Point(" + a + "," + b + ")};");
            System.out.println("Expect [(" + a + "," + b + "), ("
                    + (2 * a) + "," + (2 * b) + "), ("
                    + (4 * a) + "," + b + "), ("
                    + a + "," + b + ")] \nResult "
                    + Arrays.deepToString(originalPointsArray) + "\n");
        }
        return originalPointsArray;
    }

    /**
     * Tests the Point constructors, as well as getX(), getY(), and getReference().
     *
     * @param a An integer value for x.
     * @param b An integer value for y.
     */
    public void testConstructors(int a, int b) {
        System.out.println("********** TEST CONSTRUCTORS **********\n");
        Point origin = new Point();
        Point point = new Point(a, b);
        Point pointWithReference = new Point(a, b, origin);

        //Test empty constructor
        System.out.println("Point origin = new Point();");
        System.out.println("Expect (0,0) \nResult " + origin + "\n");

        //Test constructor (int x, int y)
        System.out.println("Point point = new Point(" + a + "," + b + ");");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point + "\n");
        System.out.println("point.getReference(); // Reference is null.");
        System.out.println("Expect null \nResult " + point.getReference() + "\n");

        //Test constructor (int x, int y, Point reference)
        System.out.println("Point pointWithReference = new Point(" + a + "," + b + ",origin);");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + pointWithReference);
        System.out.println("Point pointWithReference.getReference();");
        System.out.println("Expect (0,0) \nResult " + pointWithReference.getReference() + "\n");

        //Test getters point.getX() point.getY()
        System.out.println("point.getX(); point.getY();");
        System.out.println("Expect " + a + "," + b + " \nResult " + point.getX() + "," + point.getY() + " \n");

        //Test getDelta(p,q)
        Point p = new Point(a,b);
        Point q = new Point(a+a,b+a);
        Point d1 = p.getDelta(q);
        System.out.println("Point p = new Point(" + a + "," + b + ");");
        System.out.println("Point q = new Point(" + (a + a) + "," + (b + a) + ");");
        System.out.println("Point d1 = p.getDelta(q);");
        System.out.println("Expect (" + a + "," + a + ") \nResult " + d1 + "\n");
        Point d2 = new Point().getDelta(p,q);
        System.out.println("Point d2 = new Point().getDelta(p,q);");
        System.out.println("Expect (" + a + "," + a + ") \nResult " + d2 + "\n");
    }

    /**
     * Tests Point.setLocation(), Point.setX() setY(), and Point.setReference.
     *
     * @param a An integer value for x.
     * @param b An integer value for y.
     */
    public void testSetters(int a, int b) {
        System.out.println("********** TEST SETLOCATION, SETX, SETY SETREFERENCE **********\n");
        Point point = new Point(a, b);
        Point q = new Point(a * 2, b * 2);
        System.out.println("Point point = new Point(" + a + "," + b + ");");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point + "\n");
        System.out.println("Point q = new Point(" + (2 * a) + "," + (2 * b) + ");");
        System.out.println("Expect (" + (2 * a) + "," + (2 * b) + ") \nResult " + q + "\n");
        q.setLocation(point);
        System.out.println("q.setLocation(point);");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + q + "\n");
        point.setLocation(a * 2, b * 2);
        System.out.println("point.setLocation(" + (2 * a) + "," + (2 * b) + ");");
        System.out.println("Expect (" + (2 * a) + "," + (2 * b) + ") \nResult " + point + "\n");
        point.setReference(a, b);
        System.out.println("point.setReference(a,b);");
        System.out.println("Expect [(" + (2 * a) + "," + (2 * b) + "),(" + a + "," + b + ")] \n" +
                "Result " + point.deepToString() + "\n");
        q.setReference(point);
        System.out.println("q.setReference(point);");
        System.out.println("Expect [(" + (2 * a) + "," + (2 * b) + "),(" + a + "," + b + ")] \n" +
                "Result " + point.deepToString() + "\n");
        point.setX(a);
        point.setY(b);
        System.out.println("point.setX(" + a + "); \npoint.setY(" + b + ");");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point + "\n");
    }

    /**
     * Tests translate(), which translates the point along the x and y axes.
     *
     * @param a An integer value for x. Multiples are used for translation value a.
     * @param b An integer value for y. Multiples are used for translation value b.
     */
    public void testTranslate(int a, int b) {
        System.out.println("********** TEST TRANSLATE, MOVE, MOVEBY  **********\n");
        // Perform basic tests and output to the console terminal
        Point point = new Point(a, b, new Point(a, b));
        System.out.println("Point point = new Point(" + a + "," + b + ");");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point + "\n");
        point.translate(a, b);
        System.out.println("point.translate(" + a + "," + b + ");");
        System.out.println("Expect (" + (a + a) + "," + (b + b) + ") \nResult " + point + "\n");
        point.setLocation(a, b); // Resets point to original position
        Point q = new Point(a, b);
        System.out.println("Point q = new Point(" + a + "," + b + ");");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + q + "\n");
        point.translate(q);
        System.out.println("point.translate(q);");
        System.out.println("Expect (" + (a + a) + "," + (b + b) + ") \nResult " + point + "\n");
        point.move(a, b);
        System.out.println("point.move(" + a + "," + b + ");");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point + "\n");
        Point r = point.moveBy(a, b);
        System.out.println("Point r = point.moveBy(" + a + "," + b + ");");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point + " (point)");
        System.out.println("Expect (" + (a + a) + "," + (b + b) + ") \nResult " + r + " (r)\n");
        Point s = point.moveBy(q);
        System.out.println("Point s = point.moveBy(q);");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point + " (point)");
        System.out.println("Expect (" + (a + a) + "," + (b + b) + ") \nResult " + s + " (s)\n");

        // Convert a Points[] array shape to an XYSeries for the JFreeChart XYChart chart display.
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        Point[] originalPointsArray = createShape(a, b, true);
        // Convert the Points[] array to an XYSeriesCollection
        xySeriesCollection.addSeries(xySeries(originalPointsArray,
                "Point[] points = {new Point(" + a + "," + b + "),...}"));

        // Re-create original shape and reflect all points.
        Point[] translatedPointsArray = createShape(a, b, false);
        for (Point translatedPoint : translatedPointsArray) {
            translatedPoint.translate(b, a);
        }
        System.out.println("for(Point point : points){");
        System.out.println(padLeft(4) + "point.translate(" + b + "," + a + ");");
        System.out.println("}");
        System.out.println("Expect [(" + (a + b) + "," + (b + a) + "), ("
                + ((2 * a) + b) + "," + ((2 * b) + a) + "), ("
                + ((4 * a) + b) + "," + (b + a) + "), ("
                + (a + b) + "," + (b + a) + ")] \nResult "
                + Arrays.deepToString(translatedPointsArray) + "\n");
        //Add the translated Points[] array to an XYSeriesCollection
        xySeriesCollection.addSeries(xySeries(translatedPointsArray,
                "for(Point point : points){point.translate(" + b + "," + a + ");}"));

        ChartIt chartIt = new ChartIt(xySeriesCollection);
        chartIt.setWindowTitle("Test Point.translate(a,b)");
        chartIt.chartIt(chartIt);
    }

    /**
     * Tests reflect(), which reflects the point across the line y = x.
     * Point(x,y) will be reflected to Point(y,x).
     * This is useful for navigating 2D arrays arranged in (row,col) format.
     *
     * @param a An integer value for x. Multiples are used for reflection value a.
     * @param b An integer value for y. Multiples are used for reflection value b.
     */
    public void testReflect(int a, int b) {
        System.out.println("********** TEST REFLECT **********\n");
        // Perform basic tests and output to the console terminal
        Point point = new Point(a, b, new Point(a, b));
        System.out.println("Point point = new Point(" + a + "," + b + ");");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point + "\n");
        point.reflect();
        System.out.println("point.reflect();");
        System.out.println("Expect (" + b + "," + a + ") \nResult " + point + "\n");
        System.out.println("point.getReference();");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point.getReference() + "\n");

        // Convert a Points[] array shape to an XYSeries for the JFreeChart XYChart chart display.
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        Point[] originalPointsArray = createShape(a, b, true);
        // Convert the Points[] array to an XYSeriesCollection
        xySeriesCollection.addSeries(xySeries(originalPointsArray,
                "Point[] points = {new Point(" + a + "," + b + "),...}"));

        // Re-create original shape and reflect all points.
        Point[] reflectedPointsArray = createShape(a, b, false);
        for (Point reflectedPoint : reflectedPointsArray) {
            reflectedPoint.reflect();
        }
        System.out.println("for(Point point : pointsArray){");
        System.out.println(padLeft(4) + "point.reflect();");
        System.out.println("}");
        System.out.println("Expect [(" + b + "," + a + "), ("
                + (2 * b) + "," + (2 * a) + "), ("
                + b + "," + (4 * a) + "), ("
                + b + "," + a + ")] \nResult "
                + Arrays.deepToString(reflectedPointsArray) + "\n");
        //Add the reflected Points[] array to an XYSeriesCollection
        xySeriesCollection.addSeries(xySeries(reflectedPointsArray,
                "for(Point point : points){point.reflect();}"));

        ChartIt chartIt = new ChartIt(xySeriesCollection);
        chartIt.setWindowTitle("Test Point.reflect()");
        chartIt.chartIt(chartIt);
    }

    /**
     * Tests reflectX(), which reflects the point across the x axis.
     *
     * @param a An integer value for x. Multiples are used for reflection value a.
     * @param b An integer value for y. Multiples are used for reflection value b.
     */
    public void testReflectX(int a, int b) {
        System.out.println("********** TEST REFLECTX **********\n");
        // Perform basic tests and output to the console terminal
        Point point = new Point(a, b, new Point(a, b));
        System.out.println("Point point = new Point(" + a + "," + b + ");");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point + "\n");
        point.reflectX();
        System.out.println("point.reflectX();");
        System.out.println("Expect (" + a + "," + -b + ") \nResult " + point + "\n");
        System.out.println("point.getReference();");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point.getReference() + "\n");

        // Convert a Points[] array shape to an XYSeries for the JFreeChart XYChart chart display.
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        Point[] originalPointsArray = createShape(a, b, true);
        // Convert the Points[] array to an XYSeriesCollection
        xySeriesCollection.addSeries(xySeries(originalPointsArray,
                "Point[] points = {new Point(" + a + "," + b + "),...}"));

        // Re-create original shape and reflect all points.
        // originalPointsArray.clone() doesn't seem to make a true clone.
        Point[] reflectedPointsArray = createShape(a, b, false);
        for (Point reflectedPoint : reflectedPointsArray) {
            reflectedPoint.reflectX();
        }
        System.out.println("for(Point point : pointsArray){");
        System.out.println(padLeft(4) + "point.reflectX();");
        System.out.println("}");
        System.out.println("Expect [(" + a + "," + -b + "), ("
                + (2 * a) + "," + (2 * -b) + "), ("
                + (4 * a) + "," + -b + "), ("
                + a + "," + -b + ")] \nResult "
                + Arrays.deepToString(reflectedPointsArray) + "\n");
        //Add the reflected Points[] array to an XYSeriesCollection
        xySeriesCollection.addSeries(xySeries(reflectedPointsArray,
                "for(Point point : points){point.reflectX();}"));

        // Re-create original shape and reflect all points.
        reflectedPointsArray = createShape(a, b, false);
        for (Point reflectedPoint : reflectedPointsArray) {
            reflectedPoint.reflectX(a - b);
        }
        System.out.println("for(Point point : pointsArray){");
        System.out.println(padLeft(4) + "point.reflectX(" + (a - b) + ");");
        System.out.println("}");
        // https://www.dcode.fr/math-simplification
        System.out.println("Expect [(" + a + "," + ((2 * a) - (3 * b)) + "), ("
                + (2 * a) + "," + (2 * (a - (2 * b))) + "), ("
                + (4 * a) + "," + ((2 * a) - (3 * b)) + "), ("
                + a + "," + ((2 * a) - (3 * b)) + ")] \nResult "
                + Arrays.deepToString(reflectedPointsArray) + "\n");
        //Add the reflected Points[] array to an XYSeriesCollection
        xySeriesCollection.addSeries(xySeries(reflectedPointsArray,
                "for(Point point : points){point.reflectX(" + (a - b) + ");}"));

        ChartIt chartIt = new ChartIt(xySeriesCollection);
        chartIt.setWindowTitle("Test Point.reflectX()");
        chartIt.chartIt(chartIt);
    }

    /**
     * Tests reflectY(), which reflects the point across the y axis.
     *
     * @param a An integer value for x. Multiples are used for reflection value a.
     * @param b An integer value for y. Multiples are used for reflection value b.
     */
    public void testReflectY(int a, int b) {
        System.out.println("********** TEST REFLECTY **********\n");
        // Perform basic tests and output to the console terminal
        Point point = new Point(a, b, new Point(a, b));
        System.out.println("Point point = new Point(" + a + "," + b + ");");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point + "\n");
        point.reflectY();
        System.out.println("point.reflectY();");
        System.out.println("Expect (" + -a + "," + b + ") \nResult " + point + "\n");
        System.out.println("point.getReference();");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point.getReference() + "\n");

        // Convert a Points[] array shape to an XYSeries for the JFreeChart XYChart chart display.
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        Point[] originalPointsArray = createShape(a, b, true);
        // Convert the Points[] array to an XYSeriesCollection
        xySeriesCollection.addSeries(xySeries(originalPointsArray,
                "Point[] points = {new Point(" + a + "," + b + "),...}"));

        // Re-create original shape and reflect all points.
        Point[] reflectedPointsArray = createShape(a, b, false);
        for (Point reflectedPoint : reflectedPointsArray) {
            reflectedPoint.reflectY();
        }
        System.out.println("for(Point point : pointsArray){");
        System.out.println(padLeft(4) + "point.reflectY();");
        System.out.println("}");
        System.out.println("Expect [(" + -a + "," + b + "), ("
                + -(2 * a) + "," + (2 * b) + "), ("
                + -(4 * a) + "," + b + "), ("
                + -a + "," + b + ")] \nResult "
                + Arrays.deepToString(reflectedPointsArray) + "\n");
        //Add the reflected Points[] array to an XYSeriesCollection
        xySeriesCollection.addSeries(xySeries(reflectedPointsArray,
                "for(Point point : points){point.reflectY();}"));

        // Re-create original shape and reflect all points.
        reflectedPointsArray = createShape(a, b, false);
        for (Point reflectedPoint : reflectedPointsArray) {
            reflectedPoint.reflectY(a - b);
        }
        System.out.println("for(Point point : pointsArray){");
        System.out.println(padLeft(4) + "point.reflectY(" + (a - b) + ");");
        System.out.println("}");
        //(2 * a)+(2 * (a-b))
        // https://www.dcode.fr/math-simplification
        System.out.println("Expect [(" + (a - (2 * b)) + "," + b + "), ("
                + -(2 * b) + "," + (2 * b) + "), ("
                + (-2 * (a + b)) + "," + b + "), ("
                + (a - (2 * b)) + "," + b + ")] \nResult "
                + Arrays.deepToString(reflectedPointsArray) + "\n");
        //Add the reflected Points[] array to an XYSeriesCollection
        xySeriesCollection.addSeries(xySeries(reflectedPointsArray,
                "for(Point point : points){point.reflectY(" + (a - b) + ");}"));

        ChartIt chartIt = new ChartIt(xySeriesCollection);
        chartIt.setWindowTitle("Test Point.reflectY()");
        chartIt.chartIt(chartIt);
    }

    /**
     * Tests reflectNegative(), which reflects the point across the line -y = x.
     *
     * @param a An integer value for x. Multiples are used for reflection value a.
     * @param b An integer value for y. Multiples are used for reflection value b.
     */
    public void testReflectNegative(int a, int b) {
        System.out.println("********** TEST REFLECTNEGATIVE **********\n");
        // Perform basic tests and output to the console terminal
        Point point = new Point(a, b, new Point(a, b));
        System.out.println("Point point = new Point(" + a + "," + b + ");");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point + "\n");
        point.reflectNegative();
        System.out.println("point.reflectNegative();");
        System.out.println("Expect (" + -b + "," + -a + ") \nResult " + point + "\n");
        System.out.println("point.getReference();");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point.getReference() + "\n");

        // Convert a Points[] array shape to an XYSeries for the JFreeChart XYChart chart display.
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        Point[] originalPointsArray = createShape(a, b, true);
        // Convert the Points[] array to an XYSeriesCollection
        xySeriesCollection.addSeries(xySeries(originalPointsArray,
                "Point[] points = {new Point(" + a + "," + b + "),...}"));

        // Re-create original shape and reflect all points.
        Point[] reflectedPointsArray = createShape(a, b, false);
        for (Point reflectedPoint : reflectedPointsArray) {
            reflectedPoint.reflectNegative();
        }
        System.out.println("for(Point point : pointsArray){");
        System.out.println(padLeft(4) + "point.reflectNegative();");
        System.out.println("}");
        System.out.println("Expect [(" + -b + "," + -a + "), ("
                + -(2 * b) + "," + -(2 * a) + "), ("
                + -b + "," + -(4 * b) + "), ("
                + -b + "," + -a + ")] \nResult "
                + Arrays.deepToString(reflectedPointsArray) + "\n");
        //Add the reflected Points[] array to an XYSeriesCollection
        xySeriesCollection.addSeries(xySeries(reflectedPointsArray,
                "for(Point point : points){point.reflectNegative();}"));

        ChartIt chartIt = new ChartIt(xySeriesCollection);
        chartIt.setWindowTitle("Test Point.reflectNegative()");
        chartIt.chartIt(chartIt);
    }

    /**
     * Tests clone and equals overriding methods.
     *
     * @param a An integer value for x.
     * @param b An integer value for y.
     */
    public void testClone(int a, int b) {
        System.out.println("********** TEST CLONE, EQUALS **********\n");
        // Create a new Point with reference (origin).
        Point point = new Point(a, b, new Point());
        System.out.println("Point point = new Point(" + a + ", " + b + ", new Point());");
        System.out.println("Expect (" + a + "," + b + ") \nResult " + point);
        System.out.println("point.getReference();");
        System.out.println("Expect (0,0) \nResult " + point.getReference() + "\n");
        // Create a clone of point.
        Point clone = (Point) point.clone();
        System.out.println("Point clone = (Point) point.clone();");
        System.out.println("Expect (" + a + "," + b + ")");
        System.out.println("Result " + clone);
        System.out.println("clone.getReference();");
        System.out.println("Expect (0,0) \nResult " + clone.getReference() + "\n");
        // Translate the original point
        point.translate(a, b);
        System.out.println("point.translate(" + a + "," + b + ");");
        System.out.println("Expect (" + (a + a) + "," + (b + b) + ") \nResult " + point);
        System.out.println("point.getReference();");
        System.out.println("Expect (0,0) \nResult " + point.getReference() + "\n");
        clone.translate(-b, -a);
        System.out.println("clone.translate(" + -b + "," + -a + ");");
        System.out.println("Expect (" + (a - b) + "," + (b - a) + ") \nResult " + clone);
        System.out.println("clone.getReference();");
        System.out.println("Expect (0,0) \nResult " + clone.getReference() + "\n");

        // Test Point.equals(anotherPoint), including Point.reference.
        System.out.println("Test Point.equals(anotherPoint). \nExpect true, false, true, false, true.");
        Point point1 = new Point(a, b);
        Point point2 = new Point(a, b, new Point(a + 2, b + 2));
        Point point3 = new Point(a + 2, b + 2);
        point3.translate(-2, -2);
        System.out.println(point3.equals(point1));
        System.out.println(point3.equals(point2));
        point3.setReference(a + 2, b + 2);
        System.out.println(point3.equals(point2));
        point2.setReference(a, b);
        System.out.println(point3.equals(point2));
        point1.translate(-2, -2);
        point1.setReference(a + 2, b + 2);
        point3.translate(-2, -2);
        System.out.println(point3.equals(point1));
    }
}
