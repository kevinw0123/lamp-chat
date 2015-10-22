package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient extends Thread {
	private BufferedReader br;
	private PrintWriter pw;
	private String uID, hostname;
	private int port;
	
	public ChatClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		Scanner scan = null;
		Socket s = null;
		try {
			s = new Socket(hostname, port);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
			this.start();
			scan = new Scanner(System.in);
			
			while (true) {
				String line = scan.nextLine();
				pw.println(uID + ": " + line);
				pw.flush();
			}
		} catch (IOException ioe) {
			System.out.println("Cannot connect to server: " + ioe.getMessage());
		} finally {
			if (scan != null) scan.close();
//			if (s != null) s.close();
		}
	}
	
	public void run() {
		try {
			for (int i=0; i<10; i++) {
				System.out.println(" ");
			}
			System.out.println("Connected to " + hostname + " at " + port);
			while (true) {
				if (uID == null) uID = br.readLine();
				else {
					String line = br.readLine();
					//append to text area
					if (line == null) break;
					System.out.println(line);
				}
			}
		} catch (IOException ioe) {
			System.out.println("ioe in ChatClient.run(): " + ioe.getMessage());
		}
	}
	
	public static void main (String [] args) {
//		new ChatClient("192.168.1.123", 6789);
		new ChatClient("localhost", 6789);
	}
}
