package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class RandomWords {

	private Random random = new Random();
	private int randomInt;
	ArrayList<String> words;
	private String randomWord;
	Properties prop = new Properties();
	private String randomPlace;
	public final static int NUMBEROFWORDS = 155;

	public RandomWords() {

	}

	public ArrayList<String> selectWords() {
		words = new ArrayList<String>();

		for (int i = 0; i < 5; i++) {
			randomInt = random.nextInt(NUMBEROFWORDS - 1);
			randomPlace = Integer.toString(randomInt);

			try (InputStream input = new FileInputStream("../SketchAll_v2/src/main/words.properties")) {
				prop.load(input);
				randomWord = prop.getProperty(randomPlace);
				//On s'assure que le mot existe bien (indice 0) et n'est pas déjà dans la liste
				if (words.contains(randomWord) || randomWord == null) {	
					System.out.println("oups");
					i--;
				} else {
					words.add(randomWord);	
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return words;

	}

	public static void main(String[] args) {
		System.out.println(new RandomWords().selectWords());
	}
}
