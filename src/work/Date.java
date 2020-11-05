package work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

//��������
public class Date {
	public static Set<List<Station>> lineSet=new LinkedHashSet<>();//�������վ��
	public Date() {};
	public Date(String name) throws IOException{
		File file=new File(name);
		Scanner input=new Scanner(file);
		InputStreamReader reader = new InputStreamReader( new FileInputStream(file),"UTF-8");
		BufferedReader br = new BufferedReader(reader); 
		String s;
		while((s=br.readLine())!=null) {
			//String s=br.readLine();
			//System.out.println(s);
			List<Station> line=new ArrayList<>();
			String[] collection=s.split(" ");
			String linename=collection[0];
			//��վ�����lineSet
			//��վ�����line
			for(int i=1;i<collection.length;i++) {
				boolean flag=true;
				//�л���
				for(List<Station> l:lineSet) {
					for(int j=0;j<l.size();j++) {
						if(l.get(j).getName().equals(collection[i])) {
							List<String> newline=l.get(j).getLine();
							newline.add(linename);
							l.get(j).setLine(newline);
							line.add(l.get(j));
							flag=false;
							break;
						}
					}
					if(!flag) break;
				}
				//�л�·
				if(i==collection.length-1&&(collection[i].equals(collection[1]))) {
					//��ʱ��û������һվ
					line.get(0).getAdjacentstations().add(line.get(line.size()-1));//��·����ʼվ��ӻ�·���յ�վ
					line.get(line.size()-1).getAdjacentstations().add(line.get(0));//��·���յ�վ��ӻ�·����ʼվ
					flag=false;
				}
				//���������
				if(flag) line.add(new Station(collection[i],linename));
			}
			
			for(int i=0;i<line.size();i++) {
				List<Station> LinkedStation=line.get(i).getAdjacentstations();
				if(i==0) {
					LinkedStation.add(line.get(i+1));
					line.get(i).setAdjacentstations(LinkedStation);
				}
				else if(i==line.size()-1) {
					LinkedStation.add(line.get(i-1));
					line.get(i).setAdjacentstations(LinkedStation);
				}
				else {
					LinkedStation.add(line.get(i+1));
					LinkedStation.add(line.get(i-1));
					line.get(i).setAdjacentstations(LinkedStation);
				}
			}
			//s=br.readLine();
			lineSet.add(line);
			
			//System.out.println(lineSet);
		}
		
	}
	
}
