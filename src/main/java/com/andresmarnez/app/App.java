package com.andresmarnez.app;


import com.andresmarnez.domain.Station;
import com.andresmarnez.domain.Train;
import com.andresmarnez.exceptions.TrainException;
import com.andresmarnez.service.StationDataService;
import com.andresmarnez.service.TrainDataService;
import com.andresmarnez.util.HibernateUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;


public class App {

	private final Scanner scanner = new Scanner(System.in);

	private static final String TERMINAL_LINE = "->";
	private static final String TITLE =
			"    ______)                   __                            \n" +
					"   (, /          ,        (__/  ) /)        /)     /)       \n" +
					"     /  __  _     __        / _  (/    _  _(/     //  _  __ \n" +
					"  ) /  / (_(_(__(_/ (_   ) / (__ / )__(/_(_(_(_(_(/__(/_/ (_\n" +
					" (_/                    (_/                                 \n" +
					"                                                            \n" +
					"Welcome to the Train Scheduler.";

	public App() {
	}


	private void mainMenu() throws TrainException {
		TrainDataService trainDataService = new TrainDataService();

		while (true) {

			System.out.print(
					"\nChoose one of the following options:\n" +
							"1. Stations arrivals/departures.\n" +
							"2. Update train status.\n" +
							"3. Destroy all retired trains.\n" +
							"4. List all stations' schedule.\n" +
							"5. Get XML list.\n" +
							"6. Get JSON list. (Not implemented yet)\n" +
							"7. Exit program.\n" +
							TERMINAL_LINE
			);

			String answer = scanner.nextLine();
			switch (answer) {

				default:
					System.out.println("Sorry, that is not a correct option.\n");
					mainMenu();
					return;
				case "1":
					stationMenu();
					break;
				case "2":
					getTrainInfo();
					break;
				case "3":
					trainDataService.deleteRetiredTrains();
					break;
				case "4":
					getAllStationsInfo();
					break;
				case "5":
					makeXMLStation();
					break;
				case "6":
					System.out.println("SOON AVAILABLE!");
					break;
				case "7":
					System.out.println("Hope to see you soon!");
					return;
			}

		}
	}

	private void getTrainInfo(){
		TrainDataService TrainDataService = new TrainDataService();

		System.out.print(
				"\nTRAIN SCHEDULER\\TRAINS\n" +
						"Please enter the serial number of the train or leave empty to list all trains:\n" + TERMINAL_LINE
		);

		String answer = scanner.nextLine();

		if (answer.matches("[1-9]+")) {
			trainMenu(TrainDataService.getTrainById(Long.parseLong(answer)));

		} else if (answer.isBlank()) {
			TrainDataService.getAllTrains().forEach(System.out::println);
			System.out.println();
			getTrainInfo();

		} else {
			System.out.println("Sorry, that option doesn't exist.\n");
			getTrainInfo();
		}
	}

	private void trainMenu(Train train){
		TrainDataService trainDataService = new TrainDataService();

		if (train == null) {

			System.out.println("Sorry, that train does not exists or id is incorrect.\n" +
					"Do you wish to try again? (Y/n)");
			String tryAgain = scanner.nextLine();

			if (tryAgain.matches("[yY]")) {
				getTrainInfo();
			} else if (!tryAgain.matches("[nN]")) {
				trainMenu(null);
			}
		} else {

			System.out.println("\nTRAIN SCHEDULER\\TRAINS\\" + train.getId());
			System.out.println(train);
			System.out.println("Actions:\n" +
					"1. Set last revision to today.\n" +
					"2. Set retire day for train to today.\n" +
					"3. Exit.");

			String answer = scanner.nextLine();
			switch (answer) {

				default:
					System.out.println("Sorry, that is not a correct option.\n");
					break;
				case "1":
					trainDataService.updateCheckedTimeById(LocalDateTime.now(),train.getId());
					System.out.println("Done.");
					break;
				case "2":
					trainDataService.updateRetirementById(LocalDateTime.now(),train.getId());
					break;
				case "3":
					return;
			}

			trainMenu(trainDataService.getTrainById(train.getId()));
		}


	}

	private void stationMenu() {

		StationDataService service = new StationDataService();

		System.out.print(
				"\nTRAIN SCHEDULER\\STATIONS\n" +
						"Please enter the station number or name (leave empty to list all stations):\n" + TERMINAL_LINE
		);

		String answer = scanner.nextLine();

		if (answer.matches("[1-9]+")) {
			getStationInfo(service.findById(Long.parseLong(answer)));
		} else if (answer.isBlank()) {
			service.getAllStations().forEach(System.out::println);
			System.out.println();
			stationMenu();
		} else {
			getStationInfo(service.getStationByName(answer));
		}
	}

	private void getAllStationsInfo() {

		StationDataService service = new StationDataService();

		System.out.print("\nTRAIN SCHEDULER\\ALL STATIONS\n");
		service.getAllStations()
				.forEach(station -> {

					System.out.println(station.toString().toUpperCase());
					System.out.println("\nARRIVALS");
					station.getArrivalLines()
							.stream()
							.flatMap(l -> l.getConnections().stream())
							.map(c -> c.getSchedule(true))
							.forEach(System.out::println);
					System.out.println("\nDEPARTURES");
					station.getDepartureLines()
							.stream()
							.flatMap(l -> l.getConnections().stream())
							.map(c -> c.getSchedule(false))
							.forEach(System.out::println);
					System.out.println();
				});

	}

	private void getStationInfo(Station station) {

		if (station == null) {
			System.out.println("Sorry, that station does not exists or id/name is incorrect.\n" +
					"Do you wish to try again? (Y/n)");
			String answer = scanner.nextLine();

			if (answer.matches("[yY]")) {
				stationMenu();
			} else if (!answer.matches("[nN]")) {
				getStationInfo(null);
			}

		} else {

			System.out.println("\nTRAIN SCHEDULER\\STATIONS\\" + station.getName().toUpperCase());
			System.out.println("Info regarding the station:\n" +
					"1. Arrivals\n" +
					"2. Departures\n" +
					"3. Exit.");

			String answer = scanner.nextLine();
			switch (answer) {

				default:
					System.out.println("Sorry, that is not a correct option.\n");
					break;
				case "1":
					station.getArrivalLines().stream()
							.flatMap(s -> s.getConnections().stream())
							.map(c -> c.getSchedule(true))
							.sorted()
							.forEach(System.out::println);
					break;
				case "2":
					station.getDepartureLines().stream()
							.flatMap(s -> s.getConnections().stream())
							.map(c -> c.getSchedule(false))
							.sorted()
							.forEach(System.out::println);
					break;
				case "3":
					return;
			}

			getStationInfo(station);
		}

	}

	private void addStations(List<Station> stations, Document document){

		Element rootElement = document.createElement("stations");
		document.appendChild(rootElement);
		int x = 1;

		for (Station station :
				stations) {

			Element stationElement = document.createElement("station");
			stationElement.setAttribute("id","" + station.getId());

			Element stationName = document.createElement("name");
			Element city = document.createElement("city");
			Element coordinates = document.createElement("coordinates");
			stationName.setTextContent(station.getName());
			city.setTextContent(station.getCity());
			coordinates.setTextContent(station.getCoordinates());

			stationElement.appendChild(stationName);
			stationElement.appendChild(city);
			stationElement.appendChild(coordinates);
			rootElement.appendChild(stationElement);
		}
	}

	private void makeXMLStation(){

		StationDataService service = new StationDataService();
		List<Station> stationList = service.getAllStations();

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = (Document) docBuilder.newDocument();

			addStations(stationList,doc);

			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.METHOD, "xml");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(2));
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			trans.transform(source, result);
			String xmlString = sw.toString();
			System.out.println(xmlString);

		} catch (Exception e) {
			System.out.println("An error has ocurred.");
		}

	}

	public static void main(String[] args) {

		System.out.println(TITLE);
		App app = new App();

		try {

			HibernateUtil.getSessionFactory().openSession();
		} catch (IllegalStateException | TrainException ex) {
			System.out.println("\n<ERROR> Couldn't establish connection to the DB, please read README.md or contact an admin.\n" +
					"Please read hibernate.log for further technical details.");
			return;
		}

		try {
			app.mainMenu();

		} catch (TrainException e) {

		}

	}
}
