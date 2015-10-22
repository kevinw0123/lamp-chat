package client;

import java.util.Scanner;

import server.ChatServer;

public class StartClient {

	public StartClient() {
		System.out.println("Welcome to the chat room");
		System.out.println("Options:");
		System.out.println("1. Host");
		System.out.println("2. Join");
		System.out.println("3. Exit");
		System.out.print("Enter an option: ");
		Scanner scan = new Scanner(System.in);
		String line = "";
		line = scan.nextLine();
		if (line.equals("1") || line.equals("host")) {
			System.out.print("Enter a port: ");
			line = scan.nextLine();
			if (line.matches("\\d+")) { 
				new ChatServer(Integer.parseInt(line));
			}
		} else if (line.equals("2") || line.equals("join")) {
			System.out.print("Enter an ip address: ");
			String ip = scan.nextLine();
			System.out.print("Enter a port: ");
			line = scan.nextLine();
			new ChatClient(ip, Integer.parseInt(line));
		}
		scan.close();
	}
	
	public static void main(String [] args) {
		new StartClient();
	}
}
