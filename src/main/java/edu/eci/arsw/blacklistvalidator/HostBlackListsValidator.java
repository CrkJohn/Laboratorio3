/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import edu.eci.arsw.threads.LifeCycleThread;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

	private static final int BLACK_LIST_ALARM_COUNT = 5;

	/**
	 * Check the given host's IP address in all the available blac k lists, and
	 * report it as NOT Trustworthy when such IP was reported in at least
	 * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case. The search
	 * is not exhaustive: When the number of occurrences is equal to
	 * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as NOT
	 * Trustworthy, and the list of the five blacklists returned.
	 * 
	 * @param ipaddress suspicious host's IP address.
	 * @return Blacklists numbers where the given host's IP address was found.
	 */
	public List<Integer> checkHost(String ipaddress, int N) {
		LinkedList<Integer> blackListOcurrences = new LinkedList<>();
		HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
		int checkedListsCount = 0;
		LifeCycleThread threads[] = new LifeCycleThread[N];
		int servers = skds.getRegisteredServersCount();
		int val = -1;
		for (int i = 0; i < N; i++) {
			if (i == N - 1)
				val = 0;
			System.err.println((int) (i * servers / N) + " " + (int) ((i + 1) * servers / N + val));
			threads[i] = new LifeCycleThread((int) (i * servers / N), (int) ((i + 1) * servers / N + val), ipaddress);
		}

		for (LifeCycleThread lifeCycleThread : threads) {
			try {
				lifeCycleThread.join();
			} catch (InterruptedException e) {
				System.err.println("Error al join lifeCycleThread");
			}
		}

		int sumaOcurrencesThreads = 0;
		for (LifeCycleThread lifeCycleThread : threads) {
			sumaOcurrencesThreads += lifeCycleThread.getNServers();
			blackListOcurrences.addAll(lifeCycleThread.getIdServer());
			if (sumaOcurrencesThreads >= BLACK_LIST_ALARM_COUNT) {
				skds.reportAsNotTrustworthy(ipaddress);
			} else {
				skds.reportAsTrustworthy(ipaddress);
			}
		}
		LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}",
				new Object[] { checkedListsCount, skds.getRegisteredServersCount() });
		return blackListOcurrences;
	}

	public List<Integer> checkHost(String ipaddress) {
		LinkedList<Integer> blackListOcurrences = new LinkedList<>();
		int ocurrencesCount = 0;
		HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
		int checkedListsCount = 0;
		for (int i = 0; i < skds.getRegisteredServersCount() && ocurrencesCount < BLACK_LIST_ALARM_COUNT; i++) {
			checkedListsCount++;
			if (skds.isInBlackListServer(i, ipaddress)) {
				blackListOcurrences.add(i);
				ocurrencesCount++;
			}
		}

		if (ocurrencesCount >= BLACK_LIST_ALARM_COUNT) {
			skds.reportAsNotTrustworthy(ipaddress);
		} else {
			skds.reportAsTrustworthy(ipaddress);
		}

		LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}",
				new Object[] { checkedListsCount, skds.getRegisteredServersCount() });

		return blackListOcurrences;
	}

	private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());

}
