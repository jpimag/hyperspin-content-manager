package jps.hyperspin.common.xml;

import java.io.Reader;
import java.io.Writer;
import jps.hyperspin.exception.HCMBindingException;
import jps.hyperspin.exception.HCMDatabaseException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


/**
 * 
 * @author JPS
 * 
 */
public final class XmlBinding {

	/**
	 * 
	 */
	private static XmlBinding instance = new XmlBinding();

	/**
	 * 
	 */
	private XmlBinding() {
	}

	/**
	 * @return singleton instance
	 */
	public static XmlBinding getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param object
	 *            object
	 * @param writer
	 *            writer
	 * @throws HCMDatabaseException
	 *             exception
	 */
	public void java2xml(final Object object, final Writer writer)
			throws HCMDatabaseException {
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(object, writer);
		} catch (JAXBException e) {
			throw new HCMBindingException(e, "[java2xml] jaxb exception");
		}
	}

	/**
	 * 
	 * @param classe
	 *            the classe
	 * @param reader
	 *            reader
	 * @return the object load
	 * @throws HCMDatabaseException
	 *             exception
	 */
	public Object xml2java(final Class<?> classe, final Reader reader)
			throws HCMDatabaseException {
		try {
			JAXBContext context = JAXBContext.newInstance(classe);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object o = unmarshaller.unmarshal(reader);
			if (!o.getClass().equals(classe)) {
				throw new HCMBindingException(null, "{xml2java] badType");
			}
			return o;
		} catch (JAXBException e) {
			throw new HCMBindingException(e, "[xml2java]jaxb exception");
		}

	}
}
