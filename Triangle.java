import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.io.Serializable;


public class Triangle implements Serializable {
	private Point A;
	private Point B;
	private Point C;
	public Triangle(Point ptA, Point ptB, Point ptC) {
		A = ptA;
		B = ptB;
		C = ptC;
	}
	public Point getA() { return A; }
	public Point getB() { return B; }
	public Point getC() { return C; }
	public double getEdge1Length() {
		return sqrt(pow(A.getX() - B.getX(), (double)2) + pow(A.getY() - B.getY(), (double)2));
	}
	public double getEdge2Length() {
		return sqrt(pow(C.getX() - B.getX(), (double)2) + pow(C.getY() - B.getY(), (double)2));
	}
	public double getEdge3Length() {
		return sqrt(pow(A.getX() - C.getX(), (double)2) + pow(A.getY() - C.getY(), (double)2));
	}

}
