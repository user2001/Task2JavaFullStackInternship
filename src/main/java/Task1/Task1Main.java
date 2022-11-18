package Task1;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Task1Main {
    public static void main(String argv[]) {
        try {
            File inputXmlFile = new File("input.xml");
            FileWriter outputXmlFile = new FileWriter("result.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document inputDoc = dBuilder.parse(inputXmlFile);
            inputDoc.getDocumentElement().normalize();

            Document outputDoc = dBuilder.newDocument();
            Element root = outputDoc.createElement("persons");
            outputDoc.appendChild(root);
            NodeList nodeList = inputDoc.getElementsByTagName("person");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Element person = outputDoc.createElement("person");
                root.appendChild(person);
                Node nNode = nodeList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    setValueForOutputFile(outputDoc, person, (Element) nNode);
                }
            }
            printToXml(outputXmlFile, outputDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setValueForOutputFile(Document outputDoc, Element personInOutFile, Element infoFromInputFile) {
        String personName = infoFromInputFile.getAttribute("name") + " " +
                infoFromInputFile.getAttribute("surname");
        String personBirthDate = infoFromInputFile.getAttribute("birthDate");
        Attr name = outputDoc.createAttribute("name");
        name.setValue(personName);
        personInOutFile.setAttributeNode(name);
        Attr birthDay = outputDoc.createAttribute("birthDate");
        birthDay.setValue(personBirthDate);
        personInOutFile.setAttributeNode(birthDay);
    }

    private static void printToXml(FileWriter outputXmlFile, Document outputDoc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        DOMSource domSource = new DOMSource(outputDoc);
        StreamResult streamResult = new StreamResult(outputXmlFile);
        transformer.transform(domSource, streamResult);
    }
}
