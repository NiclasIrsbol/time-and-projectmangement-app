package business;

import java.util.ArrayList;
import java.util.Scanner;

public class Project {

    /**
     * @author Ajani-Lamar Levi Eichel-Cooke (s223946)
     */

    private String name;
    private String projectLeader;
    private boolean completionStatus = false;
    private ArrayList<Activity> activities;
    private int budgetTime;
    private int startWeek;
    private int endWeek;
    private ArrayList<Employee> assignedEmployees;
    private String projectPlan;

    public Project(String name, String projectLeader, int startWeek, int endWeek, String projectPlan) {
        this.name = name;
        this.projectLeader = projectLeader;
        setStartWeek(startWeek);
        setEndWeek(endWeek);
        setBudgetTime(startWeek, endWeek);
        this.projectPlan = projectPlan;
        this.activities = new ArrayList<>();
    }

    // Methods
    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public static Project createProject(Scanner scanner, ArrayList<Project> projectList) {
        System.out.println("Enter name for project");
        String name = scanner.nextLine();

        Project existingProject = findProjectByName(name, projectList);
        if (existingProject != null) {
            System.out.println("A project with the name '" + name + "' already exists.");
            System.out.println("Enter name for project");
            name = scanner.nextLine();
        }

        System.out.println("Enter name for project leader");
        String projectLeaderName = scanner.nextLine();
        int endWeek = 0;
        int startWeek = 0;

        while (true) {
            System.out.println("Enter start week:");
            if (scanner.hasNextInt()) {
                startWeek = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter an integer for the start week.");
                scanner.next();
            }
        }

        while (true) {
            System.out.println("Enter end week:");
            if (scanner.hasNextInt()) {
                endWeek = scanner.nextInt();
                if (endWeek > startWeek) {
                    scanner.nextLine();
                    break;
                } else {
                    System.out.println("Invalid end week. End week must be greater than start week.");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer for the end week.");
                scanner.next();
            }
        }
        System.out.println("Enter projectPlan (Short description of the project)");
        String projectPlan = scanner.nextLine();

        System.out.println("Project created with: \n" + "  Name: " + name + "\n  Project leader: " + projectLeaderName +
                "\n  Start Week: " + startWeek + "\n  Budget Time: " + (endWeek - startWeek) + "\n  Project plan : " + projectPlan);
        return new Project(name, projectLeaderName, startWeek, endWeek, projectPlan);
    }


    //Denne metode vælger vi ikke at teste med JUnit, da det ikke rigtig ville give mening
    public static void displayProjects(ArrayList<Project> projectList) {
        int projectNumber = 1;
        for (Project project : projectList) {
            System.out.println("Project: " + projectNumber);
            System.out.println("  Project Name: " + project.getName());
            System.out.println("  Project Leader: " + project.getProjectLeader());
            System.out.println("  Budget Time: " + project.getBudgetTime());
            System.out.println("  Start Week: " + project.getStartWeek());
            System.out.println("  Project Plan:" + project.getProjectPlan());
            System.out.println();
            project.displayActivities();
            projectNumber++;
        }
    }

    //Denne metode vælger vi ikke at teste med JUnit, da det ikke rigtig ville give mening
    public void displayActivities() {
        if (activities.isEmpty()) {
            System.out.println("No activities assigned to this project.");
        } else {
            System.out.println("Activities assigned to project " + name + ":");
            for (Activity activity : activities) {
                System.out.println("  Activity: " + activity.getName());
                System.out.println("    Completion Percent: " + activity.getCompletionPercent());
                System.out.println("--------------------------------------");
                activity.getAssignedEmployees();
            }
        }
    }

    //Denne metode vælger vi ikke at teste med JUnit, da det ikke rigtig ville give mening
    public void displayAssignedEmployees() {
        if (assignedEmployees.isEmpty()) {
            System.out.println("No employees assigned to activity");
        } else {
            for (int i = 0; i < assignedEmployees.size(); i++) {
                System.out.println(assignedEmployees.get(i));
            }
        }
    }

    public static Project findProjectByName(String projectName, ArrayList<Project> projectList) {
        try {
            assert projectName != null && projectList != null : "pre-condition";
        } catch (AssertionError e){
            return null;
        }

        for (Project project : projectList) {
            if (project != null && project.getName().equalsIgnoreCase(projectName)) {
                assert project.getName().equals(projectName) : "post-condition";
                return project;
            }
        }
        return null;
    }

    public static void deleteProject(Scanner scanner, ArrayList<Project> projects) {
        try {
            System.out.println("Enter name of project you want to delete");
            String input = scanner.nextLine();
            input.toLowerCase();
            Project projectName = findProjectByName(input, projects);
            String projectNameString = projectName.getName();
            projectNameString.toLowerCase();
            if (projectNameString.equalsIgnoreCase(input)) {
                projects.remove(projectName);
                System.out.println("Project with name " + projectName + " has been deleted");
            }
        } catch (Exception e) {
            System.out.println("Invalid project");
        }
    }

    public boolean validateStartWeek(int startWeek) {
        if (startWeek <= 52 && startWeek > 0){
            return true;
        }
        return startWeek != 0;
    }

    public boolean validateEndWeek(int endWeek) {
        if (endWeek <= 52 && endWeek > 0) {
            return true;
        }
        return endWeek != 0;
    }

    public static boolean isActivityInProject(String projectName,
                                              String activityName,
                                              ArrayList<Project> projectList) {
        Project project = findProjectByName(projectName, projectList);

        try {
            assert project != null : "pre-condition";
        } catch (AssertionError e){
            System.out.println("Project with name '" + projectName + "' does not exist.");
            return false;
        }

        for (Activity activity : project.getActivities()) {
            try {
                assert activity.getName().equalsIgnoreCase(activityName) : "post-condtion";
                return true;
            } catch (AssertionError ignored){}
        }
        return false;
    }

    public static void editProject(Scanner scanner, ArrayList<Project> projects) {
        while (true) {
            System.out.println("Enter name of project you want to edit");
            String input = scanner.nextLine();
            Project project = findProjectByName(input, projects);
            String projectName = project.getName();
            if (projects != null && projectName.equalsIgnoreCase(input)) {
                projects.remove(project);

                ArrayList<Activity> assignedActivities = project.getActivities();

                Project newProject = Project.createProject(scanner, projects);

                for (Activity activity : assignedActivities) {
                    newProject.addActivity(activity);
                }

                projects.add(newProject);
                break;
            } else {
                System.out.println("Invalid Activity");
            }
        }
    }

    public String toString() {
        return name.toLowerCase();
    }

    public static boolean projectExists(Project project, ArrayList<Project> projects) {
        return projects.contains(project);
    }

    public static String delete(Project project, ArrayList<Project> projects, int currentWeek) {
        if (projectExists(project, projects) && currentWeek > project.endWeek || currentWeek < project.startWeek) {
            projects.remove(project);
            return "Project is successfully deleted";
        }
        return "Cannot delete active project";
    }

    public static boolean projectExists(Project project) {
        return project != null;
    }

    //Getters and setters
    public int getEndWeek() {
        return endWeek;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setProjectLeader(String newProjectLeader) {
        this.projectLeader = newProjectLeader;
    }

    public void setStartWeek(int newStartWeek) {
        this.startWeek = validateStartWeek(newStartWeek) ? newStartWeek : 0;
    }

    public void setEndWeek(int newEndWeek) {
        this.endWeek = validateEndWeek(newEndWeek) ? newEndWeek : 0;
    }

    public void setBudgetTime(int startWeek, int endWeek) {
        this.budgetTime = Math.max(endWeek - startWeek, 0);
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public String getProjectPlan() {
        return projectPlan;
    }

    public int getBudgetTime() {
        return budgetTime;
    }

    public int getStartWeek() {
        return startWeek;
    }
}