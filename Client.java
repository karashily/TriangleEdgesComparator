import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	public static int PORT = 9091;
	
	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", PORT);
			System.out.println("Connected to Port " + PORT);
			
			ObjectOutputStream socketOut = new ObjectOutputStream(s.getOutputStream());
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			Scanner fileIn = new Scanner(new FileInputStream("points.txt"));
			
			int n = Integer.valueOf(fileIn.nextLine());
			for(int i=0;i<n;i++) {
				fileIn.nextLine();
				
				Point A = new Point(Double.valueOf(fileIn.next()), Double.valueOf(fileIn.next()));
				Point B = new Point(Double.valueOf(fileIn.next()), Double.valueOf(fileIn.next()));
				Point C = new Point(Double.valueOf(fileIn.next()), Double.valueOf(fileIn.next()));
				
				System.out.println("Read Triangle (" + A.getX() + "," + A.getY() + "), (" + B.getX() + "," + B.getY() + "), (" + C.getX() + "," + C.getY() + ")");
				
				Triangle t = new Triangle(A, B, C);
				socketOut.writeObject(t);
				socketOut.flush();
				System.out.println("Triangle Sent");
				
				System.out.println(socketIn.readLine());
				
				System.out.println("Press Enter key to continue...");
		        System.in.read();
			}
			
			socketOut.close();
			socketIn.close();
			fileIn.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static class Point implements Serializable {
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
