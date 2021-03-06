package repository.inMemory;

import domain.entities.BaseEntity;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;


import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * In Memory Repository with XML File Storage
 * @author vinczi
 * @param <Type> generic Type
 */
public class GenericXMLRepository<Type extends BaseEntity<Long>>
        extends InMemoryRepository <Long, Type> {
    private final String fileName;
    private final String className;
    private String xmlClassName;

    public GenericXMLRepository(Validator<Type> validator,
                                String fileName,
                                String className) {
        super(validator);
        this.fileName = fileName;
        this.className = className;
        this.xmlClassName = className.substring(className.lastIndexOf('.') + 1);
        this.loadData();
    }

    private void loadData() {
        try {
            //Get Document Builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Build Document
            Document document = builder.parse(new File(this.fileName));
            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();
            //Here comes the root node
            Element root = document.getDocumentElement();
            //System.out.println(root.getNodeName());
            NodeList nList = document.getElementsByTagName(xmlClassName);
            //System.out.println("============================");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    //System.out.println(node.getTextContent());
                    String elementCreationString = node.getTextContent().replaceAll("\n", ",").replaceAll(" ", "").substring(1);
                    elementCreationString = elementCreationString.substring(0, elementCreationString.length() - 1);
                    //System.out.println("Node Name = " + node.getNodeName() + "; Value = " + elementCreationString);

                    Type instance = null;
                    try {
                        instance = (Type) Class.forName(this.className).getConstructor(String.class).newInstance(elementCreationString);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        super.save(instance);
                    } catch (ValidatorException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Type> save(Type entity) throws ValidatorException {
        Optional<Type> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        this.saveToFile(entity);
        return Optional.empty();
    }

    @Override
    public Optional<Type> delete(Long ID) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(this.fileName));
            document.getDocumentElement().normalize();
            NodeList nodes = document.getElementsByTagName(xmlClassName);
            Node rootNode = (Node) document.getDocumentElement();

            for (int i = 0; i < nodes.getLength(); i++) {
                Element elem = (Element) nodes.item(i);
                Element elemID = (Element) elem.getElementsByTagName("ID").item(0);
                String id = elemID.getTextContent();

                if (id.equals(Long.toString(ID))) {
                    elem.getParentNode().removeChild(elem);
                    break;
                }
            }

            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            trimWhitespace(rootNode);
            StreamResult result = new StreamResult(this.fileName);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.delete(ID);
    }

    @Override
    public Optional<Type> update(Type entity) throws ValidatorException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(this.fileName));
            document.getDocumentElement().normalize();
            NodeList nodes = document.getElementsByTagName(xmlClassName);
            Node rootNode = document.getDocumentElement();

            for (int i = 0; i < nodes.getLength(); i++) {
                Element elem = (Element) nodes.item(i);
                Element elemID = (Element) elem.getElementsByTagName("ID").item(0);
                String id = elemID.getTextContent();

                if (id.equals(Long.toString(entity.getId()))) {
                    Node tempNode = (Node) entity.toXML(document);
                    rootNode.replaceChild(tempNode, nodes.item(i));
                    break;
                }
            }

            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            trimWhitespace(rootNode);
            StreamResult result = new StreamResult(this.fileName);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.update(entity);
    }

    private void saveToFile(Type entity) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(this.fileName));
            Element rootNode = document.getDocumentElement();

            rootNode.appendChild(entity.toXML(document));

            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            trimWhitespace(rootNode);
            StreamResult result = new StreamResult(this.fileName);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void trimWhitespace(Node node)
    {
        NodeList children = node.getChildNodes();
        for(int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            if(child.getNodeType() == Node.TEXT_NODE) {
                child.setTextContent(child.getTextContent().trim());
            }
            trimWhitespace(child);
        }
    }
}
