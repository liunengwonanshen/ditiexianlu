package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import model.Result;
import model.Station;

public class SubwayUtil {
	private static List<Station> analysisList = new ArrayList<>();       //S //�Ѿ���������վ��
	private static HashMap<Station, Result> resultMap = new HashMap<>();  //�����
	private static Station getNextStation() {    //��ȡ��һ����Ҫ������վ��
        int min=999999;
        Station rets = null;
        Set<Station> stations = resultMap.keySet();//��������Station�ļ���
        for (Station station : stations) {
            if (analysisList.contains(station)) {
                continue;
            }
            //
            Result result = resultMap.get(station);
            if (result.getDistance() < min) {
                min = result.getDistance();
                rets = result.getEnd();
            }
        }
        return rets;
    }	
	//��ȡl1��l2����ͬ����·
	private static List<String> getSameLine(List<String> l1,List<String> l2) {  
		List<String> sameline=new ArrayList<String>();
		for(String la:l1) {
			for(String lb:l2) {
				if(la.equals(lb))
					sameline.add(la);
			}
		}
		return sameline;
	}
	
	//dijkstra�������·��
	public static Result shortestPath(Station star, Station end) {  
		for(List<Station> l:DateBuilder.lineSet) {  //��������
			for(int k=0;k<l.size();k++) {
                Result result = new Result();
                result.setStar(star);
                result.setEnd(l.get(k));
                result.setDistance(999999);
                result.setLinechange(0);
                resultMap.put(l.get(k), result);
			}
		}
		 //��ʼ�������
		for(Station s:star.getLinkStations()) { 
			resultMap.get(s).setDistance(1);//����վ��ľ���Ϊ1
			resultMap.get(s).setPassStations(star);//��һ��վ��Ϊstar
			List<String> samelines=getSameLine(star.getLine(),s.getLine());//����ʼվ�������վ�����ڵ���·���бȽ�
			resultMap.get(s).setLine(samelines.get(0));
		}
		resultMap.get(star).setDistance(0);
		
		analysisList.add(star);
        Station nextstation = getNextStation(); 		//������һ����Ҫ������վ��
        while(nextstation!=null) {  //ѭ������ÿһ��վ������·��
        	for(Station s:nextstation.getLinkStations()) {
        		if(resultMap.get(nextstation).getDistance()+1<resultMap.get(s).getDistance()) {  //�������·��
        			resultMap.get(s).setDistance(resultMap.get(nextstation).getDistance()+1);
        			resultMap.get(s).setPassStations(nextstation);
        			List<String> samelines=getSameLine(nextstation.getLine(),s.getLine());
        			if(!samelines.contains(resultMap.get(nextstation).getLine())) {  //��Ҫ����
        				resultMap.get(s).setLine(samelines.get(0));
        				resultMap.get(s).setLinechange(1);
        			}
        			else {
        				resultMap.get(s).setLine(resultMap.get(nextstation).getLine());
        			}
        		}
        	}
        	analysisList.add(nextstation); 
        	nextstation = getNextStation();
        }    
        return resultMap.get(end);
    }
	
	public static List<Station> getLineStation(String linename){  //��ȡָ��·�ߵ�����վ��
		int flag=1;
		for (List<Station> l:DateBuilder.lineSet) {
			flag=1;
			for(Station s :l) {
				if(!s.getLine().contains(linename))
					flag=0;
			}
			if(flag==1)
				return l;
		}	
		return null;
	}
	
	public static List<String> getPath(Result r){  //����result���ɳ˳�·��
		List<String> path=new ArrayList<String>();
		Stack<Station> tmp=new Stack<Station>();
		Station s=r.getPassStations();
		while(!s.equals(r.getStar())) {
			tmp.push(s);
			s=resultMap.get(s).getPassStations();
		}
		path.add(r.getStar().getName());
		while(!tmp.empty()) {
			if(resultMap.get(tmp.peek()).getLinechange()==1) {
				path.add("->����"+resultMap.get(tmp.peek()).getLine());
				path.add(tmp.pop().getName());
			}
			else
				path.add(tmp.pop().getName());
		}
		if(r.getLinechange()==1) {
			path.add("->����"+r.getLine());
			path.add(r.getEnd().getName());
		}
		else
		    path.add(r.getEnd().getName());
		return path;
	}
}
