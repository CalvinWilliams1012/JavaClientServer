package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * ClientHandler class is a runnable class to handle connections through the EchoServer class.
 * One ClientHandler is created and ran per client request.
 * @author Calvin A. Williams
 *
 */
public class ClientHandler implements Runnable {

	private String message = "";
	private int messagenum;
	private Socket connection;
	
	/**
	 * Constructor to get the connection in order to handle the client.
	 * @param connection Socket to handle client
	 */
	public ClientHandler(Socket connection){
		this.connection = connection;
	}
	
	/**
	 * Run method trys to create an output and input stream using the Socket connection passed into the constructor.
	 * The input is read and sent back to the client with additional information.
	 * When the client sends "thanks for all the fish" the output, input and connection are all closed. 
	 */
	@Override
	public void run() {
		try(
				ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());			
				ObjectInputStream input = new ObjectInputStream(connection.getInputStream())
		){
			do {
				message = (String) input.readObject();
				output.writeObject(messagenum++ + "Output> " + message);
				output.flush();
			} while (!message.contains("thanks for all the fish"));
				
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		} catch (ClassNotFoundException exception) {
			System.out.println(exception.getMessage());
		} finally {
			try {if (connection != null) {connection.close();}} catch (IOException ex) {/* to do */}
		}
		
	}

}
