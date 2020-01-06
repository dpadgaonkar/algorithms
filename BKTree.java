package com.restaurantreview.util;

import java.util.ArrayList;

public class BKTree {

	private Node root;

	private final int TOLERANCE = 2;

	public int levenshteinDistance(String word1, String word2) {
		int m = word1.length();
		int n = word2.length(); 

		int [][]dp = new int[m+1][n+1];

		for (int i=0; i<=m; i++) {
			dp[i][0] = i; 
		}
		for (int j=0; j<=n; j++){ 
			dp[0][j] = j; 
		}

		for (int i=1; i<=m; i++){		
			for (int j=1; j<=n; j++){ 			
				if (word1.charAt(i-1) != word2.charAt(j-1)){ 				
					dp[i][j] = Math.min( 1 + dp[i-1][j], Math.min(1 + dp[i][j-1], 1 + dp[i-1][j-1])); 
				} 
				else{
					dp[i][j] = dp[i-1][j-1];
				}
			} 
		} 
		return dp[m][n]; 
	} 

	public void addBKTreeNode(String word){
		if(root == null){
			//System.out.println("root is null:"+word);
			root = new Node();
			root.setWord(word);
			//root.setChildren(0);
			return;
		}
		Node ptr = root;
		//Node prev = ptr;
		while(ptr != null){			
			int distance = levenshteinDistance(word, ptr.getWord());
			Node []children = ptr.getChildren();
			if(children[distance] == null){
				//System.out.println("inserting ..."+word+" parent is:"+ptr.getWord()+" at distance: "+distance);
				Node node = new Node();
				node.setWord(word);
				node.setDistance(distance);
				ptr.setChildren(node);
				break;
			}
			else{
				//prev = ptr;
				ptr = children[distance];
			}
		}				
	}

	public ArrayList<Node> getSimilarWords(String word){
		Node ptr = root;
		ArrayList<Node> wordList = new ArrayList<Node>();
		traverseBKTree(ptr, word, wordList);
		return wordList;
	}

	public void traverseBKTree(Node ptr, String word, ArrayList<Node> wordList){

		if(ptr == null){
			return;
		}

		int distance = levenshteinDistance(word, ptr.getWord());
		if(distance <= TOLERANCE){
			// add the word in the list
			//System.out.println("Adding: "+ptr.getWord());
			Node n = new Node();
			n.setWord(ptr.getWord());
			n.setDistance(distance);
			if(!wordList.contains(n))
				wordList.add(n);
		}
		int lowerBound = distance - TOLERANCE;
		int upperBound = distance + TOLERANCE;
		if(lowerBound < 0){
			lowerBound = 1;
		}
		Node []children = ptr.getChildren();
		for(int i = 0; children != null && i < children.length; i++){
			Node node = children[i];
			if(node != null && node.getDistance() >= lowerBound && node.getDistance() <= upperBound){
				ptr = children[i];
				traverseBKTree(ptr, word, wordList);
			}
		}		
	}

	public static void main(String []args){
		String dictionary[] = {"hell","help","shel","smell", 
				"fell","felt","oops","pop","oouch","halt","halt"
		};
		//String dictionary[] = {"help","hell","hello","loop", "helps","shell","helper","troop"};

		/*BKTree tree = new BKTree();
		for (int i = 0; i < dictionary.length; i++) {
			tree.addBKTreeNode(dictionary[i]);
		}
		Node ptr = tree.root;
		ArrayList<String> wordList = new ArrayList<String>();
		tree.traverseBKTree(ptr, "halt", wordList);
		System.out.println(wordList);*/
		String s = "fish,fash|thali,tali|bombil,boombil,bomb|bangada,bagada|aloo,";
		BKTree tree = new BKTree();
		String []pipeTokens = s.split("\\|");

		ArrayList<String[]> pipeTokenList = new ArrayList<>();
		for(int i = 0; pipeTokens != null && i < pipeTokens.length; i++){
			String []commaToken = pipeTokens[i].split("\\,");
			String []tokenArray = new String[commaToken != null ? commaToken.length : 0];
			for(int j = 0; commaToken != null && j < commaToken.length; j++){
				String str = commaToken[j];
				tokenArray[j] = str;
			}
			if(tokenArray != null && tokenArray.length > 0){
				pipeTokenList.add(tokenArray);
			}
		}
	}




}
