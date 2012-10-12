package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reader {
	
	public static int[] getFolge(File f, int anzahl){
		List<Integer> resultList = new ArrayList<Integer>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			String zeile = null;
			int act = 0;
			while ((zeile = in.readLine()) != null&&act<anzahl) {
				String[] temp = zeile.split(" ");
				for(int i=0; i<temp.length&&act<anzahl; i++){
					resultList.add(Integer.valueOf(temp[i]));
					act++;
				}
			}
		} catch (IOException e) {
			System.out.println("Huups");
			e.printStackTrace();
		}
		
		//return resultList.toArray(new Integer[]{});
		return toIntArray(resultList);
	}
	
	private static int[] toIntArray(List<Integer> list)  {
	    int[] ret = new int[list.size()];
	    int i = 0;
	    for (Integer e : list)  
	        ret[i++] = e.intValue();
	    return ret;
	}
	
	public static void printIntArray(int[] a){
		for(int i=0; i<a.length; i++){
			System.out.print(a[i]+" ");
		}
	}
	
	public static File zufallsFolge(int lowerBound, int upperBound, int anzahl, File f) throws FileNotFoundException, IOException{
		//File datei = new File("z:/folge.dat");
		FileWriter schreiber = new FileWriter(f);
		Random r = new Random();
		for (int i=0;i<anzahl;i++) {
			int zufallszahl = (lowerBound + r.nextInt((upperBound+1) - lowerBound));
			schreiber.write(zufallszahl + " ");
		}
		schreiber.flush();
		return f;
	 }

}
