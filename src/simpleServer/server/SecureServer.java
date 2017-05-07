package simpleServer.server;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import simpleServer.exception.IllegalPortException;

public abstract class SecureServer extends Server {

	private SSLServerSocket srv_sock;
	
	public SecureServer(String name, int port) throws NullPointerException, IllegalPortException {
		super(name, port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean start() {
		try{
			this.srv_sock = (SSLServerSocket) SSLServerSocketFactory
								.getDefault().createServerSocket(this.getPort());
		}catch(IOException ex){
			this.srv_sock = null;
			return false;
		}
		this.setRunning(true);
		
		return this.start_Handler_service();
	}

	@Override
	public boolean stop() throws IOException {
		if(!this.isRunning())
			return false;
		
		if(!stop_Handler_service())
			return false;
		
		this.srv_sock.close();
		this.setRunning(false);
		this.srv_sock = null;
		return true;
	}
	
	protected SSLServerSocket getSSocket(){
		return this.srv_sock;
	}

	protected abstract boolean start_Handler_service();
	
	protected abstract boolean stop_Handler_service();

}
