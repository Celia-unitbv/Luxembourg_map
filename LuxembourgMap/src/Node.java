import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Node
{
	private int coordX;
	private int coordY;
	String longitude;
	String latitude;
	private int number;
	public boolean color = false;
	Node atNode = null;
	private LinkedList<Node> shortestPath = new LinkedList<>();
	private Map<Node, Integer> adjacentNodes = new HashMap<>();
	public Node(int coordX, int coordY, int number)
	{
		this.coordX = coordX;
		this.coordY = coordY;
		this.number = number;
	}
	public Node(Node node)
	{
		coordX = node.coordX;
		coordY = node.coordY;
		number = node.number;
		longitude = node.longitude;
		latitude = node.latitude;
		color = node.color;
		shortestPath = node.shortestPath;
		adjacentNodes = node.adjacentNodes;
		atNode = node;
	}

	public Node(String id, String longitude, String latitude){
		this.number = Integer.parseInt(id);
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public int getCoordX() {
		return coordX;
	}
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}
	public int getCoordY() {
		return coordY;
	}
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public void drawNode(Graphics g, int node_diam, int panelWidth, int panelHeight, int minimLongitude, int maximLongitude, int minimLatitude, int maximLatitude, Color color) {
		double longitudeScale = (double) panelWidth / (maximLongitude - minimLongitude);
		double latitudeScale = (double) panelHeight / (maximLatitude - minimLatitude);

		double normLongitude = Double.parseDouble(longitude) - minimLongitude;
		double normLatitude = Double.parseDouble(latitude) - minimLatitude;

		coordX = (int) (normLongitude * longitudeScale);
		coordY = panelHeight - (int) (normLatitude * latitudeScale);

		g.setColor(color);
		g.fillOval(coordX, coordY, node_diam, node_diam);
		g.drawOval(coordX, coordY, node_diam, node_diam);
	}


	public LinkedList<Node> getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(LinkedList<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}

	private int distance = Integer.MAX_VALUE; // Initialize to a large value as infinity

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}


	// Other properties and methods...

	public Map<Node, Integer> getAdjacentNodes() {
		return adjacentNodes;
	}

	public void addAdjacentNode(Node neighbor, int edgeWeight) {
		adjacentNodes.put(neighbor, edgeWeight);
	}

	public Point getPoint() {
		return new Point(coordX, coordY);
	}

}
