package mrfoop;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVHandler{

	class Record implements Comparator<Record>{
	
		String name;
		int score;
		int time;

		Record(String name, int score, int time) {
			this.name = name;
			this.score = score;
			this.time = time;
		}

		
		public int compare(Record record, Record record2) {
	        int compareScore = ((Record)record2).score;
	        int compareTime = ((Record)record2).time;
	    	if(compareScore != record.score)
	    		return compareScore - record.score;
	    	else 
	    		return record.time - compareTime;
		}
	}

	private String fileName;
	private BufferedReader br;
	
	private String cvsSplitBy;
	
	public CSVHandler(String fileName){
		this.fileName = fileName;
		cvsSplitBy = ",";
		br = null;
	}

	public ArrayList<Record> readAll(){
		String line = "";

		ArrayList<Record> records = new ArrayList<Record>();
		try{
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {

				String[] elements = line.split(cvsSplitBy);

				records.add(new Record(elements[0], Integer.valueOf(elements[1]), Integer.valueOf(elements[2])));

			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return records;
	}

	public static void main(){
		CSVHandler csv = new CSVHandler("test.csv");
		ArrayList<Record> rec = csv.readAll();
		for(int i = 0; i < rec.size(); i++){
			System.out.println(rec.get(i).name+", "+Integer.toString(rec.get(i).score)+", "+Integer.toString(rec.get(i).time));
		}
	}




  
}