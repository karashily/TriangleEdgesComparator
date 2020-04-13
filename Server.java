import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;


public class Server {
	
	public static final int PORT = 6012;
	
	public static int clientNumber = 0;
	
	public static void main(String[] args) {
		System.out.println("Server Started at Port " + PORT + "....");
		try {
			ServerSocket ss = new ServerSocket(PORT);
			while (true) {
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
			this.clientSocket = s;
			this.clientNumber = cno;
		}
		public void run() {
			System.out.println("Client #" + this.clientNumber + " Connected....");
			try {
				PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
				ObjectInputStream in = new ObjectInputStream(this.clientSocket.getInputStream());
				Triangle t;
				
				while ((t = (Triangle) in.readObject()) != null) {
					int edge1 = (int) Math.round(t.getEdge1Length());
					int edge2 = (int) Math.round(t.getEdge2Length());
					int edge3 = (int) Math.round(t.getEdge3Length());
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
	
	
	
}
