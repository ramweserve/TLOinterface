package jaxb.processor;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link NamespacePrefixMapper} that maps the schema
 * namespaces more to readable names. Used by the jaxb marshaller. Requires
 * setting the property "com.sun.xml.bind.namespacePrefixMapper" to an instance
 * of this class.
 */
public class DefaultNamespacePrefixMapper extends NamespacePrefixMapper {

    public DefaultNamespacePrefixMapper() {
        namespaceMap.put(COMMON, "common");
        namespaceMap.put(TYPES, "types");
        namespaceMap.put(N4TLO, "n4tlo");
    }

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        String matchingNamespace = namespaceMap.get(namespaceUri);
        return (matchingNamespace != null? matchingNamespace : "");
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] { COMMON, TYPES, N4TLO };
    }

    private static final String COMMON = "http://www.syncrotess.com/external/n4-tlo/common/types";
    private static final String TYPES = "http://www.syncrotess.com/external/n4-tlo/types";
    private static final String N4TLO = "http://www.syncrotess.com/external/n4-tlo";
    private Map<String, String> namespaceMap = new HashMap<>();
}