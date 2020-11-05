package work;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("请输入要查找的地铁文件:");
		Scanner input=new Scanner(System.in);
		String filename=input.nextLine();
		try {
			new Date(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("请输入要查找的起始站点:");
		input=new Scanner(System.in);
		String s1=input.next();
		System.out.print("请输入要查找的终点站:");
		input=new Scanner(System.in);
		String s2=input.next();
		Station star=null;
		Station end=null;
		for(List<Station> l:Date.lineSet)
			for(Station s:l) {
				if(s1.equals(s.getName())) 
					star=s;
				if(s2.equals(s.getName()))
					end=s;
			}
		if(star==null )
			System.out.println("输入的起始站点不存在");
		else if(end==null) System.out.println("输入的终点站不存在");
		else {
			Result r=Dijkstra.shortRoute(star, end);
			Dijkstra.Path(r);
		}

	}

}
