package com.restaurantreview.util;

public class Node implements Comparable<Node>{

	private String word;
	
	private int distance;
	
	private final int MAX_LENGTH = 20;
	
	private Node [] children;
	
	public Node(){
		children = new Node[MAX_LENGTH];
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Node[] getChildren() {
		return children;
	}

	public void setChildren(Node node) {
		this.children[node.distance] = node;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

	@Override
	public int compareTo(Node o) {
		return this.distance - o.distance;
	}

	@Override
	public String toString() {
		return "Node [word=" + word + ", distance=" + distance + "]";
	}
	
	
	
}
