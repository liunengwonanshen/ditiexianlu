package work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Dijkstra {
	private static List<Station> S=new ArrayList<>();//S �ռ��Ѿ����ʹ���վ��
	private static Map<Station,Result> hashMap=new HashMap<>();
	
	//�������·��
	public static Result shortRoute(Station star,Station end) {
		//��ʼ��Result
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
		//����Result
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
			//������վ����¾���
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
	//���·��
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
				System.out.println("->����"+hashMap.get(stack.peek()).getLine());
				System.out.println(stack.pop().getName());
			}
			else System.out.println(stack.pop().getName());
			num++;
		}
		System.out.println("һ��������"+num+"վ");
	}
	//����վ���Ƿ�����ͬ��·��
	public static List<String> GetSameLine(Station s1,Station s2){
		List<String> sameline=new ArrayList<>();
		for(String line1:s1.getLine())
			for(String line2:s2.getLine())
				if(line2.equals(line1))
					sameline.add(line2);
		return sameline;
	}
	//Ѱ����һ��վ��
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
