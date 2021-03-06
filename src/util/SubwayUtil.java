package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import model.Result;
import model.Station;

public class SubwayUtil {
	private static List<Station> analysisList = new ArrayList<>();       //S //已经分析过的站点
	private static HashMap<Station, Result> resultMap = new HashMap<>();  //结果集
	private static Station getNextStation() {    //获取下一个需要分析的站点
        int min=999999;
        Station rets = null;
        Set<Station> stations = resultMap.keySet();//返回所有Station的集合
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
	//获取l1和l2中相同的线路
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
	
	//dijkstra计算最短路径
	public static Result shortestPath(Station star, Station end) {  
		for(List<Station> l:DateBuilder.lineSet) {  //构造结果集
			for(int k=0;k<l.size();k++) {
                Result result = new Result();
                result.setStar(star);
                result.setEnd(l.get(k));
                result.setDistance(999999);
                result.setLinechange(0);
                resultMap.put(l.get(k), result);
			}
		}
		 //初始化结果集
		for(Station s:star.getLinkStations()) { 
			resultMap.get(s).setDistance(1);//相邻站点的距离为1
			resultMap.get(s).setPassStations(star);//上一个站点为star
			List<String> samelines=getSameLine(star.getLine(),s.getLine());//将开始站点和相邻站点所在的线路进行比较
			resultMap.get(s).setLine(samelines.get(0));
		}
		resultMap.get(star).setDistance(0);
		
		analysisList.add(star);
        Station nextstation = getNextStation(); 		//计算下一个需要分析的站点
        while(nextstation!=null) {  //循环计算每一个站点的最短路径
        	for(Station s:nextstation.getLinkStations()) {
        		if(resultMap.get(nextstation).getDistance()+1<resultMap.get(s).getDistance()) {  //更新最短路径
        			resultMap.get(s).setDistance(resultMap.get(nextstation).getDistance()+1);
        			resultMap.get(s).setPassStations(nextstation);
        			List<String> samelines=getSameLine(nextstation.getLine(),s.getLine());
        			if(!samelines.contains(resultMap.get(nextstation).getLine())) {  //需要换乘
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
	
	public static List<Station> getLineStation(String linename){  //获取指定路线的所有站点
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
	
	public static List<String> getPath(Result r){  //根据result生成乘车路线
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
				path.add("->换乘"+resultMap.get(tmp.peek()).getLine());
				path.add(tmp.pop().getName());
			}
			else
				path.add(tmp.pop().getName());
		}
		if(r.getLinechange()==1) {
			path.add("->换乘"+r.getLine());
			path.add(r.getEnd().getName());
		}
		else
		    path.add(r.getEnd().getName());
		return path;
	}
}
