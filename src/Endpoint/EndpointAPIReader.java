package Endpoint;

import Exceptions.ClassTypeException;
import Exceptions.DataDoesNotMatchModelException;
import Exceptions.InvalidUrlException;
import Exceptions.NodeInstantiationException;
import Models.ApiModel;
import Models.Pagination;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mattias on 1/11/17.
 * Generic version of the Box class.
 * @param <T> the type of the value being boxed
 */
public class EndpointAPIReader<T> {

    private final Class<T> typeParameterClass;

    public EndpointAPIReader(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public ArrayList<T> getDataListFromUri(String uri) throws NodeInstantiationException, DataDoesNotMatchModelException, InvalidUrlException {
        Document doc = getXMLDocument(uri);

        ApiModel annotation = typeParameterClass.getAnnotation(ApiModel.class);

        if (annotation.pagination()) {
            Node documentNode = doc.getDocumentElement();

            NodeReader<Pagination> paginationNodeReader = new NodeReader<Pagination>(Pagination.class);
            NodeReader<T> dataNodeReader = new NodeReader<>(typeParameterClass);



            Node paginationNode = paginationNodeReader.findNodeInChilds(documentNode);
            Pagination page = paginationNodeReader.nodeToObject(paginationNode);
            ArrayList<T> dataList = new ArrayList<T>(page.getTotalhits());

            if (page.getTotalhits() > 0) {
                dataList.addAll(dataNodeReader.getObjectListFromNode(documentNode, annotation.container()));

                while (page.hasNextPage()) {
                    documentNode = getXMLDocument(page.getNextpage()).getDocumentElement();
                    paginationNode = paginationNodeReader.findNodeInChilds(documentNode);
                    page = paginationNodeReader.nodeToObject(paginationNode);

                    dataList.addAll(dataNodeReader.getObjectListFromNode(documentNode, annotation.container()));
                }
            }

            return dataList;
        }

        return null;
    }

    public T getDataFromUri(String uri) throws NodeInstantiationException, DataDoesNotMatchModelException, InvalidUrlException {
        Document doc = getXMLDocument(uri);

        NodeReader<T> dataNodeReader = new NodeReader<>(typeParameterClass);

        Node dataNode = dataNodeReader.findNodeInChilds(doc.getDocumentElement());

        return dataNodeReader.nodeToObject(dataNode);
    }



    private Document getXMLDocument(String uri) throws DataDoesNotMatchModelException, InvalidUrlException {
        try {
            URL url = new URL(uri);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            try {
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/xml");

                InputStream xml = connection.getInputStream();

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(xml);
                return doc;
            } catch (IOException e) {
                throw new DataDoesNotMatchModelException("Server responded with file not found");
            }
            catch (ParserConfigurationException | SAXException e){
                throw new DataDoesNotMatchModelException("Response from server does not match xml format");
            } finally {
                connection.disconnect();
            }
        }
        catch (IOException e) {
            throw new InvalidUrlException("The url for the download is malformed");
        }
    }

}
