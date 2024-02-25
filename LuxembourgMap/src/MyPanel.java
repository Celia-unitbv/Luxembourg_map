import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.lang.Math;

public class MyPanel extends JPanel {
	private int node_diam = 1;
	private Vector<Node> nodes;
	private Vector<Arc> arcs;
	private LinkedList<Node> shortestPath = new LinkedList<>();
	Node start = null;
	Node finish = null;
	int minimLongitude, maximLongitude, minimLatitude, maximLatitude;

	public MyPanel() {
		nodes = new Vector<Node>();
		arcs = new Vector<Arc>();

		setBorder(BorderFactory.createLineBorder(Color.black));
		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				if (start == null) {
					start = new Node(SelectedNode(e.getPoint()));
					System.out.println("start is " + start.getCoordX() + " " + start.getCoordY());
					start.atNode.color = true;
					repaint();
					return;
				}
				if (finish == null) {
					finish = new Node(SelectedNode(e.getPoint()));
					System.out.println("end is " + finish.getCoordX() + " " + finish.getCoordY());
					finish.atNode.color = true;
					System.out.println("Start is " + start.getCoordX() + " " + start.getCoordY());
					shortestPath = ShortestPath(start.atNode, finish.atNode);
					for(Node node : shortestPath)
					{
						node.color = true;
					}
					repaint();
					return;
				}

				if(start != null && finish != null)
				{
					start.atNode.color = false;
					finish.atNode.color = false;

					for(Node node : shortestPath)
					{
						node.color = false;
					}
					start = new Node(SelectedNode(e.getPoint()));
					start.atNode.color = true;
					finish = null;
					System.out.println("End is " + finish);
					System.out.println("Start is " + start.getCoordX() + " " + start.getCoordY());
				}

				repaint();

			}
		});
		SaxHandler myHandler = new SaxHandler(nodes, arcs);
		minimLongitude = myHandler.getMinimLongitude();
		maximLongitude = myHandler.getMaximLongitude();
		minimLatitude = myHandler.getMinimLatitude();
		maximLatitude = myHandler.getMaximLatitude();
		repaint();
	}

	public LinkedList<Node> ShortestPath(Node source, Node destination) {
		System.out.println("start " + source.getCoordX() + " " + source.getCoordY());
		System.out.println("finish " + destination.getCoordX() + " " + destination.getCoordY());
		source.setDistance(0);

		Set<Node> settledNodes = new HashSet<>();
		Set<Node> unsettledNodes = new HashSet<>();

		unsettledNodes.add(source);

		while (!unsettledNodes.isEmpty()) {
			Node currentNode = getLowestDistanceNode(unsettledNodes);
			unsettledNodes.remove(currentNode);

			if (currentNode.equals(destination)) {
				return currentNode.getShortestPath();
			}

			for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
				Node adjacentNode = adjacencyPair.getKey();
				Integer edgeWeight = adjacencyPair.getValue();

				if (!settledNodes.contains(adjacentNode)) {
					MinDistance(adjacentNode, edgeWeight, currentNode);
					unsettledNodes.add(adjacentNode);
				}
			}
			settledNodes.add(currentNode);
		}

		// No path found
		return new LinkedList<>();
	}

	private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
		Node lowestDistanceNode = null;
		int lowestDistance = Integer.MAX_VALUE;
		for (Node node: unsettledNodes) {
			int nodeDistance = node.getDistance();
			if (nodeDistance < lowestDistance) {
				lowestDistance = nodeDistance;
				lowestDistanceNode = node;
			}
		}
		return lowestDistanceNode;
	}

	private static void MinDistance(Node evaluationNode,
									Integer edgeWeigh, Node sourceNode) {
		Integer sourceDistance = sourceNode.getDistance();
		if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
			evaluationNode.setDistance(sourceDistance + edgeWeigh);
			LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			shortestPath.add(sourceNode);
			evaluationNode.setShortestPath(shortestPath);
		}
	}

	public Node SelectedNode(Point targetPoint) {
		Node closestNode = nodes.get(0);
		double closestDistance = Integer.MAX_VALUE;

		for (Node node : nodes) {
			double distance = distanceNodes(targetPoint, node.getPoint());
			if (distance < closestDistance) {
				closestNode = node;
				closestDistance = distance;
			}
		}

		return closestNode;
	}

	private static double distanceNodes(Point node1, Point node2) {

		double deltaX = node1.getX() - node2.getX();
		double deltaY = node1.getY() - node2.getY();
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}


	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.elementAt(i).color) {
				nodes.elementAt(i).drawNode(g, node_diam, getWidth(), getHeight(), minimLongitude, maximLongitude, minimLatitude, maximLatitude, Color.RED);
			} else {
				nodes.elementAt(i).drawNode(g, node_diam, getWidth(), getHeight(), minimLongitude, maximLongitude, minimLatitude, maximLatitude, Color.BLACK);
			}
		}

		for (Arc a : arcs) {
			a.drawArc(g);
		}
	}

}
