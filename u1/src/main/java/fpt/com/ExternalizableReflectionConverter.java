package fpt.com;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.mapper.Mapper;

import java.io.Externalizable;

public class ExternalizableReflectionConverter
        extends ReflectionConverter {

    public ExternalizableReflectionConverter(
            Mapper mapper,
            ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider);
    }

    public ExternalizableReflectionConverter(XStream xstream) {
        this(xstream.getMapper(), xstream.getReflectionProvider());
    }

    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
        return Externalizable.class.isAssignableFrom(type);
    }
}
