

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeCodeChallenge {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ArrayList<Employee> employeeList = new ArrayList<Employee>();

		String keepGoing;
		do {
			employeeList = createEmployeeList();
			System.out.println("Here is the list of employees: ");
			for (Employee e : employeeList) {
				System.out.println(e.getName());
			}

			System.out.println(
					"Would you like to learn more about a current employee (enter \"a\"), enter a new employee (enter \"b\") or exit (enter \"c\")?");
			char ans = scan.next().charAt(0);

			switch (ans) {
			case 'a':
				getMoreInfo(scan, employeeList);
				break;
			case 'b':
				addEmployee(scan);
				break;
			case 'c':
				System.out.println("Bye!");
				System.exit(0);
				break;
			}
			System.out.println("Want to continue? Enter Y or N: ");
			keepGoing = scan.next();
		} while (keepGoing.equalsIgnoreCase("Y"));
		
		System.out.println("Bye!");
	}

	public static void addEmployee(Scanner scan) {
		Employee employee = new Employee();

		String name = Validator.getString(scan, "Please enter employee name: ");
		employee.setName(name);
		int age = Validator.getInt(scan, "Please enter employee age: ");
		employee.setAge(age);
		String favoriteFood = Validator.getString(scan, "Please enter employee favorite food: ");
		employee.setFavoriteFood(favoriteFood);

		writeToFile(employee);
	}

	private static void getMoreInfo(Scanner scan, ArrayList<Employee> employeeList) {
		System.out.println("Enter the employee you want to know more about: ");
		String userIn = scan.next();
		boolean nameExists = false;
		for (Employee e : employeeList) {
			if (e.getName().equalsIgnoreCase(userIn)) {
				System.out.printf("Name: %s\nAge: %s\nFavorite Food:%s\n", e.getName(), e.getAge(),
						e.getFavoriteFood());
				nameExists = true;
			}

		}
		if (nameExists == false) {
			System.out.println("That name doesn't exist in the employee list");
		}
	}

	public static ArrayList<Employee> createEmployeeList() {
		ArrayList<Employee> employeeArrayList = new ArrayList<Employee>();
		Path readFile = Paths.get("employees.txt");
		File file = readFile.toFile();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader reader = new BufferedReader(fr);
			String line = reader.readLine();

			while (line != null) {
				// System.out.println(line);
				// splits the line up on commas, then adds them to an array
				// then uses the pieces from the array to create a new Employee object and add
				// that to employeeArrayList
				String singleEmployeeArray[] = line.split(",");
				employeeArrayList.add(new Employee(singleEmployeeArray[0],
						Integer.parseInt(singleEmployeeArray[1].trim()), singleEmployeeArray[2]));
				line = reader.readLine();
			}
			reader.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Something went wrong with this!");
			e.printStackTrace();
		}
		return employeeArrayList;
	}

	public static void createDirectory(String dirString) { // referencing directory path
		Path dirPath = Paths.get(dirString);
		System.out.println(dirPath.toAbsolutePath());
		if (Files.notExists(dirPath)) {
			try {
				Files.createDirectory(dirPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Not sure what happened, contact customer service.");
			}
		}
	}

	public static void createFile(String dirString, String fileString) {
		Path filePath = Paths.get(dirString, fileString);
		if (Files.notExists(filePath)) {
			try {
				Files.createFile(filePath);
				System.out.println("Your file was created successfully.");
			} catch (IOException e) {
				System.out.println("Something went wrong with the file creation.");
				e.printStackTrace();
			}
		}
	}

	public static void writeToFile(Employee employee) {
		Path writeFile = Paths.get("employees.txt");
		File file = writeFile.toFile();
		try {
			PrintWriter printOut = new PrintWriter(new FileOutputStream(file, true));
			printOut.println(employee);
			printOut.close(); //
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}