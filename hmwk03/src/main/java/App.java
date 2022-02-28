import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * A program to demonstrate how to parse XML using Stax
 *
 * @author: Luke Townsend
 * @since: February 2022
 */

public class App {
    public static void main(String[] args) {

        readSimpleXML();
        ArrayList<State> states = readStates("states.xml");
        printStates(states);



        System.out.println("\nDone!");
    }

    private static void printStates(ArrayList<State> states) {
        for(State state: states){
            System.out.println(state);
        }
    }

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


    private static ArrayList<State> readStates(String file) {
        State state = null;
        ArrayList<State> states = new ArrayList<State>();
        try {
            XMLInputFactory factory = XMLInputFactory.newFactory();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "state":
                            event = eventReader.nextEvent();
                            state = new State();
                            if (event.asCharacters().getData().startsWith("\n")) {
                                break;
                            }
                            state.setState(event.asCharacters().getData());
                            break;
                        case "slug":
                            event = eventReader.nextEvent();
                            String slug = event.asCharacters().getData();
                            state.setSlug(slug);
                            break;
                        case "code":
                            event = eventReader.nextEvent();
                            String code = event.asCharacters().getData();
                            state.setCode(code);
                            break;
                        case "nickname":
                            event = eventReader.nextEvent();
                            String nickname = event.asCharacters().getData();
                            state.setNickname(nickname);
                            break;
                        case "website":
                            event = eventReader.nextEvent();
                            String website = event.asCharacters().getData();
                            state.setWebsite(website);
                            break;
                        case "admission_date":
                            event = eventReader.nextEvent();
                            String admission_date = event.asCharacters().getData();
                            state.setAdmission_date(admission_date);
                            break;
                        case "admission_number":
                            event = eventReader.nextEvent();
                            int admission_number = Integer.parseInt(event.asCharacters().getData());
                            state.setAdmission_number(admission_number);
                            break;
                        case "capital_city":
                            event = eventReader.nextEvent();
                            String capital_city = event.asCharacters().getData();
                            state.setCapitalName(capital_city);
                            break;
                        case "capital_url":
                            event = eventReader.nextEvent();
                            String capital_url = event.asCharacters().getData();
                            state.setCapital_url(capital_url);
                            break;
                        case "population":
                            event = eventReader.nextEvent();
                            int population = Integer.parseInt(event.asCharacters().getData());
                            state.setPopulation(population);
                            break;
                        case "population_rank":
                            event = eventReader.nextEvent();
                            int popRank = Integer.parseInt(event.asCharacters().getData());
                            state.setPopulation_rank(popRank);
                            break;
                        case "constitution_url":
                            event = eventReader.nextEvent();
                            String constitution_url = event.asCharacters().getData();
                            state.setConstitution_url(constitution_url);
                            break;
                        case "state_flag_url":
                            event = eventReader.nextEvent();
                            String state_flag_url = event.asCharacters().getData();
                            state.setState_flag_url(state_flag_url);
                            break;
                        case "state_seal_url":
                            event = eventReader.nextEvent();
                            String state_seal_url = event.asCharacters().getData();
                            state.setState_seal_url(state_seal_url);
                            break;
                        case "map_image_url":
                            event = eventReader.nextEvent();
                            String map_image_url = event.asCharacters().getData();
                            state.setMap_image_url(map_image_url);
                            break;
                        case "landscape_background_url":
                            event = eventReader.nextEvent();
                            String landscape_url = event.asCharacters().getData();
                            state.setLandscape_background_url(landscape_url);
                            break;
                        case "skyline_background_url":
                            event = eventReader.nextEvent();
                            String skyline_background = event.asCharacters().getData();
                            state.setSkyline_background_url(skyline_background);
                            break;
                        case "twitter_url":
                            event = eventReader.nextEvent();
                            String twitter_url = event.asCharacters().getData();
                            state.setTwitter_url(twitter_url);
                            break;
                        case "facebook_url":
                            event = eventReader.nextEvent();
                            String facebook_url = event.asCharacters().getData();
                            state.setFacebook_url(facebook_url);
                            break;
                    }
                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equalsIgnoreCase("facebook_url")) {
                        states.add(state);
                    }
                }
            }
        } catch (XMLStreamException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return states;
    }

}

