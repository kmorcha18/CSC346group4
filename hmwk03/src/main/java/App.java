import javax.swing.*;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * A program to demonstrate how to parse XML using Stax
 *
 * @author: Luke Townsend
 * @since: February 2022
 */

public class App {
    public static void main(String[] args){


        readSimpleXML();
//        readStates();



        System.out.println("\nDone!");
    }

    private static void readSimpleXML() {
        String host;
        String port;
        String user;
        String password;
        XMLInputFactory factory = XMLInputFactory.newFactory();
        try {
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader("zz_woz.xml"));
            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();

                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();
                    switch (startElement.getName().getLocalPart()){
                        case "host":
                            event = eventReader.nextEvent();
                            host = event.asCharacters().getData();
                            System.out.println("Host: " + host);
                            break;
                        case "port":
                            event = eventReader.nextEvent();
                            port = event.asCharacters().getData();
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
                if(event.isEndElement()){
                    EndElement endElement = event.asEndElement();

                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    private static void readStates() {
        boolean bStateName = false;
        boolean bNickname = false;
        boolean bCapital = false;
        boolean bPopulation = false;
        try {
            XMLInputFactory factory = XMLInputFactory.newFactory();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader("states.xml"));

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                switch(event.getEventType()) {

                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String name = startElement.getName().getLocalPart();
                        if (name.equalsIgnoreCase("state")) {
                                bStateName = true;
                        }
                        else if (name.equalsIgnoreCase("nickname"))
                           bNickname = true;
                        else if(name.equalsIgnoreCase("capital_city"))
                            bCapital = true;
                        else if(name.equalsIgnoreCase("population"))
                            bPopulation = true;
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if (bStateName) {
                            if(characters.getData().startsWith("\n")) {
                                bStateName = false;
                                break;
                            }
                            System.out.println("State: " + characters.getData());
                            bStateName = false;
                        }
                        if (bNickname) {
                            System.out.println("Nickname: " + characters.getData());
                            bNickname = false;
                        }
                        if (bCapital) {
                            System.out.println("Capital: " + characters.getData());
                            bCapital = false;
                        }
                        if (bPopulation) {
                            System.out.println("Population: " + characters.getData());
                            bPopulation = false;
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if (endElement.getName().getLocalPart().equalsIgnoreCase("facebook_url")) {
                            System.out.println("End of State");
                            System.out.println();
                        }
                        break;
                }
            }
        }
        catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
   }
}
