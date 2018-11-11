package chartconstellation.app.Controllers;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.entities.Attributes;
import chartconstellation.app.entities.ChartType;
import chartconstellation.app.util.AttributeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/attributes")
public class AttributesController {

    @Autowired
    Configuration configuration;

    @Autowired
    AttributeUtil attributeUtil;

    @RequestMapping(value="/getAllAttributes", method= RequestMethod.GET)
    public Attributes getAttributes() {

        Set<String> AttributeSet = attributeUtil.getAllAttributes(configuration.getMongoDatabase(), configuration.getOlympicchartcollection());

        Attributes attributes = new Attributes();
        attributes.setAttributesSet(AttributeSet);
        return attributes;

    }
}
