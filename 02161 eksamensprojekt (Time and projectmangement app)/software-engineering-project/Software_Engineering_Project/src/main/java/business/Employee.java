package business;

import java.util.*;

import static business.Project.projectExists;

public class Employee {

    /**
     * @author Rasmus Seh Aasberg-Petersen (s211424)
     */

    private final String name;
    private boolean availability;
    private int currentActivities = 0;
    private String jobTitle;
    private final String status;
    private int maxTimeOff = 24;
    private int currentTimeOff = 0;
    private int workTime;
    private final int maxActivityCount = 10;
    public static ArrayList<Employee> employeesList = new ArrayList<>();
    private static ArrayList<String> projectLeaders;
    private String note;
    private ArrayList<Activity> timeOff = new ArrayList<>();

    public Employee(String name, String jobTitle, String status, int workTime) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.status = status;
        this.availability = status.equalsIgnoreCase("available");
        this.workTime = workTime;
    }

    //Takes a scanner from project management app and creates an employee from user inputs. Returns
    // newly created employee for app to use.
    public static Employee createEmployee(Scanner scanner) {
        String name;
        do {
            System.out.println("Enter initials for employee (1 to 4 letters)");
            name = scanner.nextLine();
        } while (name.length()>4);

        System.out.println("Enter job title for employee");
        String jobTitle = scanner.nextLine();

        if (jobTitle.equalsIgnoreCase("Project Leader")) {
            setProjectLeaders(projectLeaders);
            projectLeaders.add(name);
        }

        String status;
        do {
            System.out.println("Enter status for employee: available/unavailable");
            status = scanner.nextLine();
        } while (!status.equalsIgnoreCase("available") && !status.equalsIgnoreCase("unavailable"));
        System.out.println("Employee created: \n" + "  Name: " + name + "\n  Job Title for Employee: " + jobTitle + "\n  Status: " + status + "\n");
        return new Employee(name, jobTitle, status,0);
    }

    // Adds already existing employee to list of employees
    public static void addEmployeeToList(Employee employee, ArrayList<Employee> employeesList) {
        if (employeesList == null) {
            employeesList = new ArrayList<>();
        } else if (employee.getStatus().equalsIgnoreCase("Available")) {
            employeesList.add(employee);
        }
    }


    // Looks up employee by name and searches the list of employees for this name.
    public static Employee findEmployeeByName(String employeeName, ArrayList<Employee> employeesList) {
        for (Employee employee : employeesList) {
            if (employee.getName().equalsIgnoreCase(employeeName)) {
                return employee;
            }
        }
        return null; // returns null if employee is not found.
    }

    // Deletes an employee. Takes a scanner and arraylist of employees as inputs.
    // The user needs to input name of the employee, and if the employee exists in the list, then it is deleted.
    public static void deleteEmployee(Scanner scanner, ArrayList<Employee> employeeList, ArrayList<Project> projectsList) {
        try {
            System.out.println("Enter name of employee you want to delete");
            String input = scanner.nextLine();
            input.toLowerCase();
            Employee employeeName = findEmployeeByName(input, employeeList);
            String employeeNameString = employeeName.getName();
            employeeNameString.toLowerCase();

            if (employeeNameString.equalsIgnoreCase(input)) {
                employeeList.remove(employeeName);

                for (Project project : projectsList) {
                    for (Activity activity : project.getActivities()) {

                        if (activity.getEmployees().contains(employeeName)) {
                            activity.getEmployees().remove(employeeName);
                            System.out.println("Employee " + employeeName.getName() + " removed from activity " + activity.getName());
                        }
                    }
                }
                System.out.println("Employee with name " + employeeNameString + " has been deleted \n");
            }
        } catch (Exception e) {
            System.out.println("Invalid employee");
        }
    }

    public static void userInputRegisterWorkTime(Scanner scanner, ArrayList<Employee> employees) {
        System.out.println("Enter name for employee");
        String name = scanner.nextLine();
        Employee employee = Employee.findEmployeeByName(name, employees);
        if (employee != null) {
            while (true) {
                System.out.println("Enter the amount of work time");
                if (scanner.hasNextInt() || scanner.next().isEmpty()) {
                    int workTime = Integer.parseInt(scanner.nextLine());
                    employee.registerWorkTime(workTime);
                    break;
                } else {
                    System.out.println("Invalid input. Please enter an integer for the start week.");
                    scanner.next();
                }
            }
        } else {
            System.out.println("Invalid employee");
        }
    }


    public static void assignEmployeeToActivityInProject(Scanner scanner, ArrayList<Project> projectList,
                                                         ArrayList<Employee> employees) throws Exception{
        System.out.println("Enter project name:");
        String projectName = scanner.nextLine();
        Project project = Project.findProjectByName(projectName, projectList);
        if (!projectExists(project)) {
            System.out.println("Project not found...");
            return;
        }

        System.out.println("\nActivitesList: ");
        for (Activity activity : project.getActivities()) {
            System.out.println(" " + activity.getName());
        }

        System.out.println("Enter activity name:");
        String activityName = scanner.nextLine();
        Activity activity = activityExistsInProject(activityName, project, null);

        if (activity == null)  {
            System.out.println("Activity not found...");
            return;
        }

        System.out.println("\nAvailable employees");
        for (Employee employee : employees) {
            System.out.println("" + employee.getName());
        }

        System.out.println("Enter employee name:");
        String employeeName = scanner.nextLine();
        Employee employee = Employee.findEmployeeByName(employeeName, employees);

        if (employee == null) { // Check if employee exists.
            System.out.println("Employee not found.");
            return;
        }
        addCurrentActivities(1, employee, activity);
    }

    public void projectLeaderAssignsEmployeeToActivity(String name, boolean login, String activityName, ArrayList<Project> projectList,
                                                       Project project, Employee employee) throws Exception {

        if (login) {
            Project project1;
            boolean available = employee.getAvailability();
            if (!available) {
                throw new Exception("Employee is not available to work on this project");
            }
            if (project != null) {
                project1 = Project.findProjectByName(project.getName(), projectList);
            } else {
                throw new Exception("Project does not exist...");
            }
            if (name.equalsIgnoreCase("QWER") && project1 != null && employee != null) {
                Activity activity = activityExistsInProject(activityName, project, null);
                addCurrentActivities(1, employee, activity);
            } else {
                throw new Exception("Project or employee does not exist...");
            }
        } else {
            throw new Exception("Project Leader not logged in...");
        }
    }

    // Check if employee exists by traversing employee list.
    public static boolean exists(Employee check, ArrayList<Employee> employeesList) {
        for (Employee employee : employeesList) {
            if (employee.getName().equalsIgnoreCase(check.getName())) {
                return true;
            }
        }
        return false;
    }

    //Den her skal rykkes til activity eller project
    public static Activity activityExistsInProject(String activityName, Project project, Activity activity) {
        for (Activity projActivity : project.getActivities()) {
            if (projActivity.getName().equalsIgnoreCase(activityName)) {
                activity = projActivity;
                return activity;
            }
        }
        return activity;
    }

    //Checks if the time is within a 24-hour cycle and minutes within 0 - 60.
    public static boolean validateWorkTime(int startHour, int endHour, int startMin, int endMin) {
        return startHour <= 24 && startHour >= 0 && endHour <= 24 && endHour >=0 && startMin <=59 && startMin >= 0
                && endMin <= 59 && endMin >= 0;
    }

    public void registerWorkTime(int n) {
        workTime += n;
        System.out.println("Work time registered for " + name + ": " + workTime);
    }

    public ArrayList<Employee> getEmployeeList() {
        return employeesList;
    }

    public void changeAvailability(boolean value) {
        availability = value;
    }

    public String getName() {
        return name;
    }

    public int getCurrentActivities() {
        return currentActivities;
    }

    public static void addCurrentActivities(int n, Employee employee, Activity activity) throws Exception {
        if (employee != null || activity != null) {
            if (employee.currentActivities + 1 <= employee.maxActivityCount) {
                activity.assignEmployee(employee);
                employee.currentActivities += n;
            } else {
                throw new Exception("Employee has exceeded activity limit.");
            }
        } else {
            throw new Exception("Employee or activity does not exist!");
        }
    }

    public void setTimeOff(int days, String noteOfLeave) {
        currentTimeOff += days;
        this.note = noteOfLeave;
    }

    public void setCurrentActivities(int n) {
        this.currentActivities += n;
    }

    public boolean getAvailability() {
        return availability;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getStatus() {
        return status;
    }

    public int getTimeOff() {
        return maxTimeOff - currentTimeOff;
    }

    public int getWorkTime() {
        return workTime;
    }

    public static void setProjectLeaders(ArrayList<String> projectLeaders) {
        Employee.projectLeaders = projectLeaders;
    }

    public ArrayList<Activity> getTimeOffActivities() {
        return timeOff;
    }

}
