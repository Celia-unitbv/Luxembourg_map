import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

public class SaxHandler extends DefaultHandler {

    private Vector<Node> nodes = new Vector<>();
    private Vector<Arc> arcs = new Vector<>();
    int minimLongitude = Integer.MAX_VALUE;
    int minimLatitude = Integer.MAX_VALUE;
    int maximLongitude = Integer.MIN_VALUE;
    int maximLatitude = Integer.MIN_VALUE;
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("node")) {
            // Extract node information
            String nodeId = attributes.getValue("id");
            String nodeLongitude = attributes.getValue("longitude");
            if(Integer.parseInt(nodeLongitude) < minimLongitude)
            {
                minimLongitude = Integer.parseInt(nodeLongitude);
            }
            if(Integer.parseInt(nodeLongitude) > maximLongitude)
            {
                maximLongitude = Integer.parseInt(nodeLongitude);
            }
            String nodeLatitude = attributes.getValue("latitude");
            if(Integer.parseInt(nodeLatitude) < minimLatitude)
            {
                minimLatitude = Integer.parseInt(nodeLatitude);
            }
            if(Integer.parseInt(nodeLatitude) > maximLatitude)
            {
                maximLatitude = Integer.parseInt(nodeLatitude);
            }
            nodes.add(new Node(nodeId, nodeLongitude,nodeLatitude));

        } else if (qName.equalsIgnoreCase("arc")) {
            // Extract arc information
            String fromNode = attributes.getValue("from");
            String toNode = attributes.getValue("to");
            String length = attributes.getValue("length");
            arcs.add(new Arc(nodes.get(Integer.parseInt(fromNode)),nodes.get(Integer.parseInt(toNode)), Integer.parseInt(length)));
            // Add more details as required
        }
    }

    public SaxHandler(Vector<Node> listnodes, Vector<Arc> listarcs){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();


            saxParser.parse("C:\\Users\\Celia\\Downloads\\Harta_Luxemburg (2).xml", this);

            // Clear the original lists
            listnodes.clear();
            listarcs.clear();

            // Copy elements
            listnodes.addAll(nodes);
            listarcs.addAll(arcs);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMinimLongitude() {
        return minimLongitude;
    }

    public int getMaximLongitude() {
        return maximLongitude;
    }

    public int getMinimLatitude() {
        return minimLatitude;
    }

    public int getMaximLatitude() {
        return maximLatitude;
    }
}