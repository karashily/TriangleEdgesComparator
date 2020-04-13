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
	public static int PORT = 6012;
	
	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", PORT);
			System.out.println("Connected to Port " + PORT);
			
			Scanner fileIn = new Scanner(new FileInputStream("points.txt"));
			
			int n = Integer.valueOf(fileIn.nextLine());
			Triangle[] triangles = new Triangle[n];
			for(int i=0;i<n;i++) {
				fileIn.nextLine();
				
				Point A = new Point(Double.valueOf(fileIn.next()), Double.valueOf(fileIn.next()));
				Point B = new Point(Double.valueOf(fileIn.next()), Double.valueOf(fileIn.next()));
				Point C = new Point(Double.valueOf(fileIn.next()), Double.valueOf(fileIn.next()));
				
				
				Triangle t = new Triangle(A, B, C);
				triangles[i] = t;
				System.out.println("Read Triangle (" + A.getX() + "," + A.getY() + "), (" + B.getX() + "," + B.getY() + "), (" + C.getX() + "," + C.getY() + ")");
					
				fileIn.nextLine();
			}
			fileIn.close();
			
			ObjectOutputStream socketOut = new ObjectOutputStream(s.getOutputStream());
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			for(int i=0;i<n;i++) {
				socketOut.writeObject(triangles[i]);
				socketOut.flush();
				System.out.println("Triangle " + i + " Sent");
				
				System.out.println(socketIn.readLine());
				
				System.out.println("Press Enter key to continue...");
		        System.in.read();
			}
			
			socketOut.close();
			socketIn.close();
			
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}
