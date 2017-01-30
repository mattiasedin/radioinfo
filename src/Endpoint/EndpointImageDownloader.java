package Endpoint;

import Exceptions.DataDoesNotMatchModelException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mattias on 1/11/17.
 */
public class EndpointImageDownloader {

    public static Image getImageFromUri(String uri) throws DataDoesNotMatchModelException, MalformedURLException {
        URL url = new URL(uri);
        try (InputStream is = url.openStream()) {
            Image image = ImageIO.read(is);
            is.close();
            return image;
        } catch (IOException e) {
            throw new DataDoesNotMatchModelException("Image does not exist");
        }
    }
}
