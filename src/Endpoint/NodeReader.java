package Endpoint;
import Exceptions.ModelParseException;
import Exceptions.NodeInstantiationException;
import Models.ApiModelData;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mattias on 1/29/17.
 */
public class NodeReader<T> {

    private final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private Class<T> classType;
    private SimpleDateFormat formatter;

    public NodeReader(Class<T> classType) {
        this.classType = classType;

        formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public Node findNodeInChilds(Node n) {

        String className = classType.getSimpleName().toLowerCase(); //classType.getAnnotation(ApiModel.class).container();

        /* Get node that matches classname */
        Node currentNode = getNodeWithName(n, className);

        return currentNode;
    }


    /*Instansiate object from generic type*/
    public T nodeToObject(Node currentNode) throws NodeInstantiationException, ModelParseException {

        T dataElement = null;
        try {
            Constructor con = classType.getConstructor();

            //noinspection unchecked
            dataElement = (T) con.newInstance();

            Method[] allMethods = classType.getMethods();
            ArrayList<Method> mList = new ArrayList<>(allMethods.length);

            for (Method m : allMethods) {
                boolean isSetMethod = m.getName().startsWith("set");
                if (isSetMethod && m.isAnnotationPresent(ApiModelData.class))
                    mList.add(m);
            }

            Method[] methods = new Method[mList.size()];

            for (int i = 0; i < mList.size(); i++) {
                methods[i] = mList.get(i);
            }

            for (Method method : methods) {

                ApiModelData annotation = method.getAnnotation(ApiModelData.class);

                switch (annotation.type()){
                    case attribute:
                        Node attr = currentNode.getAttributes().getNamedItem(annotation.name());
                        if (attr != null)
                            invokeSetter(dataElement, method, attr.getTextContent());
                        break;
                    case innercontent:
                        NodeList contentNodes = currentNode.getChildNodes();

                        for (int i = 0; i < contentNodes.getLength(); i++) {
                            if (contentNodes.item(i).getNodeName().equals(annotation.name())) {
                                invokeSetter(dataElement, method, contentNodes.item(i).getTextContent());
                                break;
                            }
                        }
                        break;
                    case nestedObject:
                        NodeList contentNodes2 = currentNode.getChildNodes();
                        for (int i = 0; i < contentNodes2.getLength(); i++) {
                            if (contentNodes2.item(i).getNodeName().equals(annotation.name())) {

                                Class<?> aClass = annotation.nestedObjectType();

                                //noinspection unchecked
                                NodeReader a = new NodeReader(aClass);

                                Object o = a.nodeToObject(contentNodes2.item(i));

                                invokeSetter(dataElement, method, o);
                                break;
                            }

                        }
                        break;
                }
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException  e) {
            throw new NodeInstantiationException(e.getMessage());
        } catch (ParseException ep) {
            throw new ModelParseException(ep.getMessage());
        }
        return dataElement;
    }

    private Node getNodeWithName(Node n, String className) {
        Node currentNode = n.getFirstChild();
        while (!currentNode.getNodeName().toLowerCase().equals(className.toLowerCase())) {
            currentNode = currentNode.getNextSibling();
            if (currentNode == null)
                break;
        }
        return currentNode;
    }

    public ArrayList<T> getObjectListFromNode(Node n, String listContainerName) throws ModelParseException, NodeInstantiationException {
        Node nodeListContainer = getNodeWithName(n, listContainerName);

        if (nodeListContainer == null)
            throw new ModelParseException("Payload does not contain model: "+listContainerName);

        NodeList nodeDataList = nodeListContainer.getChildNodes();

        ArrayList<T> dataList = new ArrayList<T>(nodeDataList.getLength());

        for (int i = 0; i < nodeDataList.getLength(); i++) {
            if (nodeDataList.item(i).getNodeName().equals(classType.getSimpleName().toLowerCase())) {
                dataList.add(nodeToObject(nodeDataList.item(i)));
            }
        }

        return dataList;
    }



    private void invokeSetter(Object instance, Method method, Object value) throws IllegalAccessException, InvocationTargetException, ParseException {
        Type[] paramTypes = method.getGenericParameterTypes();
        try {
            if (paramTypes[0].getTypeName().equals("java.lang.String")) {
                method.invoke(instance, value);
            }

            else if (paramTypes[0].getTypeName().equals("int")) {
                try {
                    method.invoke(instance, Integer.parseInt((String) value));
                } catch (NumberFormatException | ClassCastException e) {
                    throw new ParseException("Value malformed, tried to cast object to integer but failed.", 0);
                }

            }

            else if (paramTypes[0].getTypeName().equals("java.util.Date")) {
                try {
                    Date d = formatter.parse((String) value);
                    method.invoke(instance, d);
                } catch (ParseException e) {
                    throw new ParseException("Date string malformed, string does not match format "+ DATE_TIME_FORMAT, e.getErrorOffset());
                }
            } else {
                method.invoke(instance, value);
            }
        } catch (IllegalArgumentException e2) {
            throw new ParseException("Tried to invoke setter with wrong argument type.", 0);
        }
    }
}
