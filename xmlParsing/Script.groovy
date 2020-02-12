import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

import org.xml.sax.SAXException

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

import javax.xml.transform.Transformer
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class Script {
    static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String filepath = "modules.xml";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
        DocumentBuilder builder = factory.newDocumentBuilder()
        Document xml = builder.parse(new File(filepath))

        TransformerFactory transformerFactory = TransformerFactory.newInstance()
        Transformer transformer = transformerFactory.newTransformer()
        DOMSource source = new DOMSource(xml)
        StreamResult result = new StreamResult(new File(filepath))

        Element element = xml.getDocumentElement()

        editElements(element.getChildNodes())
        deleteElements(xml)
        transformer.transform(source, result)
    }

    static void editElements(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i)
            if (node instanceof Element) {
                if (node.getTextContent() == "packer") {
                    Node next = (node.getNextSibling()).getNextSibling()
                    double version = Double.parseDouble(next.getTextContent())
                    if (next.getNodeName() == "version" && version < 2) {
                        next.setTextContent("2.1")
                    }
                }
                if (node.hasChildNodes()) {
                    editElements(node.getChildNodes())
                }
            }
        }
    }

    static void deleteElements(Document xml) {
        Node core = xml.getElementsByTagName("core").item(0)
        while (core.firstChild) {
            core.removeChild(core.firstChild)
        }
        core.getParentNode().removeChild(core)
    }
}
