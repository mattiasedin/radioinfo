package Endpoint;

import Exceptions.*;
import Models.ApiModel;
import Models.Pagination;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by mattias on 1/11/17.
 * <p>
 * Generic Endpoint Reader to download arbitrary data and cast to typed object.
 * @param <T> the type of the values to get from the Endpoint. This type has to use the annotation interface ApiModel
 *           and ApiModelData for its setter methods for correctly reading the api.
 * @see Models.ApiModel
 * @see Models.ApiModelData
 */
public class EndpointAPIReader<T> {

    private final Class<T> typeParameterClass;

    /**
     * Constructor for class.
     * @param typeParameterClass the typed class to match the data to.
     */
    public EndpointAPIReader(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    /**
     * Donwloads the data from internet using http connection and converts to the specified type.
     * @param uri the url to download the data from.
     * @return list of data if typed class extends ApiModel and has pagination else null
     * @throws XMLParseExeption if the downloaded object is not a type of xml
     * @throws InternetConnectionException if there is no internet connection
     * @throws MalformedURLException if the connection to the specified url could not be made
     * @throws ModelParseException if the data does not match the model in any way
     * @throws NodeInstantiationException if the typed class have errors in form of access violation or not found
     * @see ApiModel
     */
    public ArrayList<T> getDataListFromUri(String uri) throws XMLParseExeption, InternetConnectionException, MalformedURLException, ModelParseException, NodeInstantiationException {
        if (!typeParameterClass.isAnnotationPresent(ApiModel.class))
            return null;

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

    /**
     * Donwloads the data from internet using http connection and converts to the specified type.
     * @param uri the url to download the data from.
     * @return downloaded object if typed class extends ApiModel and has no pagination else null
     * @throws NodeInstantiationException if the typed class have errors in form of access violation or not implementing the interfaces
     * @throws ModelParseException if the data does not match the model in any way
     * @throws InvalidUrlException if the connection to the specified url could not be made
     * @see ApiModel
     */


    /**
     * Donwloads the data from internet using http connection and converts to the specified type.
     * @param uri the url to download the data from.
     * @return downloaded object if typed class exte
     * @throws XMLParseExeption if the downloaded object is not a type of xml
     * @throws InternetConnectionException if there is no internet connection
     * @throws MalformedURLException if the connection to the specified url could not be made
     * @throws ModelParseException if the data does not match the model in any way
     * @throws NodeInstantiationException if the typed class have errors in form of access violation or not found
     */
    public T getDataFromUri(String uri) throws XMLParseExeption, InternetConnectionException, MalformedURLException, ModelParseException, NodeInstantiationException {
        if (!typeParameterClass.isAnnotationPresent(ApiModel.class))
            return null;

        Document doc = getXMLDocument(uri);

        ApiModel annotation = typeParameterClass.getAnnotation(ApiModel.class);

        if (annotation.pagination() == false) {
            NodeReader<T> dataNodeReader = new NodeReader<>(typeParameterClass);

            Node dataNode = dataNodeReader.findNodeInChilds(doc.getDocumentElement());

            if (dataNode != null)
                return dataNodeReader.nodeToObject(dataNode);
        }
        return null;
    }


    /**
     * Downloads the xml document for specific url with an http connection
     * @param uri the url to the document
     * @return the xml document
     * @throws ModelParseException if the data does not match the model in any way
     * @throws InvalidUrlException if the connection to the specified url could not be made
     */
    private Document getXMLDocument(String uri) throws MalformedURLException, InternetConnectionException, XMLParseExeption {
        URL url = new URL(uri);
        try {
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            InputStream xml = connection.getInputStream();
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(xml);
                return doc;
            }
            catch (ParserConfigurationException | SAXException e){
                throw new XMLParseExeption("Response from server does not match xml format");
            } finally {
                connection.disconnect();
            }
        }
        catch (IOException e) {
            throw new InternetConnectionException("The connection to the specified url could not be made.");
        }
    }

    /**
     * Downloads image from url.
     * @param uri the url to the image
     * @return the image.
     * @throws ModelParseException if the
     * @throws MalformedURLException
     */
    public static Image getImageFromUri(String uri) throws InternetConnectionException, MalformedURLException {
        URL url = new URL(uri);
        try (InputStream is = url.openStream()) {
            Image image;
            try {
                image = ImageIO.read(is);
            } catch (IOException e) {
                throw new InternetConnectionException("Downloaded payload could not be read as image");
            }
            is.close();
            return image;
        } catch (IOException e) {
            throw new InternetConnectionException("Connection could not be established");
        }
    }

}
