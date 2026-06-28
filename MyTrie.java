package spell;

public class MyTrie implements Trie {
	
	private int wordCount = 0;
	private int nodeCount = 1;
	private MyNode root;
	
	public MyTrie(){
		root = new MyNode();
	}
	
	@Override
	public void add(String word) {
		// TODO Auto-generated method stub
		MyNode currentNode = root;//switches between the nodes to traverse down the tree.
		
		for (int i = 0; i < word.length(); i++){
			if(currentNode.addNode(word.charAt(i))){ //addNode returns true if a new node was added
				nodeCount++;
			}
			currentNode = currentNode.getNode(word.charAt(i));
			
			if(i >= word.length() - 1){
				wordCount++;
				currentNode.incrementCount();
			}
		}
	}

	@Override
	public Node find(String word) {
		// TODO Auto-generated method stub
		MyNode currentNode = root;
		
		for (int i = 0; i < word.length(); i++){
			currentNode = currentNode.getNode(word.charAt(i));
			if (currentNode == null)
				return null;
			if (i >= word.length() - 1 && currentNode.getValue() > 0)
				return currentNode;
		}
		return null;
	}

	@Override
	public int getWordCount() {
		// TODO Auto-generated method stub
		return wordCount;
	}

	@Override
	public int getNodeCount() {
		// TODO Auto-generated method stub
		return nodeCount;
	}
	
	//Extra Stuff----------------------------------------------------------------------------------------------------
	@Override
	public String toString(){
		StringBuilder wsf = new StringBuilder();// word so far
		StringBuilder printString = new StringBuilder(); //string to print
		toString(wsf, printString, root);
		return printString.toString();
	}
	
	//Recursive toString function
	public void toString(StringBuilder wsf, StringBuilder printString, MyNode node){
		if (node != root)
			wsf.append(node.getLetter());
		if (node.getValue() > 0){
			printString.append(String.format("%s %d\n", wsf.toString(), node.getValue()));
		}
		
		for (int i = 0; i < node.getLetters().length; i++){
			
			if (node.getLetters()[i] != null){
				toString(wsf, printString, node.getLetters()[i]);
			}
		}
		
		if (node != root)
			wsf.deleteCharAt(wsf.length()-1);
	}
	
	@Override
	public int hashCode() {
		final int prime = 37;
		int result = 1;
		result = prime * result + nodeCount;
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		result = prime * result + wordCount;
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
		MyTrie other = (MyTrie) obj;
		if (wordCount != other.wordCount)
			return false;
		if (nodeCount != other.nodeCount)
			return false;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		
		return true;
	}
	
}
