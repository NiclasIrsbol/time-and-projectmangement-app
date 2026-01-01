package business;

import java.util.*;
import java.time.*;

/**
 * @author Niclas Søe Irsbøl (s236136)
 */

public class Activity {
    private boolean completionStatus = false;
    private String name;
    private Employee employee;
    private int completionPercent;
    private ArrayList<Employee> assignedEmployees;
    private String assignedProject;
    private boolean editing = false;
    private LocalDate startDate;
    private LocalDate endDate;
    private static Activity vacation;

    public Activity(String name, int completionPercent, String assignedProject) {
        this.name = name;
        this.completionPercent = completionPercent;
        this.assignedProject = assignedProject;
        this.assignedEmployees = new ArrayList<>();
    }

    // Constructor for vacation activity. Takes start and end date and checks if its after the current date.
    // It is not possible to create a vacation activity in the past.
    public Activity(Employee employee, LocalDate startDate, LocalDate endDate, String noteOfLeave) throws Exception {
        if (Employee.findEmployeeByName((employee.getName()), employee.getEmployeeList()) != null) {
            this.employee = employee;
        } else {
            throw new Exception("Employee does not exist.");
        }
        LocalDate currentDate = LocalDate.now();
        this.startDate = validateDate(startDate, currentDate);

        this.endDate = validateDate(endDate, startDate);
        if (!validateTimeOff(employee, startDate, endDate)) {
            throw new Exception("Time off exceeds allowed leave, please select fewer days");
        }
        System.out.println("Time Off successfully registered for: " + employee.getName());
        if (startDate == currentDate) {
            employee.setTimeOff(startDate.compareTo(endDate), noteOfLeave);
            employee.changeAvailability(false);
        }
    }

    public static Activity createActivity(Scanner scanner, ArrayList<Project> projectList, boolean isEditing) {
        System.out.println("Enter name");
        String name = scanner.nextLine();

        if (isEditing) {
            Activity activity = new Activity(name, 0, null);
            return activity;
        }

        System.out.println("Assign the activity to a project");
        String assignedProject = scanner.nextLine();
        Project project = Project.findProjectByName(assignedProject, projectList);

        if (project != null) {
            int completionPercent = 0;
            Activity activity = new Activity(name, completionPercent, assignedProject);

            // Retain the existing list of assigned employees if any
            for (Activity projActivity : project.getActivities()) {
                if (projActivity.getName().equalsIgnoreCase(name)) {
                    for (Employee employee : projActivity.getEmployees()) {
                        activity.assignEmployee(employee);
                    }
                    break;
                }
            }

            project.addActivity(activity);
            System.out.println("Activity " + name + " assigned to project " + assignedProject + " successfully.");
            return activity;
        } else {
            System.out.println("Project not found.");
            return null;
        }
    }

    public static void getVacationInfo(Scanner input, Employee employee, boolean retur) throws Exception {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate;
        while (true) {
            System.out.println("Current date is: " + currentDate + "\n Enter start date...");
            startDate = selectDate(input, currentDate);

            System.out.println("Enter end date.");
            if (startDate == null) {
                endDate = selectDate(input, currentDate);
            } else {
                endDate = selectDate(input, startDate);
            }

            while (startDate == null || endDate == null) {
                System.out.println("Invalid dates chosen. \nEnter start date.");
                startDate = selectDate(input, currentDate);

                System.out.println("Enter end date. \nYear:");
                endDate = selectDate(input, startDate);
            }

            System.out.println("Enter note of leave");
            input.next();
            String noteOfLeave = input.nextLine();

            vacation = new Activity(employee, startDate, endDate, noteOfLeave);
            employee.getTimeOffActivities().add(vacation);
            break;
        }
        if(retur) {
            projectManagementApp.employeeMenu(input, Employee.employeesList);
        }
    }

    public static LocalDate selectDate(Scanner input, LocalDate comparison) {

        for (;;) {
            System.out.println("Year:");
            String year1 = "";
            String month1 = "";
            String day1 = "";

            while (!input.hasNextInt()) {
                System.out.println("Please input integer value!");
                year1 = input.nextLine();
            }
            year1 = input.nextLine();

            int year = Integer.parseInt(year1);

            System.out.println("Month (01-12):");
            while (!input.hasNextInt()) {
                System.out.println("Please input integer value!");
                month1 = input.nextLine();
            }
            month1 = input.nextLine();

            int month = Integer.parseInt(month1);

            System.out.println("Day of month (1-31): ");
            while (!input.hasNextInt()) {
                System.out.println("Please input integer value!");
                day1 = input.nextLine();
            }
            day1 = input.nextLine();

            int dayOfMonth = Integer.parseInt(day1);
            try {
                return validateDate(LocalDate.of(year, month, dayOfMonth), comparison);
            } catch (DateTimeException dateIsInvalid){
                System.out.println("Invalid Date - Try Again");
            }

        }
    }

    public static LocalDate validateDate(LocalDate date, LocalDate compareDate) {
        return date.isAfter(compareDate) ? date : null;
    }

    public void getAssignedEmployees() {
        if (assignedEmployees.isEmpty()) {
            System.out.println("No employees assigned to activity");
            System.out.println("--------------------------------------");
        } else {
            for (Employee employee : assignedEmployees) {
                System.out.println(" Employee name: " + employee.getName());
                System.out.println("  Employee job title: " + employee.getJobTitle());
                System.out.println("  Employee availability: " + employee.getStatus());
                System.out.println("--------------------------------------");
            }
        }
    }

    public static Activity findActivityByName(String activityName, ArrayList<Activity> activityList) {
        if (activityList == null || activityList == null) {
            return null;
        }
        for (Activity activity : activityList) {
            if (activity != null && activity.getName().equalsIgnoreCase(activityName)) {
                return activity;
            }
        }
        return null;
    }

    public static void deleteActivity(Scanner scanner, ArrayList<Project> projects) {
        System.out.println("Enter name of project where an activity should be deleted");
        String projectName = scanner.nextLine();
        Project project = Project.findProjectByName(projectName, projects);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        System.out.println("Enter the name of the activity you want to delete");
        String activityName = scanner.nextLine();

        ArrayList<Activity> activities = project.getActivities();

        for (Activity activity : activities) {
            if (activity.getName().equalsIgnoreCase(activityName)) {
                activities.remove(activity);
                System.out.println("Activity " + activityName + " assigned to project " + projectName + " has been deleted successfully.");
                return;
            }
        }

        System.out.println("Activity not found in the project.");
    }

    // Method used in collaboration with employee class. Assigns an employee to an activity if this employee is not
    // already in the activity.
    public void assignEmployee(Employee employee) {
        if (!this.assignedEmployees.contains(employee)) {
            this.assignedEmployees.add(employee);
            System.out.println("Employee " + employee.getName() + " assigned to activity \'" + this.getName() + "\' successfully.");
        } else {
            System.out.println("Employee already assigned to activity");
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public static void editActivity(Scanner scanner, ArrayList<Project> projects) {
        while (true) {
            System.out.println("Enter name of project where you want to edit an activity");
            String input = scanner.nextLine();
            Project project = Project.findProjectByName(input, projects);

            if (project == null) {
                System.out.println("Project not found.");
                return;
            }

            String projectName = project.getName();
            ArrayList<Activity> activities = project.getActivities();
            if (projects != null && projectName.equalsIgnoreCase(input)) {
                System.out.println("Enter name of activity you want to edit");
                String activityName = scanner.nextLine();
                Activity activity = findActivityByName(activityName, activities);
                if (project.isActivityInProject(input, activityName, projects)) {
                    activities.remove(activity);
                    Activity newActivity = createActivity(scanner, projects, true);

                    System.out.println("Enter new completionPercent");

                    if (scanner.hasNextInt()) {
                        int completionPercent = scanner.nextInt();
                        if (completionPercent >= 0) {
                            scanner.nextLine();
                            newActivity.setCompletionPercent(completionPercent);
                            activities.add(newActivity);
                            System.out.println("Activity name updated successfully to: " + newActivity.getName() + " and completion percent: " + activity.getCompletionPercent());
                            break;
                        } else {
                            System.out.println("Invalid completion percent. Must be greater than 0.");
                        }
                    } else {
                        System.out.println("Invalid activity");
                    }
                } else {
                    System.out.println("Invalid project");
                }
            }
        }
    }

    public boolean validateTimeOff(Employee employee, LocalDate startDate, LocalDate endDate) {
        return startDate.compareTo(endDate) < employee.getTimeOff();
    }

    //Getters and Setters
    public void setCompletionPercent(int completionPercent) {
        this.completionPercent = completionPercent;
    }

    public void setAssignedProject(String newAssignedProject) {
        this.assignedProject = newAssignedProject;
    }
    public ArrayList<Employee> getArrayList() {
        return this.assignedEmployees;
    }

    public String getName() {
        return name;
    }
    public int getCompletionPercent() {
        return completionPercent;
    }

    public String getAssignedProject() {
        return assignedProject;
    }

    public ArrayList<Employee> getEmployees() {
        return this.assignedEmployees;
    }
}