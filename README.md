# Java StAX (Streaming API for XML) Parser Tutorial

### In the first example we will be pulling elements from an xml file and produce an output using the data we pulled.

``` xml
<?xml version="1.0"?>
<credentials>
    <host>woz.cs.missouriwestern.edu</host>
    <port>33006</port> <!--This isn't the right port, by the way -->
    <user>csc</user>
    <password xhint="room where woz is located It definitily is not '!😈湯🦊🚴'">********</password>
</credentials>
```

We want to pull all the data from this XML file including the attribute "xhint". The following line will allow us to read in streams of data `XMLInputFactory factory = XMLInputFactory.newFactory();`. Using the XMLInputFactory we just created, we will create an event reader so we know where to pull data from. `event.isStartElement()` is used to recognize each element. Using a switch statement we will be able to decided what we want to do with each element. We will need to use `event = eventReader.nextEvent();` to make sure the loop will run through again. Each variable we pull we will set using `event.asCharacters().getData();`. After this we can use a print statement to produce output.

``` java
private static void readSimpleXML() {
        String host;
        int port;
        String user;
        String password;
        XMLInputFactory factory = XMLInputFactory.newFactory();
        try {
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader("zz_woz.xml"));
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "host":
                            event = eventReader.nextEvent();
                            host = event.asCharacters().getData();
                            System.out.println("Host: " + host);
                            break;
                        case "port":
                            event = eventReader.nextEvent();
                            port = Integer.parseInt(event.asCharacters().getData());
                            System.out.println("Port: " + port);
                            break;
                        case "user":
                            event = eventReader.nextEvent();
                            user = event.asCharacters().getData();
                            System.out.println("User: " + user);
                            break;
                        case "password":
                            Attribute xHint = startElement.getAttributeByName(new QName("xhint"));
                            System.out.println("Password hint: " + xHint.getValue());
                            event = eventReader.nextEvent();
                            password = event.asCharacters().getData();
                            System.out.println("Password: " + password);
                    }
                }

            }
            eventReader.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
 ```
 
 
