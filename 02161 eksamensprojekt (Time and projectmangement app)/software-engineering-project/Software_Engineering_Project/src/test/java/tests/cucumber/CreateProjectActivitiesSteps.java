package tests.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import business.*;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class CreateProjectActivitiesSteps {
    private Project project;
    private Activity activity;
    private ArrayList<Project> projectList;
    private String projectLeader;


    @Given("A project with the name {string} exists")
    public void a_project_with_the_name_exists(String projectName) {
        this.project = new Project(projectName, "", 10, 15, "plan");
        this.projectList = new ArrayList<>();
        projectList.add(project);
        assertTrue(Project.projectExists(project,projectList));
    }

    @When("{string} adds an activity {string} to {string}")
    public void adds_an_activity_to(String projectLeaderName, String activityName, String projectName) {
        this.projectLeader = projectLeaderName;
        this.activity = new Activity(activityName,0, projectName);
        project.addActivity(activity);
    }

    @Then("The activity {string} is added to {string}")
    public void the_activity_is_added_to(String activityName, String projectName) {
        assertTrue(project.getActivities().contains(activity));
    }

    @Then("An errormessage displays {string}")
    public void an_errormessage_displays(String string) {
        try {
            if (activity.getAssignedProject().equalsIgnoreCase(project.getName())){
                System.out.println("Activity successfully added to project");
            } else {
                throw new Exception("Project does not exist");
            }
        } catch (Exception e) {
            assertEquals(string, e.getMessage());
        }
    }
}
