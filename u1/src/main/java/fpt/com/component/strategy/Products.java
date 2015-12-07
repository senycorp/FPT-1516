package fpt.com.component.strategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import fpt.com.model.Product;

import java.util.List;

/**
 * Created by senycorp on 07.12.15.
 */
@XStreamAlias("Waren")
public class Products {
    @XStreamImplicit(itemFieldName = "Waren")
    @XStreamAlias("Waren")
    List<Product> products;
}
