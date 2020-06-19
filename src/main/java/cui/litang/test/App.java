package cui.litang.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * @Date 2020-6-19 19:38:52
 * @author 86133
 *
 */
public class App {

	public static String CONS_DIC = "{i,like,sam,sung,samsung,mobile,ice,cream,man go,and}";
	public static String CONS_CUST_DIC = "{i,like,sam,sung,mobile,icecream,man go,mango,and}";
	public static String CON_LINE_BREAK = "\n";
	public static char CON_SPACE_CHAR = ' ';

	public static void main(String[] args) {

		String stage1 = stage1("ilikeicecreamandmango", CONS_DIC);
		System.out.println("stage1:" + CON_LINE_BREAK + stage1);

		String stage2 = stage2("ilikeicecreamandmango", CONS_CUST_DIC);
		System.out.println("stage2:" + CON_LINE_BREAK + stage2);

		String stage3 = stage3("ilikeicecreamandmango", CONS_DIC, CONS_CUST_DIC);
		System.out.println("stage3:" + CON_LINE_BREAK + stage3);

	}

	/**
	 * Given a valid sentence without any spaces between the words and a dictionary
	 * of valid English words, find all possible ways to break the sentence in
	 * individual dictionary words. Consider the following dictionary { i, like,sam,
	 * sung, samsung, mobile, ice, cream, man go} Input: "ilikesamsungmobile"
	 * Output: i like sam sung mobile i like samsung mobile
	 * Input:"ilikeicecreamandmango" Output: i like ice cream and man go
	 */
	public static String stage1(String input, String dic) {

		Dictionary dictionary = new Dictionary();
		HashMap<Character, Map> dic2Map = dictionary.dic2Map(dic);
		ArrayList<String> wordList = new ArrayList<String>();
		dictionary.letter2Word(wordList, input, dic2Map);
		String word2Sentence = dictionary.word2Sentence(dictionary.sentenceList, dic2Map.get(CON_SPACE_CHAR));
		return word2Sentence;

	}

	/**
	 * #Stage 2 - new requirement to be implemented: If user provide a customized
	 * dictionary of valid English words as additional input, and the program will
	 * only find in the user customized dictionary E.g.:the user customized
	 * dictionary { i, like, sam, sung, mobile, icecream, man go, mango}
	 */
	public static String stage2(String input, String custDic) {

		String stage2 = stage1(input, custDic);
		return stage2;

	}

	/**
	 * #Stage 3 - new requirement to be implemented: If user provide a customized
	 * dictionary of valid English words as additional input, and the program will
	 * find all the valid words in the both dictionaries E.g.: the user customized
	 * dictionary { i, like, sam, sung, mobile, icecream, man go, mango}
	 */
	public static String stage3(String input, String dic, String custDic) {

		HashSet<String> resultSet = new HashSet<String>();
		StringBuilder stringBuilder = new StringBuilder();

		String[] standard = stage1(input, dic).split(CON_LINE_BREAK);
		String[] cust = stage1(input, custDic).split(CON_LINE_BREAK);

		resultSet.addAll(Arrays.asList(standard));
		resultSet.addAll(Arrays.asList(cust));

		ArrayList<String> resultList = new ArrayList<String>(resultSet);
		for (int i = 0; i < resultList.size(); i++) {
			String sentence = resultList.get(i);
			stringBuilder.append(sentence);
			if (i < resultList.size() - 1) {
				stringBuilder.append(CON_LINE_BREAK);
			}
		}

		return stringBuilder.toString();

	}

}
