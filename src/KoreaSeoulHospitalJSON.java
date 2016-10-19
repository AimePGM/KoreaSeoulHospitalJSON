import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KoreaSeoulHospitalJSON {
	public static List<String> guList = new ArrayList<String>();
	public static List<String> dongList = new ArrayList<String>();
	public static List<Map> guAndDong = new ArrayList<Map>();
	public static void main(String[] args){
		
		reader();
	
		System.out.println("Gu");
		for (int i = 0; i < guList.size(); i++) {
			System.out.println(guList.get(i));
		}
		System.out.println("Dong");
		for (int i = 0; i < dongList.size(); i++) {
			System.out.println(dongList.get(i));
		}
		System.out.println("Map");
		
		for (int i = 0; i < guAndDong.size(); i++) {
			System.out.print("gu: "+guAndDong.get(i).keySet().toArray()[0]+" dong: "+guAndDong.get(i).get(guAndDong.get(i).keySet().toArray()[0]));
			System.out.println();
		}
		
		System.out.println("\n\n\n");
		System.out.println("{\"name\":\"서울시\",\n\t\"children\":[");
		for (int i = 0; i < guList.size(); i++) {
			
			System.out.println("\t\t{\"name\":\""+guList.get(i)+"\",\n\t\t\"children\":[");
			
			for (int j = 0; j < guAndDong.size(); j++) {
				if(guAndDong.get(j).keySet().toArray()[0].equals(guList.get(i))){
					System.out.print("\t\t\t\t{\"name\":\""+guAndDong.get(j).get(guList.get(i))+"\"}");
					if(j+1 < guAndDong.size()){
						if(guAndDong.get(j+1).keySet().toArray()[0].equals(guList.get(i))){
							System.out.print(",\n");
						}
					}
					
				}
			}
			System.out.println("\n\t\t]\n\t\t},");
		
		}
		System.out.println("]}");
		
		
		
	}
	
	public static void reader(){
		BufferedReader br = null;

		try {

			String sCurrentLine;
			
			
			br = new BufferedReader(new FileReader(new File("/Users/AimeTPGM/Downloads/boo.json")));
			while ((sCurrentLine = br.readLine()) != null) {
				int forDong = 0;
				int forGu = 0;
				int id = 1;
				int startGuIndex = 0;
				int endGuIndex = 0;
				boolean inProgressGu = false;
				
				
				for (int i = 0; i < sCurrentLine.length(); i++) {
					if(sCurrentLine.charAt(i) == 'H') {
						for (int j = i; j < sCurrentLine.length(); j++) {
							if (inProgressGu){
								if(sCurrentLine.charAt(j) == '"'){
									endGuIndex = j;
									inProgressGu = false;
									break;
								}
							}
							
							if(sCurrentLine.charAt(j) == ':'){
								int k = j;
								k++;
								if(sCurrentLine.charAt(k) != 'n'){
									j+=2;
									startGuIndex = j;
									inProgressGu = true;
								}
								else {
									j++;
									startGuIndex = j;
									j+=4;
									endGuIndex = j;
									
									break;
									
								}
								
							}
							
						}
						String gu = sCurrentLine.substring(startGuIndex, endGuIndex);
						if(guList.size() != 0){
							if(!guList.contains(gu)){
								guList.add(gu);
							}
						}
						else {
							guList.add(gu);
							
						}
						
						boolean inProgressDong = false;
						int startDongIndex = 0;
						int endDongIndex = 0;
						
						int startDong = sCurrentLine.indexOf("\"H_KOR_DONG\":");
						if(startDong != -1){
							for (int j = startDong; j < sCurrentLine.length(); j++) {
								if (inProgressDong){
									if(sCurrentLine.charAt(j) == '"'){
										endDongIndex = j;								inProgressDong = false;
										break;
									}
								}
								if (sCurrentLine.charAt(j) == ':'){
									int k = j;
									k++;
									if(sCurrentLine.charAt(k) != 'n'){
										j+=2;
										startDongIndex = j;
										inProgressDong = true;
									}
									else{
										j++;
										startGuIndex = j;
										j+=4;
										endGuIndex = j;
										
										break;
									}
									
									
								}
							}

							
							String dong = sCurrentLine.substring(startDongIndex, endDongIndex);
							if(!dongList.contains(dong)){
								dongList.add(dong);
							}
							Map temp = new HashMap<>();
							temp.put(gu, dong);
							if(!guAndDong.contains(temp)){
								guAndDong.add(temp);
							}
							
							
						}
						
						break;
					}
					
				}
				
		
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
