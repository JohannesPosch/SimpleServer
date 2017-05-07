package simpleServer.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import simpleServer.exception.NoSuchServerException;
import simpleServer.exception.RunningServerException;
import simpleServer.server.Server;

public class ServerControl {

	ArrayList<Server> mServers;
	
	public ServerControl(){
		this.mServers = new ArrayList<Server>();
	}
	
	public boolean addServer(final Server srv) throws NullPointerException{
		
		Objects.requireNonNull(srv);
		
		return this.mServers.add(srv);
	}
	
	public boolean removeServer(final Server srv) throws NullPointerException{
		
		Objects.requireNonNull(srv);
		
		if(!this.mServers.contains(srv) || srv.isRunning())
			return false;
		return this.mServers.remove(srv);
	}
	
	public boolean removeServer(final int index) throws NullPointerException{
		
		Server tmp = this.mServers.get(index);
		
		if(tmp == null)
			return false;
		
		return this.removeServer(tmp);
	}
	
	public void printServerInformation(){
		for(Server tmp_srv : this.mServers){
			
			this.Message_OK_FAIL(tmp_srv.getName(), tmp_srv.isRunning(), "running", "not running", false, "");
			if(tmp_srv.isRunning())
				System.out.println("\tPort: " + tmp_srv.getPort());
		}
	}
	
	public void startAllServers() throws IOException{
		for(int i = 0; i < this.mServers.size(); i++){
			try{
				this.startSpecificServer(i);
			}catch(IndexOutOfBoundsException ex){
				System.err.println("[ERROR] Sever ServerControl error");
			}
		}
	}
	
	public void stopAllServers() throws IOException{		
		for(int i = 0; i < this.mServers.size(); i++)
			this.stopSpecificServer(i);
	}
	
	public void startSpecificServer(final String name) throws NoSuchServerException, IOException {
		
		int index = this.getServerIndexOfName(name);
		
		try{
			this.startSpecificServer(index);
		}catch(IndexOutOfBoundsException ex){
			System.err.println("[ERROR] Sever ServerControl error");
		}
	}
	
	public void startSpecificServer(final int index) throws IndexOutOfBoundsException, IOException {
		
		Server tmp_srv = this.mServers.get(index);
		if(!tmp_srv.isRunning()){
			try{
				this.Message_OK_FAIL("Starting " + tmp_srv.getName(), tmp_srv.start(), true, "[ERROR] Port already in use");
			}catch(RunningServerException ex){
				System.err.println("[ERROR] Server is running");
			}
		}else{
			System.out.println("Starting " + tmp_srv.getName() + " ...... already running");
		}
	}
	
	public void stopSpecificServer(final String name) throws NoSuchServerException, IOException {
		
		int index = this.getServerIndexOfName(name);
		
		try{
			this.stopSpecificServer(index);
		}catch(IndexOutOfBoundsException ex){
			System.err.println("[ERROR] Sever ServerControl error");
		}
	}
	
	public void stopSpecificServer(final int index) throws IndexOutOfBoundsException, IOException {
		
		Server tmp_srv = this.mServers.get(index);
		
		if(tmp_srv.isRunning()){
			this.Message_OK_FAIL("Stopping " + tmp_srv.getName(), tmp_srv.stop(), false, "");
		}else{
			System.out.println("Stopping " + tmp_srv.getName() + " ...... already stopped");
		}
	}
	
	public String[] getServerNameList(){
		ArrayList<String> names = new ArrayList<String>();
		
		for(Server tmp_srv : this.mServers){
			names.add(tmp_srv.getName());
		}
		
		String[] strArr = new String[names.size()];
		strArr = names.toArray(strArr);
		
		return strArr;
	}
	
	public Server getServer(final String name) throws NoSuchServerException{
		int index = this.getServerIndexOfName(name);
		
		try{
			return this.getServer(index);
		}catch(IndexOutOfBoundsException ex){
			System.err.println("[ERROR] Sever ServerControl error");
		}
		return null;
	}
	
	public Server getServer(final int index) throws IndexOutOfBoundsException{
		if(this.mServers.isEmpty())
			return null;
		
		return this.mServers.get(index);
	}
	
	public int getServerCount(){
		return this.mServers.size();
	}
	
	private void Message_OK_FAIL(final String tex, final boolean query, final boolean printErrorMsg, final String errorMsg){
		System.out.print(tex + " ...... ");
		if(query)
			System.out.println("[ OK ]");
		else{
			System.out.println("[ FAIL ]");
			if(printErrorMsg)
				System.out.println("\t" + errorMsg);
		}
	}
	
	private void Message_OK_FAIL(final String tex, final boolean query, final String option1, final String option2, final boolean printErrorMsg, final String errorMsg){
		System.out.print(tex + " ...... ");
		if(query)
			System.out.println(option1);
		else{
			System.out.println(option2);
			if(printErrorMsg)
				System.out.println("\t" + errorMsg);
		}
	}
	
	private int getServerIndexOfName(final String name) throws NoSuchServerException{
		boolean found = false;
		int index = 0;
		for(index = 0; index < this.mServers.size() && !found; index++){
			Server tmp_srv = this.mServers.get(index);
			if(name.equals(tmp_srv.getName()))
				found = true;
		}
		
		if(!found)
			throw new NoSuchServerException();
		index--;
		
		return index;
	}
}
