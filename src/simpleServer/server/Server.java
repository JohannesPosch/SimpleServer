// -------------------------------------------------------
// Workfile: Server.java
// Author: Johannes Posch
// Date: 05.05.2017
// -------------------------------------------------------

package simpleServer.server;

import java.io.IOException;

import simpleServer.exception.IllegalPortException;
import simpleServer.exception.RunningServerException;
import simpleServer.util.Constant;

public abstract class Server {

	private String mServerName;
	private int mPort;
	private boolean mRunning;
	
	protected Server(final String name, final int port) throws NullPointerException, IllegalPortException{
		
		if(name.equals("") || name == null)
			throw new NullPointerException();
		
		if(port < Constant.getMinServerPort() || port > Constant.getMaxServerPort())
			throw new IllegalPortException();
		
		this.mServerName = name;
		this.mPort = port;
		
		this.mRunning = false;
	}

	public void setPort(final int port) throws IllegalPortException, RunningServerException{
		if(port < Constant.getMinServerPort() || port > Constant.getMaxServerPort())
			throw new IllegalPortException();
		
		if(this.mRunning)
			throw new RunningServerException();
		
		this.mPort = port;
	}
	
	public String getName(){
		return this.mServerName;
	}
	
	public int getPort(){
		return this.mPort;
	}
	
	public boolean isRunning(){
		return this.mRunning;
	}
	
	protected void setRunning(final boolean running){
		this.mRunning = running;
	}
	
	public abstract boolean start() throws RunningServerException;
	
	public abstract boolean stop() throws IOException;
	
	protected abstract boolean start_Handler_service();
	
	protected abstract boolean stop_Handler_service();
}
