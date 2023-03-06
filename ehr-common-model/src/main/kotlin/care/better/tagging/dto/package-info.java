@XmlSchema(
        namespace = "http://schemas.marand.com/thinkehr/v1",
        elementFormDefault = XmlNsForm.QUALIFIED,
        xmlns = {
                @XmlNs(prefix = "te", namespaceURI = "http://schemas.marand.com/thinkehr/v1")
        }
)
package care.better.tagging.dto;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;
