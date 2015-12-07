package fpt.com.component.converter;

import com.thoughtworks.xstream.converters.SingleValueConverter;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Locale;


public class DoubleConverter implements SingleValueConverter{


	public boolean canConvert(Class arg0) {
		return SimpleDoubleProperty.class.equals(arg0);
	}


	public Object fromString(String arg0) {
		return new SimpleDoubleProperty(Double.parseDouble(arg0));
	}


	public String toString(Object arg0) {
		return String.format(Locale.US,"%3.2f", ((SimpleDoubleProperty)arg0).getValue());
	}

}
