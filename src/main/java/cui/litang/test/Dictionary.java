package cui.litang.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Date 2020-6-19 23:47:40
 * @author 86133
 *
 */
public class Dictionary {

	String CON_BRACE_START = "{";
	String CON_BRACE_END = "}";
	String CON_COMMA = ",";
	String CON_NOTHING = "";
	String CON_SPACE = " ";
	String CON_LINE_BREAK = "\n";
	char CON_SPACE_CHAR = ' ';
	char CON_POINT_CHAR = '.';

	/**
	 * 创建索引 create tree index by HashMap
	 * 
	 * @param dic original dictionary
	 */
	public HashMap<Character, Map> dic2Map(String dic) {

		HashMap<Character, Map> dictionaryMap = new HashMap<Character, Map>(26);

		String[] dicArr = dic.replace(CON_BRACE_START, CON_NOTHING).replace(CON_BRACE_END, CON_NOTHING)
				.split(CON_COMMA);

		// de-duplication
		Set<String> dicSet = new HashSet<String>();
		for (String word : dicArr) {
			dicSet.add(word);
		}

		// find word that contains whitespace
		HashSet<String> haveSapceWordsSet = new HashSet<String>();
		Iterator<String> iterator = dicSet.iterator();
		while (iterator.hasNext()) {
			String word = iterator.next();
			if (word.contains(CON_SPACE)) {
				haveSapceWordsSet.add(word);
				iterator.remove();
			}
		}

		// haveSapceWordsSet transfer to haveSapceWordsMap
		HashMap<String, List<String>> haveSapceWordsMap = new HashMap<String, List<String>>();
		for (String word : haveSapceWordsSet) {
			String noSpaceWord = word.replace(CON_SPACE, CON_NOTHING);
			if (haveSapceWordsMap.containsKey(noSpaceWord)) {
				List<String> list = haveSapceWordsMap.get(noSpaceWord);
				list.add(word);
				haveSapceWordsMap.put(noSpaceWord, list);
			} else {
				ArrayList<String> arrayList = new ArrayList<String>();
				arrayList.add(word);
				haveSapceWordsMap.put(noSpaceWord, arrayList);
			}

		}

		// haveSapceWordsMap's key add to dicSet
		Set<String> keySet = haveSapceWordsMap.keySet();
		for (String word : keySet) {
			boolean contains = dicSet.contains(word);
			if (contains) {
				List<String> list = haveSapceWordsMap.get(word);
				list.add(word);
				haveSapceWordsMap.put(word, list);
			} else {
				dicSet.add(word);
			}
		}

		// dicSet transfer to dicMap
		HashMap<Character, Map> tempMap = null;
		for (String word : dicSet) {

			tempMap = dictionaryMap;// 指针用法 point
			for (int cursor = 0; cursor < word.length(); cursor++) {

				char letter = word.charAt(cursor);
				boolean containsKey = tempMap.containsKey(letter);

				if (!containsKey) {
					tempMap.put(letter, new HashMap());
				}

				tempMap = (HashMap<Character, Map>) tempMap.get(letter);

				// 处理结尾 for tail
				if (cursor == word.length() - 1) {
					boolean containsEndKey = tempMap.containsKey(CON_POINT_CHAR);
					if (!containsEndKey) {
						tempMap.put(CON_POINT_CHAR, new HashMap());
					}

				}
			}

		}

		dictionaryMap.put(CON_SPACE_CHAR, haveSapceWordsMap);

		System.out.println(dictionaryMap);
		return dictionaryMap;

	}

	ArrayList<ArrayList<String>> sentenceList = new ArrayList<ArrayList<String>>();

	/**
	 * parse letter to word
	 * 
	 * @param wordList recursion argument
	 * @param sentence sentence without whiteSpace
	 * @param dicMap   HashMap dictionary
	 */
	public void letter2Word(ArrayList<String> wordList, String sentence, HashMap<Character, Map> dicMap) {

		char[] charArray = sentence.toCharArray();
		HashMap<Character, Map> subMap = null;
		subMap = dicMap;

		int start = 0;
		for (int cursor = 0; cursor < charArray.length;) {

			char key = charArray[cursor];

			boolean containsKey = subMap.containsKey(key);
			boolean containsSharp = subMap.containsKey(CON_POINT_CHAR);

			if (containsKey && !containsSharp) {

				subMap = (HashMap<Character, Map>) subMap.get(key);
				cursor++;
			}

			if (containsSharp && !containsKey) {

				wordList.add(sentence.substring(start, cursor));
				subMap = dicMap;
				start = cursor;
			}

			if (containsKey && containsSharp) {

				ArrayList<String> tempList = new ArrayList<String>();
				tempList.addAll(wordList);
				tempList.add(sentence.substring(start, cursor));
				letter2Word(tempList, sentence.substring(cursor), dicMap);

				subMap = (HashMap<Character, Map>) subMap.get(key);
				cursor++;
			}

			if (!containsKey && !containsSharp) {

				break;
			}

			// process tail
			if (cursor == charArray.length && subMap.containsKey(CON_POINT_CHAR)) {
				wordList.add(sentence.substring(start, cursor));

				sentenceList.add(wordList);
			}

		}
		System.out.println(sentenceList);
	}

	// word array transfer to sentence string
	public String word2Sentence(ArrayList<ArrayList<String>> sentenceList,
			Map<String, List<String>> haveSpaceWordsMap) {

		for (int i = 0; i < sentenceList.size(); i++) {
			ArrayList<String> wordList = sentenceList.get(i);
			for (int j = 0; j < wordList.size(); j++) {
				String wordKey = wordList.get(j);
				if (haveSpaceWordsMap.containsKey(wordKey)) {
					List<String> list = haveSpaceWordsMap.get(wordKey);

					for (int k = 0; k < list.size(); k++) {
						String spaceWord = list.get(k);
						if (!spaceWord.equals(wordKey)) {
							ArrayList<String> sentence = new ArrayList<String>();
							sentence.addAll(wordList);
							sentence.set(j, spaceWord);
							sentenceList.add(sentence);
						}

						if (k == 0 && !list.contains(wordKey)) {
							sentenceList.remove(i);
							i--;
						}

					}

				}
			}
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < sentenceList.size(); i++) {
			ArrayList<String> arrayList = sentenceList.get(i);
			String string = arrayList.toString().replace(CON_COMMA, CON_NOTHING);
			string = string.substring(1, string.length() - 1);
			if (i < sentenceList.size() - 1) {
				string = string + CON_LINE_BREAK;
			}
			stringBuilder.append(string);
		}
		return stringBuilder.toString();

	}

}
