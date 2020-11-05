package work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Dijkstra {
	private static List<Station> S=new ArrayList<>();//S 收集已经访问过的站点
	private static Map<Station,Result> hashMap=new HashMap<>();
	
	//计算最短路径
	public static Result shortRoute(Station star,Station end) {
		//初始化Result
		for(List<Station> stations:Date.lineSet) {
			for(Station station: stations) {
				Result result=new Result();
				result.setStar(star);
				result.setEnd(station);
				result.setDistance(999999);
				result.setChange(0);
				hashMap.put(station, result);
			}
		}
		//更新Result
		for(Station station: star.getAdjacentstations()) {
			hashMap.get(station).setDistance(1);
			hashMap.get(station).setPreviousstation(star);
			List<String> line=GetSameLine(star,station);
			hashMap.get(station).setLine(line.get(0));
		}
		hashMap.get(star).setDistance(0);
		S.add(star);
		Station next=FindNextStation();
		while(next!=null) {
			//将所有站点更新距离
			for(Station s:next.getAdjacentstations()) {
				if(hashMap.get(next).getDistance()+1<hashMap.get(s).getDistance()) {
					hashMap.get(s).setDistance(hashMap.get(next).getDistance()+1);
					hashMap.get(s).setPreviousstation(next);
				
					List<String> sameline=GetSameLine(next,s);
					if(!sameline.contains(hashMap.get(next).getLine())) {
						hashMap.get(s).setLine(sameline.get(0));
						hashMap.get(s).setChange(1);
					}
					else hashMap.get(s).setLine(hashMap.get(next).getLine());
				}
			}
			S.add(next);
			//System.out.print(next.getName()+" ");
			next=FindNextStation();
		}
		return hashMap.get(end);
		
	}
	//获得路径
	public static void Path(Result end){
		Stack<Station> stack=new Stack<>();
		stack.push(end.getEnd());
		Station s=end.getPreviousstation();
		while(!s.equals(end.getStar())) {
			stack.push(s);
			//System.out.print(s.getName());
			s=hashMap.get(s).getPreviousstation();
		}
		stack.push(end.getStar());
		int num=0;
		while(!stack.isEmpty()) {
			if(hashMap.get(stack.peek()).getChange()==1) {
				System.out.println("->换乘"+hashMap.get(stack.peek()).getLine());
				System.out.println(stack.pop().getName());
			}
			else System.out.println(stack.pop().getName());
			num++;
		}
		System.out.println("一共经历了"+num+"站");
	}
	//两个站点是否有相同的路线
	public static List<String> GetSameLine(Station s1,Station s2){
		List<String> sameline=new ArrayList<>();
		for(String line1:s1.getLine())
			for(String line2:s2.getLine())
				if(line2.equals(line1))
					sameline.add(line2);
		return sameline;
	}
	//寻找下一个站点
	public static Station FindNextStation() {
		int min=999999;
		Station st=null;
		Set<Station> set=hashMap.keySet();
		for(Station station:set) {
			if(S.contains(station)) continue;
			Result result=hashMap.get(station);
			if(result.getDistance()<min) {
				min=result.getDistance();
				st=result.getEnd();
			}
		}
		return st;
	}
	
}
