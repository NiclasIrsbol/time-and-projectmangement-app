package tests.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import business.*;

public class DeleteProjectSteps {
    private Project project;
    private ArrayList<Project> projects;

    private int currentWeek;


    //Initiates fields to a project and arraylist containing project. Calls method 'contains' from arraylist class.
    // Then asserts that the start week of the project is the same as the given start week.
    @Given("the user has a project titled {string} and the project is scheduled to start in week {int} and end in week {int}")
    public void the_user_has_a_project_titled(String string, int start, int end) {
        this.project = new Project(string, "bob", start, end, "WE need projectPlan");
        this.projects = new ArrayList<>();
        projects.add(project);
        assertTrue(projects.contains(project));
        assertSame(project.getStartWeek(), start);
    }

    //Sets current week to given integer value.
    @Given("the current week is {int}")
    public void the_current_week_is(Integer int1) {
        this.currentWeek = int1;
    }

    // The user deletes a project if the name of project is the same as the given name.
    @When("The user attempts to delete {string}")
    public void the_user_attempts_to_delete(String string) {
        assertEquals(string.toLowerCase(), project.toString());
        if (string.equals(project.getName()))
            projects.remove(project);
    }

    // Calls 'projectExists' from Project class. Checks if project is in a given list.
    @Then("The project is deleted")
    public void the_project_is_deleted() {
        assertFalse(Project.projectExists(project, projects));
    }

    // Checks if the displayed message is the correct.
    @Then("Display message saying {string}")
    public void display_errormessage_saying(String string) {
        assertEquals(Project.delete(project, projects, currentWeek), string);
    }

    // Checks if project exists.
    @Then("The project is not deleted")
    public void the_project_is_not_deleted() {
        assertTrue(Project.projectExists(project, projects));
    }
}
