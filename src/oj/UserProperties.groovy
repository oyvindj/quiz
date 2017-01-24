package oj

import org.springframework.stereotype.Component
import org.springframework.util.DefaultPropertiesPersister

@Component
class UserProperties {
    Properties props = null
    File file = null
    DefaultPropertiesPersister persister = null
    UserProperties() {
        file = new File("user.properties")
        persister = new DefaultPropertiesPersister()
        props = new Properties()
        if(!file.exists()) {
            def out = new FileOutputStream(file)
            persister.store(props, out, "user config properties")
        }
        def instream = new FileInputStream(file)
        persister.load(props, instream)
    }

    Boolean hasProperty(String prop) {
        return props.contains(prop)
    }

    String readProperty(String prop)  {
        return props.get(prop)
    }

    void writeProperty(String name, String value) {
        props.put(name, value)
        def out = new FileOutputStream(file)
        persister.store(props, out, "")
    }
}
