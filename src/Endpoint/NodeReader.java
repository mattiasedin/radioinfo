package Endpoint;
import Exceptions.ModelMalformedException;
import Exceptions.ModelParseException;
import Exceptions.ModelInstantiationException;
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
 * <p>
 * Converts xml node to typed object. The object to convert to have to use the annotation interface ApiModelData for its
 * setters methods
 * @see ApiModelData
 */
public class NodeReader<T> {

    private final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private Class<T> classType;
    private SimpleDateFormat formatter;

    /**
     * Constructor for class
     * @param classType the typed class to instantiate during convertion. This class should need to use the ApiModelData
     *                  annotation interface for its setters
     * @see ApiModelData
     */
    public NodeReader(Class<T> classType) {
        this.classType = classType;

        formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    /**
     * Gets the node by the typed classname in the parent nodes children.
     * @param parentNode the parent node to search in.
     * @return the found node if found else null.
     */
    public Node findClassNodeInChilds(Node parentNode) {

        String className = classType.getSimpleName().toLowerCase(); //classType.getAnnotation(ApiModel.class).container();

        /* Get node that matches classname */
        Node currentNode = findNodeByNameInChilds(parentNode, className);

        return currentNode;
    }

    /**
     * Finds a specific node in the children by name.
     * @param parentNode the node to search in.
     * @param nameToFind case-insensitive search name.
     * @return the node if found, else null.
     */
    public static Node findNodeByNameInChilds(Node parentNode, String nameToFind) {
        Node currentChildNode = parentNode.getFirstChild();
        while (!currentChildNode.getNodeName().toLowerCase().equals(nameToFind.toLowerCase())) {
            currentChildNode = currentChildNode.getNextSibling();
            if (currentChildNode == null)
                break;
        }
        return currentChildNode;
    }

    /**
     * Instansiate object from generic type and sets its value from the node with matching setters.
     * @param nodeToConvert the node containing the object values
     * @return the converted object
     * @throws ModelInstantiationException if the instantiation of the class failed with access violation or access error
     * @throws ModelParseException if the given nodes values could not be parsed to model. This is due to inconsistency
     *                             in the model and the given node.
     */
    public T nodeToObject(Node nodeToConvert) throws ModelInstantiationException, ModelMalformedException {

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
                        Node attr = nodeToConvert.getAttributes().getNamedItem(annotation.name());
                        if (attr != null)
                            invokeSetter(dataElement, method, attr.getTextContent());
                        break;
                    case innercontent:
                        NodeList contentNodes = nodeToConvert.getChildNodes();

                        for (int i = 0; i < contentNodes.getLength(); i++) {
                            if (contentNodes.item(i).getNodeName().equals(annotation.name())) {
                                invokeSetter(dataElement, method, contentNodes.item(i).getTextContent());
                                break;
                            }
                        }
                        break;
                    case nestedObject:
                        NodeList contentNodes2 = nodeToConvert.getChildNodes();
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

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ModelInstantiationException("Unable to instantiate model class");
        } catch (ModelParseException e) {
            throw new ModelMalformedException("Given node does not match the models values correctly given parse exception: "+e.getMessage());
        }
        return dataElement;
    }

    /**
     * Traverse the container node and parses children to typed object list.
     * @param nodeListContainer container node
     * @return list of typed objects.
     * @throws ModelMalformedException if the parsing from node to object malfunctioned.
     * @throws ModelInstantiationException if the instatiation of the typed object failed.
     */
    public ArrayList<T> getObjectListFromNode(Node nodeListContainer) throws ModelInstantiationException, ModelMalformedException {
        NodeList nodeDataList = nodeListContainer.getChildNodes();

        ArrayList<T> dataList = new ArrayList<T>(nodeDataList.getLength());

        for (int i = 0; i < nodeDataList.getLength(); i++) {
            if (nodeDataList.item(i).getNodeName().equals(classType.getSimpleName().toLowerCase())) {
                dataList.add(nodeToObject(nodeDataList.item(i)));
            }
        }

        return dataList;
    }

    /**
     * Sets the model value to its correct type
     * @param instance the instance to update
     * @param method the setter
     * @param value value to set
     * @throws ModelParseException if the value given does not match model type
     * @throws ModelInstantiationException if the method could not be called
     * @throws ModelMalformedException if the method given is not of setter type with only one parameter or wrong parameter
     *                                 type.
     */
    private void invokeSetter(Object instance, Method method, Object value) throws ModelParseException, ModelInstantiationException, ModelMalformedException {
        Type[] paramTypes = method.getGenericParameterTypes();
        if (paramTypes.length != 1)
            throw new ModelMalformedException("Model does not match setter structure with only one setter");
        Type setterParameter = paramTypes[0];
        try {
            switch (setterParameter.getTypeName()) {
                case "int":
                    try {
                        method.invoke(instance, Integer.parseInt((String) value));
                    } catch (NumberFormatException | ClassCastException e) {
                        throw new ModelParseException("Value malformed, tried to cast object to integer but failed. Value is not of integer type.");
                    }
                    break;
                case "java.util.Date":
                    try {
                        Date d = formatter.parse((String) value);
                        method.invoke(instance, d);
                    } catch (ParseException e) {
                        throw new ModelParseException("Date string malformed, string does not match format '" + DATE_TIME_FORMAT + "' at offset: " + e.getErrorOffset());
                    }
                    break;
                default:
                    method.invoke(instance, value);
            }
        } catch (IllegalArgumentException e) {
            throw new ModelMalformedException("Model setter does not match given type");
        } catch (IllegalAccessException e) {
            throw new ModelInstantiationException("Unable to acces model class setter");
        } catch (InvocationTargetException e) {
            throw new ModelInstantiationException("Unable to invoke model setter");
        }
    }
}
