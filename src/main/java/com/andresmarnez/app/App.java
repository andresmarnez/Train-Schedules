package com.andresmarnez.app;

import com.andresmarnez.domain.Station;
import com.andresmarnez.exceptions.TRAINCODE;
import com.andresmarnez.exceptions.TrainException;
import com.andresmarnez.service.StationDataService;
import com.andresmarnez.util.HibernateUtil;

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

		while (true) {

			System.out.print(
					"Choose one of the following options:\n" +
							"1. Stations arrivals/departures.\n" +
							"2. See train status.\n" +
							"3. List all stations.\n" +
							"4. Get XML list.\n" +
							"5. Get JSON list.\n" +
							"6. Exit program.\n" +
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
				case "3":
				case "4":
				case "5":
				case "6":
					System.out.println("Hope to see you soon!");
					return;
			}

		}
	}

	private void stationMenu() {

		StationDataService service = new StationDataService();

		System.out.print(
				"\nTRAIN SCHEDULER\\STATIONS\n" +
						"Please enter the station number or name (leave empty to list all stations):\n" + TERMINAL_LINE
		);

		String answer = scanner.nextLine();

		if (answer.matches("[1-9]*")) {
			showStationInfo(service.findById(Long.parseLong(answer)));

		} else if (answer.isBlank()) {
		}

	}

	private void showStationInfo(Station station) {

		if (station == null) {
			System.out.println("Sorry, that station does not exists or id/name is incorrect.\n" +
					"Do you wish to try again? (Y/n)");
			String answer = scanner.nextLine();

			if (answer.matches("[yY]")) {
				stationMenu();
				return;

			} else if (answer.matches("[nN]]")) {
				return;
			}

		} else {

			System.out.println("Station: " + station);
			System.out.println("");
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
