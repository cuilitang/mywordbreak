package cui.litang.test;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Dictionary.
 */
public class DictionaryTest {

	Dictionary dictionary;

	@Before
	public void before() {
		dictionary = new Dictionary();
	}

	@After
	public void after() {
		dictionary = null;
	}

	@Test
	public void testDic2Map() {

		HashMap<Character, Map> dic2Map = dictionary.dic2Map("{he llo}");
		byte[] expected = dic2Map.toString().getBytes();
		byte[] actual = "{ ={hello=[he llo]}, h={e={l={l={o={.={}}}}}}}".getBytes();

		assertArrayEquals("failure - byte arrays not same", expected, actual);

	}

	@Test
	public void testLetter2Word() {

		ArrayList<String> wordList = new ArrayList<String>();
		HashMap<Character, Map> dic2Map = dictionary.dic2Map("{he llo}");
		dictionary.letter2Word(wordList, "hello", dic2Map);

		byte[] expected = dictionary.sentenceList.toString().getBytes();
		byte[] actual = "[[hello]]".getBytes();

		assertArrayEquals("failure - byte arrays not same", expected, actual);

	}

	@Test
	public void testWord2Sentence() {
		ArrayList<String> wordList = new ArrayList<String>();
		HashMap<Character, Map> dic2Map = dictionary.dic2Map("{he llo,hello}");
		dictionary.letter2Word(wordList, "hello", dic2Map);
		String word2Sentence = dictionary.word2Sentence(dictionary.sentenceList, dic2Map.get(' '));
		System.out.println(word2Sentence);

		byte[] expected = word2Sentence.getBytes();
		byte[] actual = "hello\nhe llo".getBytes();

		assertArrayEquals("failure - byte arrays not same", expected, actual);

	}

}
