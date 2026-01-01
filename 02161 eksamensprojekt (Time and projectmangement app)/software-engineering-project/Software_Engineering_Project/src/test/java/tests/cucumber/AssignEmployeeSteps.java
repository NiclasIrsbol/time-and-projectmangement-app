package tests.cucumber;

import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import business.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AssignEmployeeSteps {
    private Employee employee;
    private Activity activity;
    private Project project;
    private ArrayList<Project> projectList;

    private String errorMessage;



    @Given("the employee with initials {string} is free")
    public void the_employee_with_initials_is_free(String name) {
        this.employee = new Employee(name, "Software Engineer", "available", 0);
        assertTrue(employee.getAvailability());
    }

    @Given("{string} is an existing activity in a project")
    public void is_an_existing_project(String string) {
        this.project = new Project("project", "", 9, 19, "plan");
        this.projectList = new ArrayList<>();
        projectList.add(project);
        this.activity = new Activity(string, 0, "project");
        project.addActivity(activity);
        assertTrue(project.getActivities().contains(activity));
    }
    @When("selects employee {string}")
    public void selects_employee(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("the Project Leader selects {string} and {string}")
    public void the_project_leader_selects_and(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the Project Leader assigns {string} to {string}")
    public void the_Project_Leader_assigns_to(String employeeName, String activityName) {
        activity.assignEmployee(employee);
        assertTrue(activity.getEmployees().contains(employee));
    }

    @Given("{string} does not exist")
    public void AE_does_not_exist(String projectName) {
        this.project = Project.findProjectByName(projectName, projectList);
        assertNull(project);
    }

    @Then("the message {string} returns")
    public void the_message_returns(String string) {
        assertEquals(string, errorMessage);
    }

    @When("the Project Leader assigns {string} to project {string}")
    public void the_Project_Leader_assigns_to_project(String employeeName, String projectName) {
        try {
            employee.projectLeaderAssignsEmployeeToActivity("QWER", true, projectName, projectList, project, employee);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @Given("{string} does exist")
    public void does_exist(String string) {
        this.project = new Project(string, "", 0, 1, "plan");
        this.projectList = new ArrayList<>();
        projectList.add(project);
        assertTrue(projectList.contains(project));
    }

    @Given("employee {string} is not free")
    public void employee_is_not_free(String string) {
        this.employee = new Employee(string, "Software Engineer", "Unavailable", 0);
        assertFalse(employee.getAvailability());
    }
}
