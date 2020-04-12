import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;


public class Server {
	
	public static final int PORT = 9091;
	
	public static int clientNumber = 0;
	
	public static void main(String[] args) {
		System.out.println("Server Started at Port " + PORT + "....");
		try {
			ServerSocket ss = new ServerSocket(PORT);
			while (true) {
				System.out.println("here");
				new TriangleEdgesSolver(ss.accept(), clientNumber++).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static class TriangleEdgesSolver extends Thread {
		private Socket clientSocket;
		private int clientNumber;
		
		public TriangleEdgesSolver (Socket s, int cno) {
			clientSocket = s;
			this.clientNumber = cno;
		}
		public void Run() {
			System.out.println("Connected with Client #" + this.clientNumber);
			try {
				PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
				ObjectInputStream in = new ObjectInputStream(this.clientSocket.getInputStream());
				Triangle t = (Triangle) in.readObject();
				
				while (t != null) {
					int edge1 = (int) t.getEdge1Length();
					int edge2 = (int) t.getEdge2Length();
					int edge3 = (int) t.getEdge3Length();
					System.out.println("" + edge1 + " " + edge2 + " " + edge3);
					if(edge1 == edge2 && edge2 == edge3) {
						out.println("Equilateral");
					}
					else if(edge1 == edge2 || edge2 == edge3 || edge1 == edge3) {
						out.println("Isosceles");
					}
					else {
						out.println("Scalene");
					}
					t = (Triangle) in.readObject();
				}
				out.close();
				in.close();
				clientSocket.close();
				System.out.println("Connection with Client #" + this.clientNumber + " finished..");
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class Point {
		private double x;
		private double y;
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		public double getX() { return x; }
		public double getY() { return y; }
	}
	
	public static class Triangle implements Serializable {
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
}
