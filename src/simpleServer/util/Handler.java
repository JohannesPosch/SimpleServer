package simpleServer.util;

import java.net.Socket;
import java.util.Objects;
import java.util.function.Consumer;

public final class Handler implements Runnable{

	private Socket requestSocket;
	private Consumer<Socket> handleRoutine;
	
	public Handler (final Socket sock, final Consumer<Socket> handler){
		this.requestSocket = Objects.requireNonNull(sock);
		this.handleRoutine = Objects.requireNonNull(handler);
	}
	
	@Override
	public void run(){
		handleRoutine.accept(requestSocket);
	}
}
