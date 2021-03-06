# Java StAX (Streaming API for XML) Parser Tutorial

### In the first example we will be pulling elements from an xml file and produce an output using the data we pulled.

<details>
    <summary>XML file we will use</summary>

``` xml
<?xml version="1.0"?>
<credentials>
    <host>woz.cs.missouriwestern.edu</host>
    <port>33006</port> <!--This isn't the right port, by the way -->
    <user>csc</user>
    <password xhint="room where woz is located It definitily is not '!😈湯🦊🚴'">********</password>
</credentials>
```
    
</details>

We want to pull all the data from this XML file including the attribute "xhint". The following line will allow us to read in streams of data `XMLInputFactory factory = XMLInputFactory.newFactory();`. Using the XMLInputFactory we just created, we will create an event reader so we know where to pull data from. `event.isStartElement()` is used to recognize each element. Using a switch statement we will be able to decided what we want to do with each element. We will need to use `event = eventReader.nextEvent();` to make sure the loop will run through again. Each variable we pull we will set using `event.asCharacters().getData();`. If we want to pull an attribute from an element, we can use the attribute class like so `Attribute xHint = startElement.getAttributeByName(new QName("xhint"));`. After this we can use a print statement to produce output.

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
 
 You can try this method by using the following in you main method.
 
 ``` java
 public static void main(String[] args) {
        System.out.println("Reading the simple XML file...");
        readSimpleXML();
    }
 ```
 
 ### For our next example we will be pulling from a website instead of a local file
 
 To pull from a website we will add an address parameter into the method. Using this paramenter, we can pull data from the website using the openStream() method. We parse the data the same way we did in the previous example.
 
 ``` java
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
 ```
 
  You can try this method by using the following in you main method.
 
 ``` java
 public static void main(String[] args) {
        System.out.println("Reading from weather website...");
        String address = "https://w1.weather.gov/xml/current_obs/KSTJ.xml";
        readWeatherURL(address);
    }
 ```
 
### In our last example we will pull data from an array expressed as XML

<details>
    <summary>XML file we will use</summary>

``` xml
<?xml version='1.0'?>
<states>
    <state>
        <state>Alabama</state>
        <slug>alabama</slug>
        <code>AL</code>
        <nickname>Yellowhammer State</nickname>
        <website>http://www.alabama.gov</website>
        <admission_date>1819-12-14</admission_date>
        <admission_number>22</admission_number>
        <capital_city>Montgomery</capital_city>
        <capital_url>http://www.montgomeryal.gov</capital_url>
        <population>4833722</population>
        <population_rank>23</population_rank>
        <constitution_url>http://alisondb.legislature.state.al.us/alison/default.aspx</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/alabama-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/alabama-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/alabama-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/alabama.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/alabama.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/alabamagov</twitter_url>
        <facebook_url>https://www.facebook.com/alabamagov</facebook_url>
    </state>
    <state>
        <state>Alaska</state>
        <slug>alaska</slug>
        <code>AK</code>
        <nickname>The Last Frontier</nickname>
        <website>http://alaska.gov</website>
        <admission_date>1959-01-03</admission_date>
        <admission_number>49</admission_number>
        <capital_city>Juneau</capital_city>
        <capital_url>http://www.juneau.org</capital_url>
        <population>735132</population>
        <population_rank>47</population_rank>
        <constitution_url>http://www.legis.state.ak.us/basis/folioproxy.asp?url=http://wwwjnu01.legis.state.ak.us/cgi-bin/folioisa.dll/acontxt/query=*/doc/{t1}?</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/alaska-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/alaska-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/alaska-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/alaska.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/alaska.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/alaska</twitter_url>
        <facebook_url>https://www.facebook.com/AlaskaLocalGovernments</facebook_url>
    </state>
    <state>
        <state>Arizona</state>
        <slug>arizona</slug>
        <code>AZ</code>
        <nickname>The Grand Canyon State</nickname>
        <website>https://az.gov</website>
        <admission_date>1912-02-14</admission_date>
        <admission_number>48</admission_number>
        <capital_city>Phoenix</capital_city>
        <capital_url>https://www.phoenix.gov</capital_url>
        <population>6626624</population>
        <population_rank>15</population_rank>
        <constitution_url>http://www.azleg.gov/Constitution.asp</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/arizona-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/arizona-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/arizona-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/arizona.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/arizona.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Arkansas</state>
        <slug>arkansas</slug>
        <code>AR</code>
        <nickname>The Natural State</nickname>
        <website>http://arkansas.gov</website>
        <admission_date>1836-06-15</admission_date>
        <admission_number>25</admission_number>
        <capital_city>Little Rock</capital_city>
        <capital_url>http://www.littlerock.org</capital_url>
        <population>2959373</population>
        <population_rank>32</population_rank>
        <constitution_url>http://www.arkleg.state.ar.us/assembly/Summary/ArkansasConstitution1874.pdf</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/arkansas-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/arkansas-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/arkansas-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/arkansas.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/arkansas.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/arkansasgov</twitter_url>
        <facebook_url>https://www.facebook.com/Arkansas.gov</facebook_url>
    </state>
    <state>
        <state>California</state>
        <slug>california</slug>
        <code>CA</code>
        <nickname>Golden State</nickname>
        <website>http://www.ca.gov</website>
        <admission_date>1850-09-09</admission_date>
        <admission_number>31</admission_number>
        <capital_city>Sacramento</capital_city>
        <capital_url>http://www.cityofsacramento.org</capital_url>
        <population>38332521</population>
        <population_rank>1</population_rank>
        <constitution_url>http://www.leginfo.ca.gov/const-toc.html</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/california-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/california-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/california-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/california.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/california.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/cagovernment</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Colorado</state>
        <slug>colorado</slug>
        <code>CO</code>
        <nickname>The Centennial State</nickname>
        <website>https://www.colorado.gov</website>
        <admission_date>1876-08-01</admission_date>
        <admission_number>38</admission_number>
        <capital_city>Denver</capital_city>
        <capital_url>http://www.denvergov.org</capital_url>
        <population>5268367</population>
        <population_rank>22</population_rank>
        <constitution_url>https://www.colorado.gov/pacific/archives/government</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/colorado-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/colorado-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/colorado-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/colorado.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/colorado.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/coloradogov</twitter_url>
        <facebook_url>https://www.facebook.com/Colorado.gov</facebook_url>
    </state>
    <state>
        <state>Connecticut</state>
        <slug>connecticut</slug>
        <code>CT</code>
        <nickname>Constitution State</nickname>
        <website>http://www.ct.gov</website>
        <admission_date>1788-01-09</admission_date>
        <admission_number>5</admission_number>
        <capital_city>Hartford</capital_city>
        <capital_url>http://www.hartford.gov</capital_url>
        <population>3596080</population>
        <population_rank>29</population_rank>
        <constitution_url>http://www.ct.gov/sots/cwp/view.asp?a=3188&amp;q=392288</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/connecticut-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/connecticut-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/connecticut-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/connecticut.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/connecticut.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Delaware</state>
        <slug>delaware</slug>
        <code>DE</code>
        <nickname>The First State / The Diamond State</nickname>
        <website>http://delaware.gov</website>
        <admission_date>1787-12-07</admission_date>
        <admission_number>1</admission_number>
        <capital_city>Dover</capital_city>
        <capital_url>http://www.cityofdover.com</capital_url>
        <population>925749</population>
        <population_rank>45</population_rank>
        <constitution_url>http://www.state.de.us/facts/constit/welcome.htm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/delaware-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/delaware-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/delaware-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/delaware.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/delaware.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/delaware_gov</twitter_url>
        <facebook_url>https://www.facebook.com/delaware.gov</facebook_url>
    </state>
    <state>
        <state>Florida</state>
        <slug>florida</slug>
        <code>FL</code>
        <nickname>Sunshine State</nickname>
        <website>http://www.myflorida.com</website>
        <admission_date>1845-03-03</admission_date>
        <admission_number>27</admission_number>
        <capital_city>Tallahassee</capital_city>
        <capital_url>https://www.talgov.com/Main/Home.aspx</capital_url>
        <population>19552860</population>
        <population_rank>4</population_rank>
        <constitution_url>http://www.leg.state.fl.us/Statutes/index.cfm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/florida-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/florida-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/florida-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/florida.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/florida.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Georgia</state>
        <slug>georgia</slug>
        <code>GA</code>
        <nickname>Peach State</nickname>
        <website>http://georgia.gov</website>
        <admission_date>1788-01-02</admission_date>
        <admission_number>4</admission_number>
        <capital_city>Atlanta</capital_city>
        <capital_url>http://www.atlantaga.gov</capital_url>
        <population>9992167</population>
        <population_rank>8</population_rank>
        <constitution_url>http://sos.ga.gov/admin/files/Constitution_2013_Final_Printed.pdf</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/georgia-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/georgia-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/georgia-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/georgia.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/georgia.jpg</skyline_background_url>
        <twitter_url>http://twitter.com/georgiagov</twitter_url>
        <facebook_url>http://www.facebook.com/pages/georgiagov/29760668054</facebook_url>
    </state>
    <state>
        <state>Hawaii</state>
        <slug>hawaii</slug>
        <code>HI</code>
        <nickname>Aloha State</nickname>
        <website>https://www.ehawaii.gov</website>
        <admission_date>1959-08-21</admission_date>
        <admission_number>50</admission_number>
        <capital_city>Honolulu</capital_city>
        <capital_url>http://www.co.honolulu.hi.us</capital_url>
        <population>1404054</population>
        <population_rank>40</population_rank>
        <constitution_url>http://lrbhawaii.org/con</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/hawaii-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/hawaii-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/hawaii-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/hawaii.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/hawaii.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/ehawaiigov</twitter_url>
        <facebook_url>https://www.facebook.com/ehawaii.gov</facebook_url>
    </state>
    <state>
        <state>Idaho</state>
        <slug>idaho</slug>
        <code>ID</code>
        <nickname>Gem State</nickname>
        <website>https://www.idaho.gov</website>
        <admission_date>1890-07-03</admission_date>
        <admission_number>43</admission_number>
        <capital_city>Boise</capital_city>
        <capital_url>http://www.cityofboise.org</capital_url>
        <population>1612136</population>
        <population_rank>39</population_rank>
        <constitution_url>http://www.legislature.idaho.gov/idstat/IC/Title003.htm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/idaho-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/idaho-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/idaho-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/idaho.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/idaho.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/IDAHOgov</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Illinois</state>
        <slug>illinois</slug>
        <code>IL</code>
        <nickname>Prairie State</nickname>
        <website>https://www.illinois.gov</website>
        <admission_date>1818-12-03</admission_date>
        <admission_number>21</admission_number>
        <capital_city>Springfield</capital_city>
        <capital_url>http://www.springfield.il.us</capital_url>
        <population>12882135</population>
        <population_rank>5</population_rank>
        <constitution_url>http://www.ilga.gov/commission/lrb/conmain.htm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/illinois-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/illinois-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/illinois-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/illinois.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/illinois.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Indiana</state>
        <slug>indiana</slug>
        <code>IN</code>
        <nickname>Hoosier State</nickname>
        <website>http://www.in.gov</website>
        <admission_date>1816-12-11</admission_date>
        <admission_number>19</admission_number>
        <capital_city>Indianapolis</capital_city>
        <capital_url>http://www.indy.gov/Pages/Home.aspx</capital_url>
        <population>6570902</population>
        <population_rank>16</population_rank>
        <constitution_url>http://www.law.indiana.edu/uslawdocs/inconst.html</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/indiana-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/indiana-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/indiana-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/indiana.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/indiana.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/in_gov</twitter_url>
        <facebook_url>https://www.facebook.com/IndianaGovernment</facebook_url>
    </state>
    <state>
        <state>Iowa</state>
        <slug>iowa</slug>
        <code>IA</code>
        <nickname>Hawkeye State</nickname>
        <website>https://www.iowa.gov</website>
        <admission_date>1846-12-28</admission_date>
        <admission_number>29</admission_number>
        <capital_city>Des Moines</capital_city>
        <capital_url>http://www.ci.des-moines.ia.us</capital_url>
        <population>3090416</population>
        <population_rank>30</population_rank>
        <constitution_url>http://publications.iowa.gov/135/1/history/7-7.html</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/iowa-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/iowa-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/iowa-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/iowa.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/iowa.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/IAGOVTWEETS</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Kansas</state>
        <slug>kansas</slug>
        <code>KS</code>
        <nickname>Sunflower State</nickname>
        <website>https://www.kansas.gov</website>
        <admission_date>1861-01-29</admission_date>
        <admission_number>34</admission_number>
        <capital_city>Topeka</capital_city>
        <capital_url>http://www.topeka.org</capital_url>
        <population>2893957</population>
        <population_rank>34</population_rank>
        <constitution_url>https://kslib.info/405/Kansas-Constitution</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/kansas-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/kansas-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/kansas-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/kansas.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/kansas.jpg</skyline_background_url>
        <twitter_url>http://www.twitter.com/ksgovernment</twitter_url>
        <facebook_url>http://www.facebook.com/pages/Topeka-KS/Kansasgov-Kansas-Government-Online/52068474220</facebook_url>
    </state>
    <state>
        <state>Kentucky</state>
        <slug>kentucky</slug>
        <code>KY</code>
        <nickname>Bluegrass State</nickname>
        <website>http://kentucky.gov</website>
        <admission_date>1792-06-01</admission_date>
        <admission_number>15</admission_number>
        <capital_city>Frankfort</capital_city>
        <capital_url>http://frankfort.ky.gov</capital_url>
        <population>4395295</population>
        <population_rank>26</population_rank>
        <constitution_url>http://www.lrc.state.ky.us/Legresou/Constitu/intro.htm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/kentucky-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/kentucky-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/kentucky-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/kentucky.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/kentucky.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/kygov</twitter_url>
        <facebook_url>https://www.facebook.com/kygov</facebook_url>
    </state>
    <state>
        <state>Louisiana</state>
        <slug>louisiana</slug>
        <code>LA</code>
        <nickname>Pelican State</nickname>
        <website>http://louisiana.gov</website>
        <admission_date>1812-04-30</admission_date>
        <admission_number>18</admission_number>
        <capital_city>Baton Rouge</capital_city>
        <capital_url>http://brgov.com</capital_url>
        <population>4625470</population>
        <population_rank>25</population_rank>
        <constitution_url>http://senate.legis.state.la.us/Documents/Constitution</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/louisiana-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/louisiana-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/louisiana-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/louisiana.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/louisiana.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Maine</state>
        <slug>maine</slug>
        <code>ME</code>
        <nickname>Pine Tree State</nickname>
        <website>http://www.maine.gov</website>
        <admission_date>1820-03-15</admission_date>
        <admission_number>23</admission_number>
        <capital_city>Augusta</capital_city>
        <capital_url>http://www.augustamaine.gov</capital_url>
        <population>1328302</population>
        <population_rank>41</population_rank>
        <constitution_url>http://www.maine.gov/legis/const</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/maine-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/maine-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/maine-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/maine.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/maine.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/mainegov_news</twitter_url>
        <facebook_url>http://www.facebook.com/pages/Augusta-ME/Mainegov/98519328240</facebook_url>
    </state>
    <state>
        <state>Maryland</state>
        <slug>maryland</slug>
        <code>MD</code>
        <nickname>Old Line State</nickname>
        <website>http://www.maryland.gov</website>
        <admission_date>1788-04-28</admission_date>
        <admission_number>7</admission_number>
        <capital_city>Annapolis</capital_city>
        <capital_url>http://www.annapolis.gov</capital_url>
        <population>5928814</population>
        <population_rank>19</population_rank>
        <constitution_url>http://msa.maryland.gov/msa/mdmanual/43const/html/const.html</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/maryland-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/maryland-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/maryland-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/maryland.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/maryland.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/statemaryland</twitter_url>
        <facebook_url>https://www.facebook.com/statemaryland</facebook_url>
    </state>
    <state>
        <state>Massachusetts</state>
        <slug>massachusetts</slug>
        <code>MA</code>
        <nickname>Bay State</nickname>
        <website>http://www.mass.gov</website>
        <admission_date>1788-02-06</admission_date>
        <admission_number>6</admission_number>
        <capital_city>Boston</capital_city>
        <capital_url>http://www.ci.boston.ma.us</capital_url>
        <population>6692824</population>
        <population_rank>14</population_rank>
        <constitution_url>http://www.state.ma.us/legis/const.htm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/massachusetts-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/massachusetts-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/massachusetts-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/massachusetts.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/massachusetts.jpg</skyline_background_url>
        <twitter_url>http://twitter.com/massgov</twitter_url>
        <facebook_url>https://www.facebook.com/massgov</facebook_url>
    </state>
    <state>
        <state>Michigan</state>
        <slug>michigan</slug>
        <code>MI</code>
        <nickname>Wolverine State / Great Lakes State</nickname>
        <website>http://www.michigan.gov</website>
        <admission_date>1837-01-26</admission_date>
        <admission_number>26</admission_number>
        <capital_city>Lansing</capital_city>
        <capital_url>http://cityoflansingmi.com</capital_url>
        <population>9895622</population>
        <population_rank>9</population_rank>
        <constitution_url>http://www.legislature.mi.gov/(S(hrowl12tg05hemnnkidim1jb))/mileg.aspx?page=GetObject&amp;objectname=mcl-Constitution</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/michigan-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/michigan-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/michigan-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/michigan.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/michigan.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/migov</twitter_url>
        <facebook_url>https://www.facebook.com/MIgovernment</facebook_url>
    </state>
    <state>
        <state>Minnesota</state>
        <slug>minnesota</slug>
        <code>MN</code>
        <nickname>North Star State / Land of 10,000 Lakes</nickname>
        <website>https://mn.gov</website>
        <admission_date>1858-05-11</admission_date>
        <admission_number>32</admission_number>
        <capital_city>Saint Paul</capital_city>
        <capital_url>http://www.stpaul.gov</capital_url>
        <population>5420380</population>
        <population_rank>21</population_rank>
        <constitution_url>http://www.house.leg.state.mn.us/cco/rules/mncon/preamble.htm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/minnesota-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/minnesota-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/minnesota-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/minnesota.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/minnesota.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Mississippi</state>
        <slug>mississippi</slug>
        <code>MS</code>
        <nickname>Magnolia State</nickname>
        <website>http://www.ms.gov</website>
        <admission_date>1817-12-10</admission_date>
        <admission_number>20</admission_number>
        <capital_city>Jackson</capital_city>
        <capital_url>http://www.city.jackson.ms.us</capital_url>
        <population>2991207</population>
        <population_rank>31</population_rank>
        <constitution_url>http://law.justia.com/constitution/mississippi</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/mississippi-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/mississippi-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/mississippi-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/mississippi.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/mississippi.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/msdotgov</twitter_url>
        <facebook_url>https://www.facebook.com/msdotgov</facebook_url>
    </state>
    <state>
        <state>Missouri</state>
        <slug>missouri</slug>
        <code>MO</code>
        <nickname>Show Me State</nickname>
        <website>https://www.mo.gov</website>
        <admission_date>1821-08-10</admission_date>
        <admission_number>24</admission_number>
        <capital_city>Jefferson City</capital_city>
        <capital_url>http://www.jeffcitymo.org</capital_url>
        <population>6044171</population>
        <population_rank>18</population_rank>
        <constitution_url>http://www.moga.mo.gov/mostatutes/moconstn.html</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/missouri-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/missouri-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/missouri-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/missouri.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/missouri.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/MoGov</twitter_url>
        <facebook_url>https://www.facebook.com/mogov</facebook_url>
    </state>
    <state>
        <state>Montana</state>
        <slug>montana</slug>
        <code>MT</code>
        <nickname>Treasure State</nickname>
        <website>http://mt.gov</website>
        <admission_date>1889-11-08</admission_date>
        <admission_number>41</admission_number>
        <capital_city>Helena</capital_city>
        <capital_url>http://www.ci.helena.mt.us</capital_url>
        <population>1015165</population>
        <population_rank>44</population_rank>
        <constitution_url>http://courts.mt.gov/content/library/docs/72constit.pdf</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/montana-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/montana-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/montana-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/montana.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/montana.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Nebraska</state>
        <slug>nebraska</slug>
        <code>NE</code>
        <nickname>Cornhusker State</nickname>
        <website>http://www.nebraska.gov</website>
        <admission_date>1867-03-01</admission_date>
        <admission_number>37</admission_number>
        <capital_city>Lincoln</capital_city>
        <capital_url>http://lincoln.ne.gov</capital_url>
        <population>1868516</population>
        <population_rank>37</population_rank>
        <constitution_url>http://www.state.ne.us/legislative/statutes/C</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/nebraska-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/nebraska-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/nebraska-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/nebraska.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/nebraska.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/Nebraskagov</twitter_url>
        <facebook_url>https://www.facebook.com/nebraska.gov</facebook_url>
    </state>
    <state>
        <state>Nevada</state>
        <slug>nevada</slug>
        <code>NV</code>
        <nickname>The Silver State</nickname>
        <website>http://nv.gov</website>
        <admission_date>1864-10-31</admission_date>
        <admission_number>36</admission_number>
        <capital_city>Carson City</capital_city>
        <capital_url>http://www.carson.org</capital_url>
        <population>2790136</population>
        <population_rank>35</population_rank>
        <constitution_url>http://www.leg.state.nv.us/Const/NvConst.html</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/nevada-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/nevada-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/nevada-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/nevada.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/nevada.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>New Hampshire</state>
        <slug>new-hampshire</slug>
        <code>NH</code>
        <nickname>Granite State</nickname>
        <website>https://www.nh.gov</website>
        <admission_date>1788-06-21</admission_date>
        <admission_number>9</admission_number>
        <capital_city>Concord</capital_city>
        <capital_url>http://www.concordnh.gov</capital_url>
        <population>1323459</population>
        <population_rank>42</population_rank>
        <constitution_url>http://www.state.nh.us/constitution/constitution.html</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/new-hampshire-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/new-hampshire-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/new-hampshire-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/new-hampshire.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/new-hampshire.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/nhgov</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>New Jersey</state>
        <slug>new-jersey</slug>
        <code>NJ</code>
        <nickname>Garden State</nickname>
        <website>http://www.state.nj.us</website>
        <admission_date>1787-12-18</admission_date>
        <admission_number>3</admission_number>
        <capital_city>Trenton</capital_city>
        <capital_url>http://www.trentonnj.org</capital_url>
        <population>8899339</population>
        <population_rank>11</population_rank>
        <constitution_url>http://www.njleg.state.nj.us/lawsconstitution/consearch.asp</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/new-jersey-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/new-jersey-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/new-jersey-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/new-jersey.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/new-jersey.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>New Mexico</state>
        <slug>new-mexico</slug>
        <code>NM</code>
        <nickname>Land of Enchantment</nickname>
        <website>http://www.newmexico.gov</website>
        <admission_date>1912-01-06</admission_date>
        <admission_number>47</admission_number>
        <capital_city>Santa Fe</capital_city>
        <capital_url>http://www.santafenm.gov</capital_url>
        <population>2085287</population>
        <population_rank>36</population_rank>
        <constitution_url>http://www.loc.gov/law/guide/us-nm.html</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/new-mexico-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/new-mexico-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/new-mexico-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/new-mexico.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/new-mexico.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>New York</state>
        <slug>new-york</slug>
        <code>NY</code>
        <nickname>Empire State</nickname>
        <website>http://www.ny.gov</website>
        <admission_date>1788-07-26</admission_date>
        <admission_number>11</admission_number>
        <capital_city>Albany</capital_city>
        <capital_url>http://www.albanyny.org</capital_url>
        <population>19651127</population>
        <population_rank>3</population_rank>
        <constitution_url>https://www.dos.ny.gov/info/constitution.htm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/new-york-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/new-york-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/new-york-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/new-york.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/new-york.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/nygov</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>North Carolina</state>
        <slug>north-carolina</slug>
        <code>NC</code>
        <nickname>Old North State / Tar Heel State</nickname>
        <website>http://www.nc.gov</website>
        <admission_date>1789-11-21</admission_date>
        <admission_number>12</admission_number>
        <capital_city>Raleigh</capital_city>
        <capital_url>http://www.raleigh-nc.org</capital_url>
        <population>9848060</population>
        <population_rank>10</population_rank>
        <constitution_url>http://statelibrary.dcr.state.nc.us/nc/stgovt/preconst.htm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/north-carolina-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/north-carolina-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/north-carolina-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/north-carolina.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/north-carolina.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/NCdotGov</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>North Dakota</state>
        <slug>north-dakota</slug>
        <code>ND</code>
        <nickname>Peace Garden State / Flickertail State / Roughrider State</nickname>
        <website>http://www.nd.gov</website>
        <admission_date>1889-11-02</admission_date>
        <admission_number>39</admission_number>
        <capital_city>Bismarck</capital_city>
        <capital_url>http://www.bismarck.org</capital_url>
        <population>723393</population>
        <population_rank>48</population_rank>
        <constitution_url>http://www.legis.nd.gov/information/statutes/const-laws.html</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/north-dakota-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/north-dakota-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/north-dakota-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/north-dakota.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/north-dakota.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/ExperienceND</twitter_url>
        <facebook_url>https://www.facebook.com/ExperienceND</facebook_url>
    </state>
    <state>
        <state>Ohio</state>
        <slug>ohio</slug>
        <code>OH</code>
        <nickname>Buckeye State</nickname>
        <website>https://ohio.gov</website>
        <admission_date>1803-03-01</admission_date>
        <admission_number>17</admission_number>
        <capital_city>Columbus</capital_city>
        <capital_url>http://ci.columbus.oh.us</capital_url>
        <population>11570808</population>
        <population_rank>7</population_rank>
        <constitution_url>http://www.legislature.state.oh.us/constitution.cfm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/ohio-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/ohio-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/ohio-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/ohio.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/ohio.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/ohgov</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Oklahoma</state>
        <slug>oklahoma</slug>
        <code>OK</code>
        <nickname>Sooner State</nickname>
        <website>https://www.ok.gov</website>
        <admission_date>1907-11-16</admission_date>
        <admission_number>46</admission_number>
        <capital_city>Oklahoma City</capital_city>
        <capital_url>http://www.okc.gov</capital_url>
        <population>3850568</population>
        <population_rank>28</population_rank>
        <constitution_url>http://oklegal.onenet.net/okcon</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/oklahoma-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/oklahoma-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/oklahoma-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/oklahoma.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/oklahoma.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/okgov</twitter_url>
        <facebook_url>https://www.facebook.com/okgov</facebook_url>
    </state>
    <state>
        <state>Oregon</state>
        <slug>oregon</slug>
        <code>OR</code>
        <nickname>Beaver State</nickname>
        <website>http://www.oregon.gov</website>
        <admission_date>1859-02-14</admission_date>
        <admission_number>33</admission_number>
        <capital_city>Salem</capital_city>
        <capital_url>http://www.cityofsalem.net/Pages/default.aspx</capital_url>
        <population>3930065</population>
        <population_rank>27</population_rank>
        <constitution_url>http://bluebook.state.or.us/state/constitution/constitution.htm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/oregon-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/oregon-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/oregon-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/oregon.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/oregon.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Pennsylvania</state>
        <slug>pennsylvania</slug>
        <code>PA</code>
        <nickname>Keystone State</nickname>
        <website>http://www.pa.gov</website>
        <admission_date>1787-12-12</admission_date>
        <admission_number>2</admission_number>
        <capital_city>Harrisburg</capital_city>
        <capital_url>http://harrisburgpa.gov</capital_url>
        <population>12773801</population>
        <population_rank>6</population_rank>
        <constitution_url>http://sites.state.pa.us/PA_Constitution.html</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/pennsylvania-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/pennsylvania-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/pennsylvania-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/pennsylvania.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/pennsylvania.jpg</skyline_background_url>
        <twitter_url>https://www.facebook.com/visitPA</twitter_url>
        <facebook_url>https://twitter.com/visitPA</facebook_url>
    </state>
    <state>
        <state>Rhode Island</state>
        <slug>rhode-island</slug>
        <code>RI</code>
        <nickname>The Ocean State</nickname>
        <website>https://www.ri.gov</website>
        <admission_date>1790-05-29</admission_date>
        <admission_number>13</admission_number>
        <capital_city>Providence</capital_city>
        <capital_url>http://www.providenceri.com</capital_url>
        <population>1051511</population>
        <population_rank>43</population_rank>
        <constitution_url>http://webserver.rilin.state.ri.us/RiConstitution</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/rhode-island-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/rhode-island-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/rhode-island-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/rhode-island.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/rhode-island.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/rigov</twitter_url>
        <facebook_url>https://www.facebook.com/RIgov-Rhode-Island-Government-Online-24056655991</facebook_url>
    </state>
    <state>
        <state>South Carolina</state>
        <slug>south-carolina</slug>
        <code>SC</code>
        <nickname>Palmetto State</nickname>
        <website>http://www.sc.gov</website>
        <admission_date>1788-05-23</admission_date>
        <admission_number>8</admission_number>
        <capital_city>Columbia</capital_city>
        <capital_url>http://www.columbiasc.net</capital_url>
        <population>4774839</population>
        <population_rank>24</population_rank>
        <constitution_url>http://www.scstatehouse.gov/scconstitution/scconst.php</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/south-carolina-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/south-carolina-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/south-carolina-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/south-carolina.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/south-carolina.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/scgov</twitter_url>
        <facebook_url>http://www.facebook.com/pages/SCgov/12752057990</facebook_url>
    </state>
    <state>
        <state>South Dakota</state>
        <slug>south-dakota</slug>
        <code>SD</code>
        <nickname>Mount Rushmore State</nickname>
        <website>http://sd.gov</website>
        <admission_date>1889-11-02</admission_date>
        <admission_number>40</admission_number>
        <capital_city>Pierre</capital_city>
        <capital_url>http://ci.pierre.sd.us</capital_url>
        <population>844877</population>
        <population_rank>46</population_rank>
        <constitution_url>http://legis.sd.gov/statutes/Constitution</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/south-dakota-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/south-dakota-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/south-dakota-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/south-dakota.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/south-dakota.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Tennessee</state>
        <slug>tennessee</slug>
        <code>TN</code>
        <nickname>Volunteer State</nickname>
        <website>https://www.tn.gov</website>
        <admission_date>1796-06-01</admission_date>
        <admission_number>16</admission_number>
        <capital_city>Nashville</capital_city>
        <capital_url>http://www.nashville.gov</capital_url>
        <population>6495978</population>
        <population_rank>17</population_rank>
        <constitution_url>http://www.capitol.tn.gov/about/docs/TN-Constitution.pdf</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/tennessee-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/tennessee-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/tennessee-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/tennessee.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/tennessee.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/TNVacation</twitter_url>
        <facebook_url>https://www.facebook.com/tnvacation</facebook_url>
    </state>
    <state>
        <state>Texas</state>
        <slug>texas</slug>
        <code>TX</code>
        <nickname>Lone Star State</nickname>
        <website>https://www.texas.gov</website>
        <admission_date>1845-12-29</admission_date>
        <admission_number>28</admission_number>
        <capital_city>Austin</capital_city>
        <capital_url>http://www.austintexas.gov</capital_url>
        <population>26448193</population>
        <population_rank>2</population_rank>
        <constitution_url>http://www.constitution.legis.state.tx.us</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/texas-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/texas-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/texas-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/texas.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/texas.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/texasgov</twitter_url>
        <facebook_url>http://www.facebook.com/Texas.gov</facebook_url>
    </state>
    <state>
        <state>Utah</state>
        <slug>utah</slug>
        <code>UT</code>
        <nickname>The Beehive State</nickname>
        <website>https://utah.gov</website>
        <admission_date>1896-01-04</admission_date>
        <admission_number>45</admission_number>
        <capital_city>Salt Lake City</capital_city>
        <capital_url>http://www.slcgov.com</capital_url>
        <population>2900872</population>
        <population_rank>33</population_rank>
        <constitution_url>http://le.utah.gov/UtahCode/chapter.jsp?code=Constitution</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/utah-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/utah-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/utah-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/utah.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/utah.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/UtahGov</twitter_url>
        <facebook_url>https://www.facebook.com/utahgov</facebook_url>
    </state>
    <state>
        <state>Vermont</state>
        <slug>vermont</slug>
        <code>VT</code>
        <nickname>Green Mountain State</nickname>
        <website>http://vermont.gov</website>
        <admission_date>1791-03-04</admission_date>
        <admission_number>14</admission_number>
        <capital_city>Montpelier</capital_city>
        <capital_url>http://www.montpelier-vt.org</capital_url>
        <population>626630</population>
        <population_rank>49</population_rank>
        <constitution_url>http://www.leg.state.vt.us/statutes/const2.htm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/vermont-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/vermont-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/vermont-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/vermont.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/vermont.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/vermontgov</twitter_url>
        <facebook_url>https://www.facebook.com/MyVermont</facebook_url>
    </state>
    <state>
        <state>Virginia</state>
        <slug>virginia</slug>
        <code>VA</code>
        <nickname>Old Dominion State</nickname>
        <website>https://www.virginia.gov</website>
        <admission_date>1788-06-25</admission_date>
        <admission_number>10</admission_number>
        <capital_city>Richmond</capital_city>
        <capital_url>http://www.richmondgov.com</capital_url>
        <population>8260405</population>
        <population_rank>12</population_rank>
        <constitution_url>http://hodcap.state.va.us/publications/Constitution-01-13.pdf</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/virginia-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/virginia-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/virginia-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/virginia.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/virginia.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Washington</state>
        <slug>washington</slug>
        <code>WA</code>
        <nickname>The Evergreen State</nickname>
        <website>http://www.wa.gov</website>
        <admission_date>1889-11-11</admission_date>
        <admission_number>42</admission_number>
        <capital_city>Olympia</capital_city>
        <capital_url>http://www.ci.olympia.wa.us</capital_url>
        <population>6971406</population>
        <population_rank>13</population_rank>
        <constitution_url>http://www.leg.wa.gov/lawsandagencyrules/pages/constitution.aspx</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/washington-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/washington-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/washington-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/washington.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/washington.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/wagov</twitter_url>
        <facebook_url></facebook_url>
    </state>
    <state>
        <state>West Virginia</state>
        <slug>west-virginia</slug>
        <code>WV</code>
        <nickname>Mountain State</nickname>
        <website>http://www.wv.gov</website>
        <admission_date>1863-06-20</admission_date>
        <admission_number>35</admission_number>
        <capital_city>Charleston</capital_city>
        <capital_url>http://www.cityofcharleston.org</capital_url>
        <population>1854304</population>
        <population_rank>38</population_rank>
        <constitution_url>http://www.legis.state.wv.us/WVCODE/WV_CON.cfm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/west-virginia-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/west-virginia-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/west-virginia-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/west-virginia.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/west-virginia.jpg</skyline_background_url>
        <twitter_url>https://twitter.com/wvgov</twitter_url>
        <facebook_url>https://www.facebook.com/wvgov</facebook_url>
    </state>
    <state>
        <state>Wisconsin</state>
        <slug>wisconsin</slug>
        <code>WI</code>
        <nickname>Badger State</nickname>
        <website>https://www.wisconsin.gov</website>
        <admission_date>1848-05-29</admission_date>
        <admission_number>30</admission_number>
        <capital_city>Madison</capital_city>
        <capital_url>http://www.ci.madison.wi.us</capital_url>
        <population>5742713</population>
        <population_rank>20</population_rank>
        <constitution_url>http://www.legis.state.wi.us/rsb/2wiscon.html</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/wisconsin-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/wisconsin-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/wisconsin-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/wisconsin.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/wisconsin.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
    <state>
        <state>Wyoming</state>
        <slug>wyoming</slug>
        <code>WY</code>
        <nickname>Equality State</nickname>
        <website>http://www.wyo.gov</website>
        <admission_date>1890-07-10</admission_date>
        <admission_number>44</admission_number>
        <capital_city>Cheyenne</capital_city>
        <capital_url>http://www.cheyennecity.org</capital_url>
        <population>582658</population>
        <population_rank>50</population_rank>
        <constitution_url>http://legisweb.state.wy.us/statutes/constitution.aspx?file=titles/97Title97.htm</constitution_url>
        <state_flag_url>https://cdn.civil.services/us-states/flags/wyoming-large.png</state_flag_url>
        <state_seal_url>https://cdn.civil.services/us-states/seals/wyoming-large.png</state_seal_url>
        <map_image_url>https://cdn.civil.services/us-states/maps/wyoming-large.png</map_image_url>
        <landscape_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/landscape/wyoming.jpg</landscape_background_url>
        <skyline_background_url>https://cdn.civil.services/us-states/backgrounds/1280x720/skyline/wyoming.jpg</skyline_background_url>
        <twitter_url>null</twitter_url>
        <facebook_url>null</facebook_url>
    </state>
</states>
```
    
</details>

To start with we will create and use a class POJO for the data.

<details>
    <summary>Class POJO</summary>
    
``` java
    public class State {
        private String state;
        private String slug;
        private String code;
        private String nickname;
        private String website;
        private String admission_date;
        private int admission_number;
        private String capital_url;
        private String capitalName;
        private int population;
        private int population_rank;
        private String constitution_url;
        private String state_flag_url;
        private String state_seal_url;
        private String map_image_url;
        private String landscape_background_url;
        private String skyline_background_url;
        private String twitter_url;
        private String facebook_url;

        public State() {
            setState("");
            setSlug("");
            setCode("");
            setNickname("");
            setWebsite("");
            setAdmission_date("");
            setAdmission_number(0);
            setCapitalName("");
            setCapital_url("");
            setPopulation(0);
            setPopulation_rank(0);
            setConstitution_url("");
            setState_flag_url("");
            setState_seal_url("");
            setMap_image_url("");
            setLandscape_background_url("");
            setSkyline_background_url("");
            setTwitter_url("");
            setFacebook_url("");
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCapitalName() {
            return capitalName;
        }

        public void setCapitalName(String capitalName) {
            this.capitalName = capitalName;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getAdmission_date() {
            return admission_date;
        }

        public void setAdmission_date(String admission_date) {
            this.admission_date = admission_date;
        }

        public int getAdmission_number() {
            return admission_number;
        }

        public void setAdmission_number(int admission_number) {
            this.admission_number = admission_number;
        }

        public String getCapital_url() {
            return capital_url;
        }

        public void setCapital_url(String capital_url) {
            this.capital_url = capital_url;
        }

        public int getPopulation_rank() {
            return population_rank;
        }

        public void setPopulation_rank(int population_rank) {
            this.population_rank = population_rank;
        }

        public String getConstitution_url() {
            return constitution_url;
        }

        public void setConstitution_url(String constitution_url) {
            this.constitution_url = constitution_url;
        }

        public String getState_flag_url() {
            return state_flag_url;
        }

        public void setState_flag_url(String state_flag_url) {
            this.state_flag_url = state_flag_url;
        }

        public String getState_seal_url() {
            return state_seal_url;
        }

        public void setState_seal_url(String state_seal_url) {
            this.state_seal_url = state_seal_url;
        }

        public String getMap_image_url() {
            return map_image_url;
        }

        public void setMap_image_url(String map_image_url) {
            this.map_image_url = map_image_url;
        }

        public String getLandscape_background_url() {
            return landscape_background_url;
        }

        public void setLandscape_background_url(String landscape_background_url) {
            this.landscape_background_url = landscape_background_url;
        }

        public String getSkyline_background_url() {
            return skyline_background_url;
        }

        public void setSkyline_background_url(String skyline_background_url) {
            this.skyline_background_url = skyline_background_url;
        }

        public String getTwitter_url() {
            return twitter_url;
        }

        public void setTwitter_url(String twitter_url) {
            this.twitter_url = twitter_url;
        }

        public String getFacebook_url() {
            return facebook_url;
        }

        public void setFacebook_url(String facebook_url) {
            this.facebook_url = facebook_url;
        }

        @Override
        public String toString() {
            return "State{" +
                    "state='" + state + '\'' +
                    ", slug='" + slug + '\'' +
                    ", code='" + code + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", website='" + website + '\'' +
                    ", admission_date='" + admission_date + '\'' +
                    ", admission_number=" + admission_number +
                    ", capital_url='" + capital_url + '\'' +
                    ", capitalName='" + capitalName + '\'' +
                    ", population=" + population +
                    ", population_rank=" + population_rank +
                    ", constitution_url='" + constitution_url + '\'' +
                    ", state_flag_url='" + state_flag_url + '\'' +
                    ", state_seal_url='" + state_seal_url + '\'' +
                    ", map_image_url='" + map_image_url + '\'' +
                    ", landscape_background_url='" + landscape_background_url + '\'' +
                    ", skyline_background_url='" + skyline_background_url + '\'' +
                    ", twitter_url='" + twitter_url + '\'' +
                    ", facebook_url='" + facebook_url + '\'' +
                    '}';
    }
}
```

</details>

We will store our states data using an arraylist storing state objects that we created in our POJO. We will pull from this file the same way we did in our first example and parse the data the same way, but using our set methods to store the data used to create a state object to add to the arraylist.

``` java
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
```

To run this method and see output you can try the following.

``` java
public static void main(String[] args) {
        System.out.println("Reading the States XML file...");
        ArrayList<State> states = readStates("states.xml");
        printStates(states);
    }
    
    private static void printStates(ArrayList<State> states) {
        for (State state : states) {
            System.out.println(state);
        }
    }
```
