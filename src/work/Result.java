package work;

public class Result {
	private Station star;//��ʼվ
	private Station end;//��ǰվ��
	private int distance;//������ʼվ�ľ���
	private Station previousstation;//ǰһվ��
	private String line;//������·
	private int change;//ʱ�򻻳�
	public Station getStar() {
		return star;
	}
	public void setStar(Station star) {
		this.star = star;
	}
	public Station getEnd() {
		return end;
	}
	public void setEnd(Station end) {
		this.end = end;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public Station getPreviousstation() {
		return previousstation;
	}
	public void setPreviousstation(Station previousstation) {
		this.previousstation = previousstation;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public int getChange() {
		return change;
	}
	public void setChange(int change) {
		this.change = change;
	}
	
}
