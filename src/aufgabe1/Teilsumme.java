package aufgabe1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import reader.Reader;

//Teilsumme.java
//Algorithmen und Datenstrukturen, Kapitel 2.1
//Autor: Prof. Solymosi, (c) 2000: APSIS GmbH
//Datum: 14. April 2000

public class Teilsumme {
	
	private static File maxTeilsumme = new File("Z:\\win7\\indigo\\AD\\maxTeilsumme3.csv");
	
	int maxTeilsumme3 (final int[] folge,int anzahl,boolean zaehlen, int anzahlDerZugriffe, long timeOhne) {
		/*
		 * Varaiblen zum Messen
		 */
		int maxSumme = Integer.MIN_VALUE;
		int index1 = Integer.MIN_VALUE;
		int index2 = Integer.MIN_VALUE;
		//int anzahlDerZugriffe = 0;
		List<Integer> resultFolge = null;
		long startTime = System.currentTimeMillis();
		
		//Algorithmus
		for (int von = 0; von < folge.length; von++){
			if(zaehlen)
			{
				anzahlDerZugriffe++;
			}
			for (int bis = von; bis < folge.length; bis++) { // Summe bilden
				int summe = 0;
				if(zaehlen)
				{
					anzahlDerZugriffe++;
				}
				List<Integer> temp = new ArrayList<Integer>();
				for (int i = von; i <= bis; i++){
					if(zaehlen)
					{
						anzahlDerZugriffe++;
					}
					int akt = folge[i];
					summe += akt;
					temp.add(akt);
					//maxSumme = Math.max(summe, maxSumme); // Summe ï¿½berprï¿½fen, ob grï¿½ï¿½er
					if(summe > maxSumme){
						maxSumme = summe;
						index1=von;
						index2=bis;
					
					}
				}
			}
		}
		long finishTime = System.currentTimeMillis() - startTime;
		if(zaehlen){
			maxTeilsumme3(folge, anzahl, false,anzahlDerZugriffe, finishTime);
		}else{
			printResult("maxTeilsumme3", maxSumme, resultFolge, anzahlDerZugriffe,index1,index2,finishTime,timeOhne,anzahl);
		}
		return maxSumme;
	}
	
	void printResult(String name, int result, List<Integer> teilfolge, int anzahlDerZugriffe, int index1, int index2, long time, long timeOhne, int anzahl){
		System.out.println("Name: "+name+"\nMax.Teilsumme: "+result+"\nAnazhl der Zugriffe: "+anzahlDerZugriffe+"\nIndex1: "+index1+"\nIndex2: "+index2+"\nZeit fÃ¼r die Berechnung: "+time+" ms");
		System.out.println("----------------------------------------");
		try {
			FileWriter schreiber = new FileWriter(maxTeilsumme,true);
			schreiber.write(anzahl+";"+name+";"+result+";"+index1+";"+index2+";"+time+";"+timeOhne+";"+anzahlDerZugriffe+System.getProperty("line.separator"));
			schreiber.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	int maxTeilsumme2(final int[] folge, int anzahl) {
		/*
		 * Varaiblen zum Messen
		 */
		int maxSumme = Integer.MIN_VALUE;
		int index1 = Integer.MIN_VALUE;
		int index2 = Integer.MIN_VALUE;
		int anzahlDerZugriffe = 0;
		int anzahlDerMatrixZugriffe = 0;
		List<Integer> resultFolge = null;
		long startTime = System.currentTimeMillis();
		
		
		int[][] s = new int [folge.length] [folge.length];
		// Tabelle fï¿½r Teilsummen: fï¿½r i ( j gilt
		// s[i][j] == Teilsumme i bis j == folge[i]+folge[i+1]+...+folge[j]
		for (int i = 0; i < folge.length; i++){
			anzahlDerMatrixZugriffe++;
			for (int j = 0; j < folge.length; j++){
				anzahlDerMatrixZugriffe++;
				s[i][j] = 0;
			}
		}
		int max = Integer.MIN_VALUE;
		s[0][0] = folge[0]; // Teilsumme 0 bis 0
		anzahlDerZugriffe++;
		for (int bis = 1; bis < folge.length; bis++){
			anzahlDerMatrixZugriffe+=3;
			s[0][bis] = s [0][bis - 1] + folge[bis];
			anzahlDerZugriffe++;
		}
		// Teilsummen 0 bis 1, ..., 0 bis n-1
		// auf die vorherige Teilsumme wird das nï¿½chste Element addiert
		for (int von = 1; von < folge.length; von++){
			for (int bis = von; bis < folge.length; bis++){
				anzahlDerMatrixZugriffe+=3;
				s[von][bis] = s[von - 1][bis] - folge[von - 1];
				anzahlDerZugriffe++;
			}
		}
		// Teilsumme fï¿½ngt um ein Element nach rechts an: um dieses Element kleiner
		// Tabelle s wurde gefï¿½llt; jetzt kann das maximale Element gesucht werden:
		for (int von = 0; von < folge.length; von++){
			for (int bis = 0; bis < folge.length; bis++){
				//max = Math.max(max, s[von][bis]); // Summe ï¿½berprï¿½fen, ob grï¿½ï¿½er
				int akt = s[von][bis];
				if(akt > max){
					max = akt;
					index1 = von;
					index2 = bis;
				}
			}
		}
		long finishTime = System.currentTimeMillis() - startTime;
		//printResult("maxTeilsumme2", (folge.length == 0 ? 0 : max), resultFolge, anzahlDerZugriffe,index1,index2,finishTime, anzahl);
	if (folge.length == 0)
		return 0; // die Summe der leeren Teilfolge ist 0
	else
		return max;
	}
	
	
	private int rechtesRandMax(final int[] folge, int links, int rechts) {
		// requires 0 <= links <= rechts < folge.length
		// berechnet rechtes Randmaximum in folge zwischen links und rechts
		int bisherMax = Integer.MIN_VALUE;
		int bisherSum = 0;
		for (int i = rechts; i >= links; i--) {
			bisherSum += folge[i];
			bisherMax = Math.max(bisherMax, bisherSum);
		};
		return bisherMax;
	}
	
	
	
	private int linkesRandMax(final int[] folge, int links, int rechts) {
		// requires 0 <= links <= rechts < folge.length
		// berechnet linkes Randmaximum in folge zwischen links und rechts
		int bisherMax = Integer.MIN_VALUE;
		int bisherSum = 0;
		for (int i = links; i <= rechts; i++) {
			bisherSum += folge[i];
			bisherMax = Math.max(bisherMax, bisherSum);
		};
		return bisherMax;
	}
	
	
	private int maxTeilsummeRekursiv(final int[]folge,int links,int rechts) {
		// requires 0 <= links <= rechts < folge.length
		// berechnet maximale Teilsumme in folge zwischen links und rechts
		if (links == rechts) // nur ein Element
			return Math.max(0, folge[links]);
		else {
			final int mitte = (rechts + links)/2;
			final int maxLinks = maxTeilsummeRekursiv(folge, links, mitte);
			final int maxRechts = maxTeilsummeRekursiv(folge, mitte+1, rechts);
			final int rechtesMax = rechtesRandMax(folge, links, mitte);
				// linke Hï¿½lfte
			final int linkesMax = linkesRandMax(folge, mitte+1, rechts);
				// rechte Hï¿½lfte
			return Math.max(maxRechts, Math.max(maxLinks, rechtesMax+linkesMax));
		}
	}
	
	
	public int maxTeilsummeRekursiv(final int[] folge) {
		// berechnet maximale Teilsumme von folge
		return maxTeilsummeRekursiv(folge, 0, folge.length-1);
	}
	
	
	int maxTeilsumme1(final int[] folge) {
		int bisherMax = 0;
		int randMax = 0;
		for (int i = 0; i < folge.length; i++) {
			randMax = Math.max(0, randMax + folge[i]);
			bisherMax = Math.max(bisherMax, randMax);
		};
		return bisherMax;
	}

//-->
	public static void main(String[] args) {
		Teilsumme t = new Teilsumme();
//		final int[] folge = {+5, -8, +3, +3, -5, +7, -2, -7, +3, +5};
		//final int[] folge = {+5, -8, +3, +3, -5, +2, +7, -2, -7, +3, -1, +5};
		int[] folge;
		FileWriter schreiber;
		try {
			schreiber = new FileWriter(maxTeilsumme);
			schreiber.write("Anzahl;Algorithmus;maxTeilsumme;Index1;Index2;Zeit (inkl. Zählen);Zeit (exkl. Zählen);Summe aller Zugriffe\n");
			schreiber.flush();
			schreiber.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			File f = Reader.zufallsFolge(-100, 100, 1000, new File("Z:\\win7\\indigo\\AD\\folge.dat"));
			for(int i=100;i<=1000;i+=100){
				folge = Reader.getFolge(f,i);
				t.maxTeilsumme3(folge,i,true,0,0);
			}
			//System.out.println("maxTeilsumme3 = " + erg);

			//erg = t.maxTeilsumme2(folge);
			//System.out.println("maxTeilsumme2 = " + erg);

			//erg = t.maxTeilsummeRekursiv(folge);
			//System.out.println("maxTeilsummRekursiv = " + erg);

			//erg = t.maxTeilsumme1(folge);
			//System.out.println("maxTeilsumme1 = " + erg);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	Reader.printIntArray(folge);
		
	}
	
	
}


