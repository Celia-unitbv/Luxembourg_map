import java.awt.*;

public class Arc {
	private Node startNode;
	private Node endNode;
	private int weight;

	public Arc(Node startNode, Node endNode, int weight) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.weight = weight;

		startNode.addAdjacentNode(endNode, weight);

		endNode.addAdjacentNode(startNode, weight);
	}

	// Getters
	public Node getStartNode() {
		return startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public int getWeight() {
		return weight;
	}

	// Setters (if you need to modify Arc properties after creation)
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void drawArc(Graphics g) {
		if (startNode != null && endNode != null) {
			g.setColor(Color.BLACK);
			if (startNode.color && endNode.color) {
				g.setColor(Color.RED);
			}
			g.drawLine(startNode.getCoordX(), startNode.getCoordY(), endNode.getCoordX(), endNode.getCoordY());
		}
	}

}
