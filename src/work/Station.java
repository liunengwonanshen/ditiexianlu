package work;

import java.util.ArrayList;
import java.util.List;

public class Station {
	private String name;//վ����
	private List<String> line=new ArrayList<>();//������·
	private List<Station> Adjacentstations =new ArrayList<>();//����վ��
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getLine() {
		return line;
	}
	public void setLine(List<String> line) {
		this.line = line;
	}
	public List<Station> getAdjacentstations() {
		return Adjacentstations;
	}
	public void setAdjacentstations(List<Station> adjacentstations) {
		Adjacentstations = adjacentstations;
	}
	public Station(String name,String linename) {
		this.name=name;
		this.line.add(linename);
	}
	
}
