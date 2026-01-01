package tests.cucumber;

import business.Activity;
import business.Project;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import business.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AssignActivitiesSteps {
    private Activity activity;
    private Project project;
    private Employee employee;
    private Employee projectLeader;
    private ArrayList<Project> projectList;
    private String errormessage;




    @Given("A project with name {string} and activity with name {string}, and employee with name {string} exists")
    public void a_project_with_name_and_activity_with_name_and_employee_with_name_exists(String projectName,
                                                                                         String activityName, String employeeName) {
        this.project = new Project(projectName, "", 1, 2, "plan");
        this.activity = new Activity(activityName, 0, "");
        this.employee = new Employee(employeeName, "Software Engineer", "available", 0);
        this.projectList = new ArrayList<>();
        projectList.add(project);
    }

    @Given("Employee has less activities assigned than {int}")
    public void employee_has_less_activities_assigned_than(Integer int1) {
        assertTrue(employee.getCurrentActivities() < int1);
    }

    @When("Project leader with name {string} tries to assign activity with name {string} to project {string} to employee {string}")
    public void project_leader_with_name_tries_to_assign_activity_with_name_to_project_to_employee(String projectLeaderName,
                                                                                                   String activityName,
                                                                                                   String projectName,
                                                                                                   String employeeName) throws Exception {
        try {
            this.projectLeader = new Employee(projectLeaderName, "Software Engineer", "available", 0);
            project.setProjectLeader(projectLeader.getName());
            activity.setAssignedProject(projectName);
            project.addActivity(activity);
            projectLeader.projectLeaderAssignsEmployeeToActivity(project.getProjectLeader(),
                    true, activity.getName(), projectList, project, employee);
        } catch (Exception e) {
            this.errormessage = e.getMessage();
            System.out.println(errormessage);
        }
    }

    @Then("Activity with name {string} is assigned to employee {string} in project {string}")
    public void activity_with_name_is_assigned_to_employee_in_project(String string, String string2, String string3) {
        assertTrue(activity.getEmployees().contains(employee));
    }

    @Given("Employee has more activities assigned than {int}")
    public void employee_has_more_activities_assigned_than(Integer int1) {
        employee.setCurrentActivities(int1 + 1);
        assertTrue(employee.getCurrentActivities() > int1);
    }

    @Then("System gives errormessage {string}")
    public void system_gives_errormessage_employee_has_to_many_activities(String errormessage) {
        assertEquals(errormessage, this.errormessage);
    }
}
