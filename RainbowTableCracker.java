package com.mieuxcoder.rainbowtable;

import java.io.UTFDataFormatException;
import java.nio.charset.Charset;

public class RainbowTableCracker {
	private RainbowTable rainbowTable;
	private byte[] hash;
	private int nbReduction;
	private ReduceHash reduction;

	public RainbowTableCracker(RainbowTable rainbowTable, byte[] hash, int nbReduction) {
		if (rainbowTable == null) {
			throw new IllegalArgumentException("You haven't entered a rainbow Table");
		}
		this.rainbowTable = rainbowTable;
		if (hash == null) {
			throw new IllegalArgumentException("You haven't entered any hash to crack");
		}
		this.hash = hash;
		this.nbReduction = nbReduction;
		this.reduction = new ReduceHash();
	}
	
	public void crack() {
		int currentIndex = nbReduction;
		byte[] currentHash = hash.clone();
		String tail = null;
		while (currentIndex >= 0) {
			tail = findTail(currentHash, currentIndex);
			if (tail != null) {
				break;
			}
			currentIndex--;
		}
		if (currentIndex < 0) {
			return;
		}
		String head = this.rainbowTable.getHead(tail);
		findPassword(head);
	}
	
	private String findTail(byte[] hash, int startIndex) {
		String tail = null;
		byte[] currentHash = hash;
		for (int i = startIndex; i <= nbReduction; i++) {
			tail = this.reduction.reduce(currentHash, i);
			currentHash = tail.getBytes(); //todo
		}
		int index = this.rainbowTable.search(tail);
		if (index != -1) {
			return tail;
		}
		return null;
	}
	
	private String findPassword(String head) {
		String currentReduction = head;
		byte[] currentHash = head.hash();
		// Recommencer les hash puis reduction jusqu'à trouver le hash du début et puis récupérer le 
	}
	
}

// add a search in rainbow table where it searches for the hash that i have entered 
// add a getHead function which searches for a head associated to the tail in argument