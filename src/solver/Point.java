package solver;

import java.io.Serializable;

/**
 * <p>
 * A Cartesian Coordinate point in integer resolution represented by a pair of numeric coordinates {@code (x,y)}.
 * The Point object can contain a separate reference point, reference.
 * </p><p>
 * A Cartesian coordinate system in two dimensions is defined by an ordered pair of perpendicular lines (axes),
 * a single unit of length for both axes, and an orientation for each axis.
 * The point where the axes meet is taken as the origin for both, thus turning each axis into a number line.
 * For any point {@code P}, a line is drawn through {@code P} perpendicular to each axis,
 * and the position where it meets the axis is interpreted as a number.
 * The two numbers, in that chosen order, are the Cartesian coordinates of {@code P}.
 * The reverse construction allows one to determine the point {@code P} given its coordinates.
 * </p><p>
 * The first and second coordinates are called the abscissa and the ordinate of {@code P}, respectively;
 * and the point where the axes meet is called the origin of the coordinate system.
 * The coordinates are usually written as two numbers in parentheses, in that order, separated by a comma,
 * as in {@code (3, −10)}.
 * Thus, the origin has coordinates {@code (0, 0)}, and the points on the positive half-axes,
 * one unit away from the origin, have coordinates {@code (1, 0)} and {@code (0, 1)}.
 * </p><p>
 * A Euclidean plane with a chosen Cartesian coordinate system is called a Cartesian plane.
 * </p><p>
 * If the coordinates of a point are {@code (x, y)},
 * then its distances from the x-axis and from the y-axis are {@code |y|} and {@code |x|}, respectively;
 * where {@code |n|} denotes the absolute value of a number.
 * </p>
 *
 * @author StephanPeters (speters33w)
 * @version 20220704.1100
 */
public class Point implements Cloneable, Serializable {
    /**
     * The integer {@code (x)} abscissa of this Point.
     */
    int x;

    /**
     * The integer {@code (y)} ordinate of this Point.
     */
    int y;

    /**
     * A retrievable reference Point.
     */
    Point reference;

    /**
     * Constructs and initializes a Point at the specified {@code (x,y)} location in the plane.
     *
     * @param x – the {@code x} abscissa of the newly constructed Point.
     * @param y – the {@code y} ordinate of the newly constructed Point.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.reference = null;
    }

    /**
     * Constructs and initializes a Point at the origin {@code (0,0)} of the plane.
     */
    public Point() {
        this(0, 0);
    }

    /**
     * Constructs and initializes a Point at the specified {@code (x,y)} location in the plane and stores a reference coordinate (reference).
     *
     * @param x         – the {@code x} abscissa of the newly constructed Point.
     * @param y         – the {@code y} ordinate of the newly constructed Point.
     * @param reference - a retrievable reference Point, designed for Breadth First Traversal search.
     */
    public Point(int x, int y, Point reference) {
        this(x, y);
        this.reference = reference;
    }

    /**
     * Returns the {@code x} abscissa of the Point as an integer.
     *
     * @return the {@code x} abscissa of this Point.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the {@code y} ordinate of this Point.
     *
     * @return the {@code y} ordinate of this Point.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the reference Point.
     *
     * @return reference Point.
     */
    public Point getReference() {
        return reference;
    }

    /**
     * Sets the location of the Point to the location of another Point.
     *
     * @param p a Point sharing new location for this Point.
     */
    public void setLocation(Point p) {
        setLocation(p.x, p.y);
    }

    /**
     * Changes the Point to have the specified location.
     *
     * @param x an integer, the new {@code x} abscissa for the Point.
     * @param y an integer, the new {@code y} ordinate for the Point.
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Changes the abscissa of the Point to a specified location along the x plane.
     *
     * @param x an integer, the new {@code x} abscissa for the Point.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Changes the ordinate of the Point to a specified location along the y plane.
     *
     * @param y an integer, the new {@code y} ordinate for the Point.
     */
    public void setY(int y) {
        this.y = y;
    }

    public void setReference(int x, int y) {
        this.reference = new Point(x, y);
    }

    public void setReference(Point reference) {
        this.reference = reference;
    }

    /**
     * Identical to setLocation.
     * Moves this Point to the specified location in the
     * {@code (x,y)} coordinate plane.
     *
     * @param x an integer, the new {@code x} abscissa for the Point.
     * @param y an integer, the new {@code y} ordinate for the Point.
     */
    public void move(int x, int y) {
        setLocation(x, y);
    }

    /**
     * Translates this Point, at location {@code (x,y)}, by {@code a} along the x-axis
     * and {@code b} along the y-axis
     * so that it now represents the Point {@code (x+a,y+b)}.
     *
     * @param a - the distance to move this Point along the x-axis.
     * @param b - the distance to move this Point along the y-axis.
     */
    public void translate(int a, int b) {
        this.x += a;
        this.y += b;
    }

    /**
     * Translates this Point, at location {@code (x,y)}, by {@code delta(x,y)}
     * along both axes so that it now represents the Point {@code (x+delta.getX(),y+delta.getY()}.
     *
     * @param delta Point to translate this Point with.
     */
    public void translate(Point delta) {
        this.x += delta.getX();
        this.y += delta.getY();
    }

    /**
     * Returns a new Point, offset from location {@code (x,y)},
     * by {@code deltaX} along the x-axis and by {@code deltaY} along the y-axis,
     * so it is at location {@code (x+deltaX,y+deltaY)}.
     *
     * @param deltaX - the distance from the Point along the x-axis
     *               where the new Point's abscissa will be.
     * @param deltaY - the distance from the Point along the y-axis
     *               where the new Point's ordinate will be.
     * @return A new Point moved to new location
     */
    public Point moveBy(int deltaX, int deltaY) {
        return new Point(x + deltaX, y + deltaY);
    }

    /**
     * Returns a new Point, offset from location {@code (x,y)},
     * by the x and y values of the delta Point provided,
     * so it is at location {@code (x + delta.getX(),y + delta.getY)}
     * <p>
     * May be used with an enum or method of directions or edges.
     *
     * @param delta - Point with the {@code (x,y)} used for the distances to translate.
     * @return A new Point moved to new location
     */
    public Point moveBy(Point delta) {
        return new Point(x + delta.x, y + delta.y);
    }

    /**
     * Reflects of the Point {@code (x,y)} across the line {@code y = x},
     * reversing the {@code (x,y)} coordinate values to {@code (y,x)}.
     */
    public void reflect() {
        int abscissa = x;
        int ordinate = y;
        this.x = ordinate;
        this.y = abscissa;
    }

    /**
     * Reflects of the Point {@code (x,y)} across the line {@code y = x + a},
     * changing the coordinate values to {@code (y-a,x+a)}.
     */
    public void reflect(int a) {
        int abscissa = x;
        int ordinate = y;
        this.x = ordinate - a;
        this.y = abscissa + a;
    }

    /**
     * Reflects of the Point {@code (x,y)} across the x-axis,
     * transforming the {@code (x,y)} coordinate values to {@code (x,-y)}.
     */
    public void reflectX() {
        this.y = -y;
    }

    /**
     * Reflects of the Point {@code (x,y)} across a horizontal line,
     * transforming the {@code y} coordinate across the line equidistant.
     *
     * @param b - integer, the {@code y} location of the horizontal line.
     */
    public void reflectX(int b) {
        this.reflectX();
        this.translate(0, (2 * b));
    }

    /**
     * Reflects of the Point {@code (x,y)} across the y-axis,
     * transforming the {@code (x,y)} coordinate values to {@code (-x,y)}.
     */
    public void reflectY() {
        this.x = -x;
    }

    /**
     * Reflects of the Point {@code (x,y)} across a vertical line,
     * transforming the {@code x} coordinate across the line equidistant.
     *
     * @param a - integer, the {@code x} location of the vertical line.
     */
    public void reflectY(int a) {
        this.reflectY();
        this.translate((2 * a), 0);
    }

    /**
     * Reflects of the Point {@code (x,y)} across the line {@code y=x},
     * reversing the {@code (x,y)} coordinate values to {@code (y,x)},
     * then translates the coordinates to {@code (y+h,x+k)}
     */
    public void glide(int h, int k) {
        this.reflect();
        this.translate(h,k);
    }

    //todo write for rotation around a point not origin.
    /**
     * Rotates the Point {@code (x,y)} by angle theta (in degrees) centered on the origin {@code (0,0))},
     * using the formula:
     * <pre>
     * x' = x cos θ + y sin θ
     * y' = -x sin θ + y cos θ.
     * </pre>
     *
     * @param angdeg double, the angle to rotate the Point.
     */
    public void rotate(double angdeg){
        double theta = Math.toRadians(angdeg);
        int xr = Math.toIntExact(Math.round(( x * Math.cos(theta)) + (y * Math.sin(theta))));
        this.y = Math.toIntExact(Math.round((-x * Math.sin(theta)) + (y * Math.cos(theta))));
        this.x = xr;
    }

    /**
     * Create a new Point with the same values as this Point.
     *
     * @return - a new Point with the same values as this Point
     * @see Cloneable
     */
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError("Error in Cloneable chain.");
        }
    }

    /**
     * Determines if two Points are equal. Two instances of
     * <code>Point</code> are equal if the values of their
     * {@code x}, {@code y} and {@code reference} member fields are the same.
     *
     * @param obj - a Point to be compared with this {@code Point}.
     * @return {@code true} if the object to be compared is a{@code Point},
     * and has the same values; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point point = (Point) obj;
            if (reference == null && point.reference == null) {
                return (x == point.x) && (y == point.y);
            } else if (reference != null && point.reference != null) {
                return (x == point.x) && (y == point.y)
                        && (reference.x == point.reference.x) && (reference.y == point.reference.y);
            }
        }
        return super.equals(obj);
    }

    /**
     * Returns the {@code (x,y)} difference between a Point and a second Point.
     *
     * @param q - The Point to compare the first Point to.
     * @return - A Point of the {@code (x,y)} difference.
     */
    public Point getDelta(Point q) {
        return new Point(q.x - this.x, q.y - this.y);
    }

    /**
     * Returns the {@code (x,y)} difference between a Point and a second Point.
     *
     * @param p - The Point to compare.
     * @param q - The Point to compare the first Point to.
     * @return - A Point of the {@code (x,y)} difference.
     */
    public Point getDelta(Point p, Point q) {
        return new Point(q.x - p.x, q.y - p.y);
    }

    /**
     * Calculate and return the Euclidean distance from this Point to another Point.
     *
     * @param q - the other Point to which distance is calculated.
     * @return - the distance from this Point to the other Point in double precision.
     */
    public double distance(Point q) {
        int dx = x - q.x;
        int dy = y - q.y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * Calculate and return the Euclidean distance from this Point
     * to the {@code (x,y)} values of a second coordinate.
     *
     * @param x2 - the {@code x} value of the second Point
     * @param y2 - the {@code y} value of the second Point
     * @return - the distance between this Point and the second coordinate location in double precision
     */
    public double distance(int x2, int y2) {
        int dx = x - x2;
        int dy = y - y2;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * Calculate and return the Euclidean distance from the {@code (x,y)} values of one coordinate
     * to the {@code (x,y)} values of a second coordinate.
     *
     * @param x1 - the {@code x} value of the first Point
     * @param y1 - the {@code y} value of the first Point
     * @param x2 - the {@code x} value of the second Point
     * @param y2 - the {@code y} value of the second Point
     * @return - the distance between the two coordinate locations in double precision
     */
    public static double distance(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * Calculate and return the square of the distance from this Point to a second Point.
     *
     * @param q - the other Point to which distance is calculated.
     * @return - the square of the distance between the two Points in double precision
     */
    public double distanceSq(Point q) {
        int dx = x - q.x;
        int dy = y - q.y;
        return (dx * dx) + (dy * dy);
    }

    /**
     * Calculate and return the square of the distance from this Point
     * to the {@code (x,y)} values of a second coordinate.
     *
     * @param x2 - the {@code x} value of the second Point
     * @param y2 - the {@code y} value of the second Point
     * @return - the distance between the two coordinate locations in double precision
     */
    public double distanceSq(int x2, int y2) {
        int dx = x - x2;
        int dy = y - y2;
        return (dx * dx) + (dy * dy);
    }

    /**
     * Calculate and return the square of the distance from the {@code (x,y)} values of one coordinate
     * to the {@code (x,y)} values of a second coordinate.
     *
     * @param x1 - the {@code x} value of the first Point
     * @param y1 - the {@code y} value of the first Point
     * @param x2 - the {@code x} value of the second Point
     * @param y2 - the {@code y} value of the second Point
     * @return - the distance between the two coordinate locations in double precision
     */
    public static double distanceSq(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return (dx * dx) + (dy * dy);
    }

    /**
     * Returns a string representation of this Point and its location
     * in the {@code (x,y)} coordinate plane.
     *
     * @return a string representation of this Point in format {@code (x,y)}.
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * Returns a string representation of this Point and reference Point locations
     * in the {@code (x,y)} coordinate plane.
     *
     * @return a string representation of this Point in format {@code [(x,y),(a,b)]}
     * where {@code (a,b)} are the coordinates of the point's reference point.
     */
    public String deepToString() {
        if (this.reference != null) {
            return "[" + this + ", (" + this.reference.x + "," + this.reference.y + ")]";
        } else {
            return "[" + this + ", (null)]";
        }
    }
}
