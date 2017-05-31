package simpleServer.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class NetworkService extends Thread{

	private ExecutorService pool;
	private ServerSocket serverSocket;
	private Consumer<Socket> RequestHandler;
	
	public NetworkService(final ServerSocket serverSocket, final Consumer<Socket> onRequest) {		
		this.serverSocket = Objects.requireNonNull(serverSocket);
		this.RequestHandler = Objects.requireNonNull(onRequest);
		
		this.pool = Executors.newCachedThreadPool();
	}
	
	public void run() {
		
		Socket s = null;
		while(!Thread.currentThread().isInterrupted()){
			try {
				s = serverSocket.accept();
			} catch (SocketException e) {
			} catch(SocketTimeoutException ext){
				continue;
			} catch(IOException ex){
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			if(!Thread.currentThread().isInterrupted())
			pool.execute(new Handler(s, RequestHandler));	
		}
		
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			System.err.println("[ERROR] Interrupted while waiting for completion of child tasks");
		}
	}
}
