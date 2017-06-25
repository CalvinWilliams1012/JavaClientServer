package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.ServerSocket;

/**
 * Original source code recieved through Algonquin College Computer Programming course Enterprise Programming by Stanley Pieda.
 * Edited by Calvin A. Williams.
 * 
 * EchoServer is a multithreaded server that accepts client connections and sends the work to ClientHandler. 
 */
public class EchoServer {

	private Socket connection;
	private ServerSocket server;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private int messagenum;
	private int portNum;
	public static final int DEFAULT_PORT = 8081;
	public static ExecutorService threadExecutor = Executors.newCachedThreadPool();

	/**
	 * Server can be ran using a port defined in parameters or constant DEFAULT_PORT.
	 * @param args 
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			(new EchoServer(Integer.parseInt(args[0]))).runServer();
		} else {
			(new EchoServer(DEFAULT_PORT)).runServer();
		}
	}

	/**
	 * EchoServer constructor sets the portNum for creating the ServerSocket.
	 * @param portNum
	 */
	public EchoServer(int portNum) {
		this.portNum = portNum;
	}

	/**
	 * Server code which instantiates the server socket and then constantly waits for connections and executes them on a new thread.
	 */
	public void runServer() {
		try{
			server = new ServerSocket(portNum);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Listening for connections...");
		while(true){
			try{
				connection = server.accept();
				ClientHandler client = new ClientHandler(connection);
				threadExecutor.execute(client);
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}
}
