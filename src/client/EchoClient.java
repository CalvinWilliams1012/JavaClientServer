package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Original source code recieved through Algonquin College Computer Programming course Enterprise Programming by Stanley Pieda.
 * Edited by Calvin A. Williams.
 * 
 * EchoClient is a client to connect to EchoServers and communicate with the ClientHandler.
 */
public class EchoClient {

	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverName;
	public static final String DEFAULT_SERVER_NAME = "localhost";
	private int portNum;
	BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Main method accepts parameters for server name, port num.
	 * @param args
	 */
	public static void main(String[] args) {
		switch (args.length) {
		case 2:
			(new EchoClient(args[0], Integer.parseInt(args[1]))).runClient();
			break;
		case 1:
			(new EchoClient(DEFAULT_SERVER_NAME, Integer.parseInt(args[0]))).runClient();
			break;
		default:
			(new EchoClient(DEFAULT_SERVER_NAME, server.EchoServer.DEFAULT_PORT)).runClient();
		}
	}
	/**
	 * Constructor for EchoClient to get values in-order to create the Socket.
	 * @param serverName Value of the server address.
	 * @param portNum value of the server port.
	 */
	public EchoClient(String serverName, int portNum) {
		this.serverName = serverName;
		this.portNum = portNum;
	}

	/**
	 * Client code block creates a socket as well as an input and output stream.
	 * The client waits for user input and sends the message to the server.
	 * The servers response is then printed.
	 * The client will quit if "thanks for all the fish" is returned in the server response.
	 */
	public void runClient() {
		try {
			connection = new Socket(InetAddress.getByName(serverName), portNum);
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			System.out.println("To Quit, enter 'thanks for all the fish'");
			do {
				System.out.print("Input> ");
				message = keyboard.readLine();
				if (message != null){
					output.writeObject(message);
					output.flush();
					message = (String) input.readObject();
					System.out.println(message);
				}
			}while (!message.contains("thanks for all the fish"));
			input.close();
			output.close();
			connection.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}
	}
}
