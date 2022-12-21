package com.andresmarnez.app;

import com.andresmarnez.dao.GenericDAOImpl;
import com.andresmarnez.domain.Line;
import com.andresmarnez.domain.Station;
import com.andresmarnez.domain.Train;
import com.andresmarnez.exceptions.TRAINCODE;
import com.andresmarnez.exceptions.TrainException;
import com.andresmarnez.service.*;
import com.andresmarnez.util.HibernateUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class App {

	private final Scanner scanner = new Scanner(System.in);

	private static final String TERMINAL_LINE = "->";
	private static final String X_LINE = "\t\t\t\t\t EXIT: 'X' + ENTER";
	private static final String TITLE =
			"    ______)                   __                            \n" +
					"   (, /          ,        (__/  ) /)        /)     /)       \n" +
					"     /  __  _     __        / _  (/    _  _(/     //  _  __ \n" +
					"  ) /  / (_(_(__(_/ (_   ) / (__ / )__(/_(_(_(_(_(/__(/_/ (_\n" +
					" (_/                    (_/                                 \n" +
					"                                                            \n" +
					"Welcome to the Train Scheduler.";


	private void mainMenu() throws TrainException {

		while (true) {

			System.out.println("\nTRAIN SCHEDULER");
			System.out.print(
					"Choose one of the following options:\n" +
							"1. Stations arrivals/departures.\n" +
							"2. Update train status.\n" +
							"3. Destroy all retired trains.\n" +
							"4. Create a new line between stations.\n" +
							"5. List all stations' schedule.\n" +
							"6. Generate XML.\n" +
							"7. Generate JSON.\n" +
							"8. Restore last deleted item. (Currently not fully working)\n" +
							"9. Exit program.\n" +
							TERMINAL_LINE
			);

			String answer = scanner.nextLine();
			switch (answer) {

				default:
					System.out.println("Sorry, that is not a correct option.\n");
					break;
				case "1":
					stationMenu();
					break;
				case "2":
					getTrainInfo();
					break;
				case "3":
					deleteTrains();
					break;
				case "4":
					createLine();
					break;
				case "5":
					getAllStationsInfo();
					break;
				case "6":
					generateXML();
					break;
				case "7":
					generateJSON();
					break;
				case "8":
					restoreLast();
					break;
				case "9":
					System.out.println("Hope to see you soon!");
					throw new TrainException(TRAINCODE.EXIT);
			}
		}
	}

	private void deleteTrains() throws TrainException {
		TrainDataService trainDataService = new TrainDataService();
		trainDataService.deleteRetiredTrains();
	}

	private void createLine() throws TrainException {

		while (true) {

			LineDataService lineDataService = new LineDataService();
			Collection<Station> stations = new StationDataService().getAllStations();

			System.out.println("\nTRAIN SCHEDULER\\NEW LINE" +
					X_LINE +
					"\n\nSelect the ORIGIN station:");

			stations.forEach(s -> System.out.println(s.getId() + " - " + s.getName() + ", " + s.getCity()));
			System.out.print(TERMINAL_LINE);
			String originId = scanner.nextLine();

			if (originId.matches("[xX]")) return;
			else if (!originId.matches("[0-9]+")) System.out.println("Station was not chosen correctly," +
					" please use the option numbers.");
			else if (Integer.parseInt(originId) > stations.size()) {
				System.out.println("The station number do not belong" +
						" to any of the options.");
				continue;
			}

			System.out.println("\nSelect the END station:");
			stations.forEach(s -> System.out.println(s.getId() + " - " + s.getName() + ", " + s.getCity()));
			System.out.print(TERMINAL_LINE);
			String endId = scanner.nextLine();

			if (originId.matches("[xX]")) return;
			else if (!endId.matches("[0-9]+")) System.out.println("Station was not chosen correctly, " +
					"please use the option numbers.");
			else if (Integer.parseInt(endId) > stations.size()) {
				System.out.println("The station numbers do not belong" +
						" to any of the options.");
				continue;
			}

			lineDataService.createLineByPoints(Long.parseLong(originId), Long.parseLong(endId));

			return;
		}
	}

	private void restoreLast() throws TrainException{

		if (GenericDAOImpl.lastObjectSaved == null){
			System.out.println("There is no item to be restored.");
			return;
		}

		System.out.println("The last object deleted was:\n" +
				GenericDAOImpl.lastObjectSaved +
				"\nDo you wish to recover it? (y/n)");

		String ans = scanner.nextLine();
		if (ans.matches("[nN]")){
			System.out.println("We won't restore it then.");
		} else if (ans.matches("[yY]")) {
			System.out.println("Restoring item...");
		}

		if (GenericDAOImpl.lastObjectSaved instanceof Train){

			GenericDAOImpl<Train> genericDAO = new GenericDAOImpl<>(Train.class);
			genericDAO.restoreLast();
			genericDAO.saveLast(null);
		}
	}

	private void getTrainInfo() throws TrainException {
		TrainDataService TrainDataService = new TrainDataService();

		while (true) {
			System.out.print(
					"\nTRAIN SCHEDULER\\TRAINS" +
							X_LINE +
							"\nPlease enter the serial number of the train or leave empty to list all trains:\n" +
							TERMINAL_LINE
			);

			String answer = scanner.nextLine();

			if (answer.isBlank()) {
				TrainDataService.getAllTrains().forEach(System.out::println);

			} else if (answer.matches("[xX]")) {
				break;

			} else if (answer.matches("[0-9]+")) {
				trainMenu(TrainDataService.getTrainById(Long.parseLong(answer)));
				break;
			} else {
				System.out.println("Sorry, that option doesn't exist.\n");
			}
		}
	}

	private void trainMenu(Train train) throws TrainException {
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
					trainDataService.updateCheckedTimeById(LocalDateTime.now(), train.getId());
					System.out.println("Done.");
					break;
				case "2":
					trainDataService.updateRetirementById(LocalDateTime.now(), train.getId());
					break;
				case "3":
					return;
			}

			trainMenu(trainDataService.getTrainById(train.getId()));
		}


	}

	private void stationMenu() throws TrainException {

		StationDataService service = new StationDataService();

		while (true) {

			System.out.print(
					"\nTRAIN SCHEDULER\\STATIONS" +
							X_LINE +
							"\nPlease enter the station number or name (leave empty to list all stations):\n" + TERMINAL_LINE
			);

			String answer = scanner.nextLine();

			if (answer.isBlank()) service.getAllStations().forEach(System.out::println);
			else if (answer.matches("[1-9]+")) getStationInfo(service.findById(Long.parseLong(answer)));
			else if (!answer.matches("[xX]")) getStationInfo(service.getStationByName(answer));
			else break;
		}
	}

	private void getAllStationsInfo() throws TrainException {

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

	private void getStationInfo(Station station) throws TrainException {

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

	private void addStationsXML(List<Station> stations, Document document) {

		Element rootElement = document.createElement("stations");
		document.appendChild(rootElement);

		for (Station station :
				stations) {

			Element stationElement = document.createElement("station");
			stationElement.setAttribute("id", "" + station.getId());

			Element stationName = document.createElement("name");
			Element city = document.createElement("city");
			Element arrivals = document.createElement("arrivals");
			station.getArrivalLines().forEach(line -> {

				String originCity = line.getOrigin().getCity();

				line.getConnections().forEach(con -> {
					Element connection = document.createElement("connection");
					Element origin = document.createElement("origin");
					Element time = document.createElement("arrival-time");

					origin.setTextContent(originCity);
					time.setTextContent(con.getArrivalTime().toString());

					connection.appendChild(origin);
					connection.appendChild(time);
					arrivals.appendChild(connection);
				});
			});

			Element departures = document.createElement("departures");

			station.getDepartureLines().forEach(line -> {

				String endCity = line.getEnd().getCity();

				line.getConnections().forEach(con -> {
					Element connection = document.createElement("connection");
					Element end = document.createElement("destiny");
					Element time = document.createElement("departure-time");

					end.setTextContent(endCity);
					time.setTextContent(con.getArrivalTime().toString());

					connection.appendChild(end);
					connection.appendChild(time);
					departures.appendChild(connection);
				});
			});

			Element coordinates = document.createElement("coordinates");
			stationName.setTextContent(station.getName());
			city.setTextContent(station.getCity());
			coordinates.setTextContent(station.getCoordinates());

			stationElement.appendChild(stationName);
			stationElement.appendChild(city);
			stationElement.appendChild(coordinates);
			stationElement.appendChild(arrivals);
			stationElement.appendChild(departures);
			rootElement.appendChild(stationElement);
		}
	}

	private void generateXML() throws TrainException {

		StationDataService service = new StationDataService();
		List<Station> stationList = service.getAllStations();

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			addStationsXML(stationList, doc);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer trans = factory.newTransformer();
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
			throw new TrainException("XML couldn't be created.", TRAINCODE.XML_ERROR);
		}

	}

	private void generateJSON() throws TrainException {
		JSONObject obj = new JSONObject();
		JSONArray stationArray = new JSONArray();
		StationDataService service = new StationDataService();

		Collection<Station> stations = service.getAllStations();

		for (Station station : stations) {
			JSONObject stationJSON = new JSONObject();
			stationJSON.put("id", station.getId());
			stationJSON.put("name", station.getName());
			stationJSON.put("city", station.getCity());
			stationJSON.put("coordinates", station.getCoordinates());

			JSONArray arrivals = new JSONArray();
			JSONArray departures = new JSONArray();

			station.getArrivalLines().forEach(l -> {
				String origin = l.getOrigin().getCity();
				l.getConnections()
						.forEach(c -> {
							JSONObject actualArrival = new JSONObject();
							actualArrival.put("origin", origin);
							actualArrival.put("arrival-time", c.getArrivalTime());
							arrivals.put(actualArrival);
						});
			});

			station.getDepartureLines().forEach(l -> {
				String destiny = l.getEnd().getCity();
				l.getConnections()
						.forEach(c -> {
							JSONObject actualArrival = new JSONObject();
							actualArrival.put("destiny", destiny);
							actualArrival.put("departure-time", c.getDepartureTime());
							departures.put(actualArrival);
						});
			});

			stationJSON.put("arrivals", arrivals);
			stationJSON.put("departures", departures);
			stationArray.put(stationJSON);
		}

		obj.put("stations", stationArray);
		System.out.println(obj.toString(4));
	}

	public static void main(String[] args) {

		System.out.println(TITLE);
		App app = new App();

		while (true) {

			try {
				HibernateUtil.getSessionFactory().openSession();
				app.mainMenu();

			} catch (TrainException e) {

				switch (e.getCode()) {
					case SESSION_FACTORY_FAILURE:
						System.out.println("\n<ERROR> Couldn't establish connection to the DB, please read README.md " +
								"or contact an admin.\n" +
								"Please read hibernate.log for further technical details.");
					case EXIT:
						return;
					default:
						System.out.println(e);
						System.out.println("Trying to recover from an error. Application will restart.");
						HibernateUtil.closeSession();
				}
			}
		}
	}
}
