package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class MySpellCorrector implements SpellCorrector{

	private MyTrie trie = new MyTrie();
	private Set<String> distanceOne = new TreeSet<String>();
	private Set<String> distanceTwo = new TreeSet<String>();
	private String suggestion;
	
	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		// TODO Auto-generated method stub
		Scanner scnr = new Scanner (new File(dictionaryFileName));
		
		while(scnr.hasNext()){
			trie.add(scnr.next().toLowerCase());
		}
		
		scnr.close();
	}

	@Override
	public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
		// TODO Auto-generated method stub
		inputWord = inputWord.toLowerCase();
		
		deletion(inputWord, distanceOne);
		transposition(inputWord, distanceOne);
		alteration(inputWord, distanceOne);
		insertion(inputWord, distanceOne);
		
		compareSuggestions(distanceOne);
		
		if (suggestion == null){
			fillDistanceTwo();
			compareSuggestions(distanceTwo);
		}
		if (suggestion == null){
			throw new NoSimilarWordFoundException();
		}
		
		//System.out.println(trie.toString());
		
		return suggestion;
	}
	
	//Possible word differences-----------------------------------------------------------------
	private void deletion(String inputWord, Set<String> suggestionSet){
		StringBuilder newWord;
		for (int i = 0; i < inputWord.length(); i++){
			newWord = new StringBuilder(inputWord);
			newWord.deleteCharAt(i);
			suggestionSet.add(newWord.toString());
		}
	}
	
	private void transposition(String inputWord, Set<String> suggestionSet){
		StringBuilder newWord;
		for (int i = 0; i < inputWord.length() - 1; i++){
			newWord = new StringBuilder(inputWord);
			char c = inputWord.charAt(i);
			newWord.setCharAt(i, newWord.charAt(i+1));
			newWord.setCharAt(i+1, c);
			suggestionSet.add(newWord.toString());
		}
	}
	
	private void alteration(String inputWord, Set<String> suggestionSet){
		StringBuilder newWord;
		char c = 'a';
		for(int i = 0; i < inputWord.length(); i++){
			newWord = new StringBuilder(inputWord);
			c = 'a';
			for (int j = 0; j < 26; j++){
				newWord.setCharAt(i, c);
				suggestionSet.add(newWord.toString());
				c++;
			}
		}
	}
	
	private void insertion(String inputWord, Set<String> suggestionSet){
		StringBuilder newWord;
		char c = 'a';
		for (int i = 0; i <= inputWord.length(); i++){
			c = 'a';
			for (int j = 0; j < 26; j++){
				newWord = new StringBuilder(inputWord);
				newWord.insert(i, c);
				suggestionSet.add(newWord.toString());
				c++;
			}
		}
	}
	
	//Comparing possible suggestions-------------------------------------------------------------
	private void compareSuggestions(Set<String> suggestionSet){
		MyNode currentNode;
		int suggestionFrequency = 0;
		for (String s: suggestionSet){
			currentNode = (MyNode)trie.find(s);
			if (currentNode != null && currentNode.getValue() > 0 && suggestion == null){
				suggestion = s;
				suggestionFrequency = currentNode.getValue();
			}
			else if (currentNode != null &&  suggestionFrequency < currentNode.getValue()){
				suggestion = s;
				suggestionFrequency = currentNode.getValue();
			}
		}
	}
	
	//Fill distanceTwo Set----------------------------------------------------------------------
	private void fillDistanceTwo(){
		for(String s: distanceOne){
			deletion(s, distanceTwo);
			transposition(s, distanceTwo);
			alteration(s, distanceTwo);
			insertion(s, distanceTwo);
		}
	}

}
