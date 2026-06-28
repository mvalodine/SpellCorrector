package spell;

import java.util.Arrays;

public class MyNode implements Trie.Node {

	private int count;
	private MyNode[] letters;
	private char letter;
	
	//Constructors----------------------------------------------------------------------
	public MyNode(){
		count = 0;
		letters = new MyNode[26];
	}

	public MyNode(char letter){
		this.letter = letter;
		count = 0;
		letters = new MyNode[26];
	}
	
	//other stuff-----------------------------------------------------------------------
	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return count;
	}

	public void incrementCount() {
		count++;
	}

	public char getLetter() {
		return letter;
	}
	
	public MyNode[] getLetters(){
		return letters;
	}
	
	public MyNode getNode(char letterToGet){
		if (letters[letterToGet - 'a'] == null)
			return null;
		else
			return letters[letterToGet - 'a'];
		
	}

	public boolean addNode(char letterToAdd){
		if (letters[letterToAdd - 'a'] == null){
			letters[letterToAdd - 'a'] = new MyNode(letterToAdd);
			return true;
		}
			return false;
		
	}
	
	//hash and equals-------------------------------------------------------------------
	@Override
	public int hashCode() {
		final int prime = 37;
		int result = 1;
		result = prime * result + count;
		result = prime * result + letter;
		result = prime * result + Arrays.hashCode(letters);
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
		MyNode other = (MyNode) obj;
		if (count != other.count)
			return false;
		if (letter != other.letter)
			return false;
		if (!Arrays.equals(letters, other.letters))
			return false;
		
		for (int i = 0; i < letters.length; i++){
			if (letters[i] != null && !letters[i].equals(other.letters[i]))
				return false;
		}
		
		return true;
	}
}
