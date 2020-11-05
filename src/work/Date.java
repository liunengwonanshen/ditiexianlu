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

//处理数据
public class Date {
	public static Set<List<Station>> lineSet=new LinkedHashSet<>();//存放所有站点
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
			//将站点放入lineSet
			//将站点放入line
			for(int i=1;i<collection.length;i++) {
				boolean flag=true;
				//有换乘
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
				//有环路
				if(i==collection.length-1&&(collection[i].equals(collection[1]))) {
					//此时还没添加最后一站
					line.get(0).getAdjacentstations().add(line.get(line.size()-1));//环路的起始站添加环路的终点站
					line.get(line.size()-1).getAdjacentstations().add(line.get(0));//环路的终点站添加环路的起始站
					flag=false;
				}
				//正常情况下
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
