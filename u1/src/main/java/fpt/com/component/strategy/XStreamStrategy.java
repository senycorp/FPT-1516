package fpt.com.component.strategy;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import fpt.com.ExternalizableReflectionConverter;
import fpt.com.SerializableStrategy;
import fpt.com.core.component.strategy.BaseStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * XStreamStrategy
 */
public class XStreamStrategy extends BaseStrategy
        implements SerializableStrategy {

    /**
     * XStream-Object
     */
    private XStream xstream = null;

    @Override
    public String getDestinationFilename() {
        return "productsXStream.xml";
    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {
        // Setting the instance with all necessary properties
        if (this.xstream == null) {
            xstream = new XStream(new DomDriver("UTF-8"));
            xstream.registerConverter(
                    new ExternalizableReflectionConverter(xstream),
                    XStream.PRIORITY_LOW);
            xstream.processAnnotations(fpt.com.model.Product.class);
            xstream.alias("Ware", fpt.com.model.Product.class);
            xstream.autodetectAnnotations(true);
        }

        // Setting up object streams for input and output
        if (input != null) {
            this.is = input;
            this.objectIS = xstream.createObjectInputStream(is);
        }

        if (output != null) {
            this.os = output;
            this.objectOS = xstream.createObjectOutputStream(output, "Waren");
        }
    }


    @Override
    public String toString() {
        return "XStreamStrategy";
    }
}
