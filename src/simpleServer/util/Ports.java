package simpleServer.util;

import simpleServer.exception.IllegalPortException;

public final class Ports {
	
	private static Ports myself;
	
	private final int absoluteMinServerPort = 0;
	private final int absoluteMaxServerPort = 65535;
	
	private int minServerPort;
	private int maxServerPort;
	
	private Ports(){
		this.minServerPort = 5000;
		this.maxServerPort = 10000;
	}
	
	public static Ports getInstance(){
		if(myself == null)
			myself = new Ports();
		
		return myself;
	}
	
	public int getMinServerPort(){
		return this.minServerPort;
	}
	
	public int getMaxServerPort(){
		return this.maxServerPort;
	}
	
	public void setMinServerPort(final int newPort) throws IllegalPortException{
		
		if(newPort < this.absoluteMinServerPort || newPort > this.absoluteMaxServerPort)
			throw new IllegalPortException();
		
		this.minServerPort = newPort;
	}
	
	public void setMaxServerPort(final int newPort) throws IllegalPortException{
		if(newPort < this.absoluteMinServerPort || newPort > this.absoluteMaxServerPort)
			throw new IllegalPortException();
		
		this.minServerPort = newPort;
	}
}
