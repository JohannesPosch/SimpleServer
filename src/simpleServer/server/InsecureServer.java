package simpleServer.server;

import java.io.IOException;
import java.net.ServerSocket;

import simpleServer.exception.IllegalPortException;
import simpleServer.exception.RunningServerException;

public abstract class InsecureServer extends Server {

	private ServerSocket srv_sock;
	
	public InsecureServer(final String name, final int port) throws NullPointerException, IllegalPortException {
		super(name, port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean start() throws RunningServerException{
		if(this.isRunning())
			throw new RunningServerException();
		try{
			this.srv_sock = new ServerSocket(this.getPort());
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

	protected ServerSocket getSSocket(){
		return this.srv_sock;
	}
	
	protected abstract boolean start_Handler_service();
	
	protected abstract boolean stop_Handler_service();
}
