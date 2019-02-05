package edu.eci.arsw.threads;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blacklistvalidator.HostBlackListsValidator;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class LifeCycleThread extends Thread{

	private int inf, sup, nServers , ocurrencesCount = 0;
	
	private String ipaddress;
	private List<Integer> idServer ;
	public LifeCycleThread(int inf , int sup, String ipaddress) {
//		super("my extending thread Life Cycle");
		this.inf = inf  ; this.sup = sup; 
		this.ipaddress = ipaddress;
		nServers=0;
		idServer = new ArrayList<Integer>();
		ocurrencesCount = 0;
//		System.out.println("my thread created" + this);
		start();
	}

	public int getNServers() {
		return nServers;
	}
	
	public List<Integer> getIdServer(){
		return idServer;
	}
	
	@Override
	public void run() {

		HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
		int checkedListsCount = 0;
		
		for (int i = inf; i <= sup; i++) {
			checkedListsCount++;
			if (skds.isInBlackListServer(i, ipaddress)) {
				idServer.add(i);
				ocurrencesCount++;
			}
		}
		
		nServers = ocurrencesCount;
	}
	
}
