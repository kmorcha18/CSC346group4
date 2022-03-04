import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * A program to demonstrate how to parse XML using Stax
 *
 * @author: Luke Townsend
 * @since: February 2022
 */

public class App {
    public static void main(String[] args) {

        System.out.println("Reading the simple XML file...");
        readSimpleXML();
        System.out.println();
        System.out.println("Reading from weather website...");
        String address = "https://w1.weather.gov/xml/current_obs/KSTJ.xml";
        readWeatherURL(address);
        System.out.println();
        System.out.println("Reading the States XML file...");
        ArrayList<State> states = readStates("states.xml");
        printStates(states);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("example.xml");
            writeXml2(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




        System.out.println("\nDone!");
    }

    private static void writeXml2(OutputStream out) {
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();

        try {
            XMLEventWriter writer = output.createXMLEventWriter(out);

            writer.add(eventFactory.createStartDocument());

            writer.add(eventFactory.createComment("This is some example xml"));

            writer.add(eventFactory.createStartElement("","","student"));
            writer.add(eventFactory.createAttribute("id","15"));

            writer.add(eventFactory.createStartElement("","","name"));
            writer.add(eventFactory.createCharacters("Josh"));
            writer.add(eventFactory.createEndElement("","","name"));

            writer.add(eventFactory.createStartElement("","","age"));
            writer.add(eventFactory.createCharacters("21"));
            writer.add(eventFactory.createEndElement("","","age"));

            writer.add(eventFactory.createStartElement("","","grade_year"));
            writer.add(eventFactory.createCharacters("Junior"));
            writer.add(eventFactory.createEndElement("","","grade_year"));

            writer.add(eventFactory.createEndElement("","","student"));

            writer.add(eventFactory.createEndDocument());

            writer.flush();
            writer.close();


        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private static void readWeatherURL(String address) {
        try {
            URL url = new URL(address);
            XMLInputFactory factory = XMLInputFactory.newFactory();
            XMLEventReader eventReader = factory.createXMLEventReader(url.openStream());

            while(eventReader.hasNext()){
                XMLEvent event = (XMLEvent) eventReader.next();
                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();
                    switch(startElement.getName().getLocalPart()){
                        case "credit":
                            event = (XMLEvent) eventReader.next();
                            System.out.println(event.asCharacters().getData());
                            break;
                        case "observation_time_rfc822":
                            event = (XMLEvent) eventReader.next();
                            System.out.println("Observation Time: " + event.asCharacters().getData());
                            break;
                        case "temperature_string":
                            event = (XMLEvent) eventReader.next();
                            System.out.println("Temperature: " + event.asCharacters().getData());
                            break;
                        case "windchill_f":
                            event = (XMLEvent) eventReader.next();
                            System.out.println("Windchill in Fahrenheit: " + event.asCharacters().getData());
                            break;
                        case "relative_humidity":
                            event = (XMLEvent) eventReader.next();
                            System.out.println("Relative Humidity: " + event.asCharacters().getData());
                    }
                }
            }
            eventReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printStates(ArrayList<State> states) {
        for (State state : states) {
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
        ArrayList<State> states = new ArrayList<>();
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
                            if (event.isEndElement()) {
                                String facebook_url = "N/A";
                                state.setFacebook_url(facebook_url);
                                break;
                            }
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

