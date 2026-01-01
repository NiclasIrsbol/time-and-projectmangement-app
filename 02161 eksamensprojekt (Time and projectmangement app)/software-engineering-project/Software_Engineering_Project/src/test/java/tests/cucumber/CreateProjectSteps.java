package tests.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Scanner;
import business.*;

import static org.junit.jupiter.api.Assertions.*;

public class CreateProjectSteps {
    private Employee employee;
    private Employee employee2;
    private projectManagementApp projectApp;
    private ArrayList<Employee> employeeList;
    private int currentWeek;
    private Project project;
    private Activity activity;
    private ArrayList<Project> projects;

    @Given("a valid user {string}")
    public void a_valid_user(String employeeName) {
        this.employee = new Employee(employeeName, "Project Leader", "available", 0);
        this.employeeList = new ArrayList<>();
        Employee.addEmployeeToList(employee, employeeList);
        assertTrue(Employee.exists(employee, employeeList));
        assertEquals("Project Leader", employee.getJobTitle());
    }

    @Given("a current week {int}")
    public void a_current_week(int currentWeek) {
        this.currentWeek = currentWeek;
    }

    @Given("another user {string} with activity count at {int}")
    public void another_user_with_activity_count_at(String user2, int activityCount) throws Exception {
        this.employee2 = new Employee(user2, "Software Engineer", "available", 0);
        employee2.setCurrentActivities(activityCount);
        assertEquals(activityCount, employee2.getCurrentActivities());
    }

    @When("the user creates an empty project with due-date in week {int}")
    public void the_user_creates_an_empty_project_with_due_date_in_week(int endWeek) {
        this.project = new Project("Project", "Name", 0, endWeek, "ProjectPlan");
        this.projects = new ArrayList<>();
        projects.add(project);
        assertEquals(endWeek, project.getEndWeek());
    }

    @When("{string} adds {string} to a project")
    public void adds_to_a_project(String name, String user2) throws Exception {
        this.activity = new Activity("Activity", 0, project.getName());
        project.addActivity(activity);
        try {
            employee.projectLeaderAssignsEmployeeToActivity(name, true, activity.getName(), projects, project, employee2);
            Employee.addEmployeeToList(employee2, employeeList);
        } catch (Exception E) {
            System.out.println(E.getMessage());
        }
    }

    @Then("an empty project is initialized")
    public void an_empty_project_is_initialized() {
        assertTrue(Project.projectExists(project, projects));
    }

    @Then("an error {string} will be thrown")
    public void an_error_will_be_thrown(String error) {
        try {
            if (project.getEndWeek() < currentWeek || !project.validateEndWeek(project.getEndWeek()) ||
                    !project.validateStartWeek(project.getStartWeek())) {
                throw new Exception("invalid due-date");
            } else {
                projects.add(project);
                System.out.println("Project successfully added!");
            }
        } catch (Exception E) {
            assertEquals("invalid due-date", E.getMessage());
        }
    }

    @Then("{string} will be added to the project, and the project count will increase")
    public void will_be_added_to_the_project_and_count_will_increase(String user2) throws Exception {
        assertTrue(employeeList.contains(employee2));
    }

    @Then("{string} will not be added to the project, and an error will be thrown")
    public void will_not_be_added_to_the_project_and_an_error_will_be_thrown(String user2) throws Exception {
        assertFalse(employeeList.contains(employee2));
    }
}
