package work;

public class Result {
	private Station star;//起始站
	private Station end;//当前站点
	private int distance;//距离起始站的距离
	private Station previousstation;//前一站点
	private String line;//所在线路
	private int change;//时候换乘
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
