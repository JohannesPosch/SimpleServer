package simpleServer.util;

import java.util.Objects;
import java.util.function.Consumer;

import javax.net.ssl.SSLSocket;

public class SecureHandler implements Runnable {

	private SSLSocket requestSocket;
	private Consumer<SSLSocket> handleRoutine;
	
	public SecureHandler (final SSLSocket sock, final Consumer<SSLSocket> handler){
		this.requestSocket = Objects.requireNonNull(sock);
		this.handleRoutine = Objects.requireNonNull(handler);
	}
	
	@Override
	public void run(){
		handleRoutine.accept(requestSocket);
	}
}
