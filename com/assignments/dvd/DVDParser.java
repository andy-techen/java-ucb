package com.assignments.dvd;

import javax.xml.parsers.*;
import javax.xml.stream.*;

import org.xml.sax.*;
import org.w3c.dom.*;

import java.io.*;
import java.util.Set;
import java.util.TreeMap;

public class DVDParser {
	public void read(String filepath) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);

		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
			db.setErrorHandler(new ErrorHandler() {
				public void error(SAXParseException spe) {
					System.err.println(spe);
				}

				public void fatalError(SAXParseException spe) {
					System.err.println(spe);
				}

				public void warning(SAXParseException spe) {
					System.out.println(spe);
				}
			});
		} catch (ParserConfigurationException pce) {
			System.err.println(pce);
			System.exit(1);
		}

		Document doc = null;
		try {
			doc = db.parse(new File(filepath));
		} catch (SAXException se) {
			System.err.println(se);
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
//		NodeList nodeList = doc.getDocumentElement().getChildNodes();
//		echo_nodes(nodeList);
		NodeList yearList = doc.getElementsByTagName("release_year");
		TreeMap<Integer, Integer> tm = get_decades(yearList);
//		tm.forEach((k, v) -> System.out.println(k + ": " + v));
		write_summary(tm);
	}

//	public void echo_nodes(NodeList nodeList) {
//		if (nodeList == null) return;
//		for (int i = 0; i < nodeList.getLength(); i++) {
//			Node child_node = nodeList.item(i);
//			if (child_node.getNodeType() == Node.ELEMENT_NODE) {
//				Element el = (Element) child_node;
//				System.out.println("Tag Name: " + el.getTagName());
//				NamedNodeMap attributes = child_node.getAttributes();
//				Node attribute = attributes.getNamedItem("id");
//				if (attribute != null)
//					System.out.println("Attr: " + attribute.getNodeName() + " = " + attribute.getNodeValue());
//			} else if (child_node.getNodeType() == Node.TEXT_NODE) {
//				Text tn = (Text) child_node;
//				String text = tn.getWholeText().trim();
//				if (text.length() > 0) System.out.println("Text: " + text);
//			}
//			echo_nodes(child_node.getChildNodes());
//		}
//	}

	private TreeMap<Integer, Integer> get_decades(NodeList yearList) {
		TreeMap<Integer, Integer> tm = new TreeMap<>();
		int decade;
		int year;
		int cnt;

		for (int i = 0; i < yearList.getLength(); i++) {
			year = Integer.parseInt(yearList.item(i).getTextContent());
			decade = year - year % 10;

			if (tm.get(decade) == null) {
				cnt = 1;
			} else {
				cnt = tm.get(decade) + 1;
			}
			tm.put(decade, cnt);
		}
		return tm;
	}

	private void write_summary(TreeMap<Integer, Integer> tm) {
		XMLStreamWriter xw = null;
		XMLOutputFactory xof = XMLOutputFactory.newInstance();

		try {
			xw = xof.createXMLStreamWriter(new FileWriter(new File("./com/assignments/dvd/DVDSummary.xml")));
			xw.writeStartDocument("1.0");
			xw.writeCharacters(System.getProperty("line.separator"));
			xw.writeStartElement("DVD");
			xw.writeCharacters(System.getProperty("line.separator"));
			xw.writeCharacters("    ");
			xw.writeStartElement("summary");
			xw.writeCharacters(System.getProperty("line.separator"));

			Set<Integer> decades = tm.keySet();
			for (Integer decade : decades) {
				Integer cnt = tm.get(decade);
				xw.writeCharacters("        ");
				xw.writeStartElement("count");
				xw.writeAttribute("decade", decade.toString());
				xw.writeCharacters(String.valueOf(cnt));
				xw.writeEndElement();
				xw.writeCharacters(System.getProperty("line.separator"));
			}
			xw.writeCharacters("    ");
			xw.writeEndElement();   // end summary
			xw.writeCharacters(System.getProperty("line.separator"));
			xw.writeEndElement();   // end DVD
			xw.writeEndDocument();
			xw.flush();
			xw.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DVDParser dvd_parser = new DVDParser();
		dvd_parser.read("./com/assignments/dvd/dvd.xml");
	}
} 
