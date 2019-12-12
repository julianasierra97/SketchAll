package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class RandomWords {

	private Random randomPlace;
	private int pos;
	private String word;
	Properties prop = new Properties();
	private String wordPlace;
	public final static int NUMBEROFWORDS = 155;

	public RandomWords() {

	}

	public String showWord() {
		randomPlace = new Random();

		pos = randomPlace.nextInt(NUMBEROFWORDS - 1);

		wordPlace = Integer.toString(pos);

		try (InputStream input = new FileInputStream("../SketchAll_v2/src/main/words.properties")) {
			// System.out.println("input" + input);
			prop.load(input);

			word = prop.getProperty(wordPlace);

			return word;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		RandomWords rand = new RandomWords();
		String word;
//		for (int i = 0; i < 10; i++) {
//
//			word = rand.showWord();
//			System.out.println(word);
//		}
		word = rand.showWord();
		System.out.println(word);
	}
}
