package simpleServer.util;

import java.io.IOException;
import java.net.SocketException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class SecureNetworkService extends Thread {

	private ExecutorService pool;
	private SSLServerSocket serverSocket;
	private Consumer<SSLSocket> RequestHandler;
	
	public SecureNetworkService(final SSLServerSocket serverSocket, final Consumer<SSLSocket> onRequest) {		
		this.serverSocket = Objects.requireNonNull(serverSocket);
		this.RequestHandler = Objects.requireNonNull(onRequest);
		
		this.pool = Executors.newCachedThreadPool();
	}
	
	public void run() {
		
		SSLSocket s = null;
		while(!Thread.currentThread().isInterrupted()){
			try {
				s = (SSLSocket)serverSocket.accept();
			} catch (SocketException e) {
			} catch(IOException ex){
				ex.printStackTrace();
			}
			if(!Thread.currentThread().isInterrupted())
			pool.execute(new SecureHandler(s, RequestHandler));	
		}
		
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			System.err.println("[ERROR] Interrupted while waiting for completion of child tasks");
		}
	}
}
