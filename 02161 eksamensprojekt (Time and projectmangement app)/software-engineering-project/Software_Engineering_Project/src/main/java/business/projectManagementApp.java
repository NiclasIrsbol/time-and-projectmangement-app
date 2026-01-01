package business;

import java.util.ArrayList;
import java.util.Scanner;

public class projectManagementApp {

    /**
     * @author Andrej Kitanovski (s236136)
     */

    static ArrayList<Project> projects = new ArrayList<>();
    static ArrayList<Employee> employees = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        Employee qwer = new Employee("qwer", "admin", "available", 0);
        Employee huba = new Employee("huba", "it-support","available", 0);
        Employee.addEmployeeToList(qwer, employees); qwer.getEmployeeList().add(qwer);
        Employee.addEmployeeToList(huba, employees); huba.getEmployeeList().add(huba);


        Scanner scanner = new Scanner(System.in);
        login(scanner);
    }

    public static void login(Scanner scanner) throws Exception {
        System.out.println("Enter username:");
        String username = scanner.next();
        if (username.equalsIgnoreCase("qwer")) {
            scanner.nextLine();
            System.out.println("Login successful as admin 'qwer'");
            adminMenu(scanner, projects);
        } else if (username.equalsIgnoreCase("huba")) {
            scanner.nextLine();
            System.out.println("Login successful as employee 'huba'");
            employeeMenu(scanner, employees);
        } else {
            System.out.println("Not a valid user");
            login(scanner);
        }
    }


    public static void adminMenu(Scanner scanner, ArrayList<Project> projectList) throws Exception {
        Project project = null;
        Activity activity = null;
        Employee employee = null;
        String input = "";
        while (!input.equals("6")) {
            System.out.println("\n Select an operation! \n 1: Manage Projects \n 2: Manage Activities \n 3: Manage Employee \n 4: Assign Employee to activity in project \n 5: Register working time \n 6: Logout");
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    System.out.println("Managing project...");
                    projectSubMenu(scanner, input, projectList);
                    break;
                case "2":
                    System.out.println("Managing activities...");
                    activitiesSubMenu(scanner, projectList);
                    break;
                case "3":
                    System.out.println("Managing Employees...");
                    employeesSubMenu(scanner, projectList);
                    break;
                case "4":
                    System.out.println("Assigning Employee to activity...");
                    Employee.assignEmployeeToActivityInProject(scanner, projectList, employees);
                    break;
                case "5":
                    System.out.println("Registering time...");
                    Employee.userInputRegisterWorkTime(scanner, employees);
                    break;
                case "6":
                    System.out.println("Logging out...");
                    logout(scanner);
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number between 1 and 6.");
                    break;
            }
        }
    }

    public static void employeeMenu(Scanner scanner, ArrayList<Employee> employeesList) throws Exception {
        String input = "";
        while (!input.equals("2")) {
            System.out.println("\n Select an operation! \n 1: Register work time \n 2: Register time off \n 3: Logout");
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    System.out.println("Registering work time...");
                    Employee.userInputRegisterWorkTime(scanner, employeesList);
                    break;
                case "2":
                    System.out.println("Registering time off...");
                    Activity.getVacationInfo(scanner, employeesList.get(1), true);
                    break;
                case "3":
                    System.out.println("Logging out...");
                    logout(scanner);
                default:
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    break;
            }
        }
    }

    public static void projectSubMenu(Scanner scanner, String input, ArrayList<Project> projectList) throws Exception {
        Project project = null;
        Activity activity = null;
        input = "";
        System.out.println("\n 1.1: Create project \n 1.2: See Project-List \n 1.3: Edit Project \n 1.4: Delete project \n 1.5: Back to main menu");
        input = scanner.nextLine();
        switch (input) {
            case "1.1":
                System.out.println("Creating project...");
                Project newProject = Project.createProject(scanner, projectList);
                projectList.add(newProject);
                break;
            case "1.2":
                System.out.println("Displaying project-list...");
                if (projectList.isEmpty()) {
                    System.out.println("No active projects");
                } else {
                    Project.displayProjects(projectList);
                }
                break;
            case "1.3":
                System.out.println("Editing project...");
                while (true) {
                    try {
                        Project.editProject(scanner, projectList);
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid project");
                    }
                }
                break;
            case "1.4":
                System.out.println("Deleting project...");
                Project.deleteProject(scanner, projectList);
                break;
            case "1.5":
                System.out.println("Going back to main menu...");
                adminMenu(scanner, projectList);
                break;
            default:
                System.out.println("Please enter a number between 1.1 and 1.5");

        }
    }

    public static void activitiesSubMenu(Scanner scanner, ArrayList<Project> projectList) throws Exception {
        Project project = null;
        Activity activity = null;
        String input = "";
            System.out.println("\n 2.1: Create activities \n 2.2: Edit activities \n 2.3: Delete activities \n 2.4: Back to main menu");
            input = scanner.nextLine();
            switch (input) {
                case "2.1":
                    System.out.println("Creating activities...");
                    Activity.createActivity(scanner, projectList, false);
                    break;
                case "2.2":
                    System.out.println("Editing activities...");
                    Activity.editActivity(scanner, projectList);
                    break;
                case "2.3":
                    System.out.println("Deleting activities...");
                    Activity.deleteActivity(scanner, projectList);
                    break;
                case "2.4":
                    System.out.println("Going back to main menu...");
                    adminMenu(scanner, projectList);
                    break;
                default:
                    System.out.println("Please enter a number between 2.1 and 2.5");

        }
    }

    public static void employeesSubMenu (Scanner scanner, ArrayList<Project> projectList) throws Exception {
        Employee employee = null;
        System.out.println(" 3.1: Create Employee \n 3.2: Delete Employee \n 3.3: Back to main menu");
        String input = scanner.nextLine();
        switch (input) {
            case "3.1":
                System.out.println("Creating employee...");
                Employee newEmployee = Employee.createEmployee(scanner);
                Employee.addEmployeeToList(newEmployee, employees);
                break;
            case "3.2":
                System.out.println("Deleting employee...");
                Employee.deleteEmployee(scanner,employees, projects);
                break;
            case "3.3":
                System.out.println("Going back to main menu...");
                adminMenu(scanner, projectList);
                break;
            default:
                    System.out.println("Please enter a number between 3.1 and 3.3");
            }
        }

    public static void logout(Scanner scanner) throws Exception {
        System.out.println("User logged out");
        login(scanner);
    }
}