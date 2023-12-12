package application.server;

import java.util.ArrayList;
import java.util.List;

public class ServerThreadBus {
	
	private List<ServerThread> listServerThreads;
	
	public List<ServerThread> getListServerThreads(){
		return listServerThreads;
	}
	
	public ServerThreadBus() {
		listServerThreads = new ArrayList<>();
	}
	
	public void add(ServerThread serverThread) {
		listServerThreads.add(serverThread);
	}
	
	public int getLength() {
		return listServerThreads.size();
	}
	
	public ServerThread getServerThreadByUserID(int ID) {
		for(int i=0; i < Server.serverThreadBus.getLength(); i++) {
			if(Server.serverThreadBus.getListServerThreads().get(i).getUser().getID() == ID) {
				return Server.serverThreadBus.listServerThreads.get(i);
			}
		}
		return null;
	}
	
	public void remove(int id) {
		for(int i=0; i < Server.serverThreadBus.getLength(); i++) {
			if(Server.serverThreadBus.getListServerThreads().get(i).getClientNumber()==id) {
				Server.serverThreadBus.listServerThreads.remove(i);
			}
		}
	}
}
