package tests.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import business.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeDeadlineSteps {
    private Project project;
    private ArrayList<Project> projectList;
    private String errormessage;


    @Given("{string} exists with start in week {int} and deadline in week {int}")
    public void exists(String string, int start, int deadline) {
        this.project = new Project(string, "", start, deadline, "plan");
        this.projectList = new ArrayList<>();
        projectList.add(project);
        assertTrue(Project.projectExists(project, projectList));
        assertEquals(start, project.getStartWeek());
        assertEquals(deadline, project.getEndWeek());
    }

    @Then("the deadline for {string} is week {int}")
    public void the_deadline_for_is(String string, int deadline) {
        assertEquals(deadline, project.getEndWeek());
    }

    @When("the Project Leader sets the new deadline to week {int}")
    public void pjs(int newDeadline) {
        try {
            project.setEndWeek(newDeadline);
        } catch (NullPointerException e) {
            this.errormessage = "Project does not exist";
        }
    }

    @Given("{string} is empty")
    public void is_empty(String string) {
        this.project = null;
    }

    @Then("the error-message {string} is given")
    public void the_error_message_is_given(String string) {
        assertEquals(string, errormessage);
    }
}
