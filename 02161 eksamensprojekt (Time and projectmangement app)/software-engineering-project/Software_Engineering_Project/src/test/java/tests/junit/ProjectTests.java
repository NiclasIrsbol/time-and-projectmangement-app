package tests.junit;

import business.Activity;
import business.Employee;
import business.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTests {
    Activity activity;
    Project project;
    @BeforeEach
    void setup() {
        project = new Project("name","leader",1,2,"plan");

    }

    @Test
    void testCreateProject() {
        System.out.println("Testing Create Project...");
        Scanner scanner = new Scanner("name\nleader\n1\n2\nplan");
        ArrayList<Project> projectList = new ArrayList<Project>();

        Project newProject = Project.createProject(scanner, projectList);

        assertEquals(newProject.getName(),"name");
        assertEquals(newProject.getProjectLeader(),"leader");
        assertEquals(newProject.getStartWeek(),1);
        assertEquals(newProject.getEndWeek(),2);
        assertEquals(newProject.getProjectPlan(),"plan");

    }

    @Test
    void testAddActivity() {
        System.out.println("Testing Add Activity...");

        activity = new Activity("bowling",0,"name");
        project.addActivity(activity);
        Activity activity_test = project.getActivities().get(0);
        assertEquals(activity_test.getName(),"bowling");
        assertEquals(activity_test.getCompletionPercent(),0);
        assertEquals(activity_test.getAssignedProject(),"name");

    }

    @Test
    void findProjectByNameTest() {
        System.out.println("Testing findProjectByName()");

        ArrayList<Project> projectList = new ArrayList<Project>();
        projectList.add(project);

        Project foundProject = Project.findProjectByName("name",projectList);

        assertEquals(foundProject.getName(),"name");
        assertEquals(foundProject.getProjectLeader(),"leader");
        assertEquals(foundProject.getStartWeek(),1);
        assertEquals(foundProject.getEndWeek(),2);
        assertEquals(foundProject.getProjectPlan(),"plan");
    }

    @Test
    void findProjectByNameWithErrorTest() {
        System.out.println("Testing findProjectByName() with null error");
        ArrayList<Project> projectList = new ArrayList<Project>();
        Project foundProject = Project.findProjectByName("name",projectList);
        assertNull(foundProject);
    }

    @Test
    void deleteProjectTest() {
        System.out.println("Testing deleteProject()");

        ArrayList<Project> projectList = new ArrayList<>();
        Scanner scanner = new Scanner("name");
        projectList.add(project);
        Project.deleteProject(scanner,projectList);
        Project foundProject = Project.findProjectByName("name",projectList);
        assertNull(foundProject);

    }

    @Test
    void isActivityInProjectTest() {
        System.out.println("Testing isActivityInProject()");

        Activity activity = new Activity("partying",0,"project");
        project.addActivity(activity);
        ArrayList<Project> projectList = new ArrayList<>();
        projectList.add(project);

        boolean result = Project.isActivityInProject("name","partying",projectList);
        assertTrue(result);
    }

    @Test
    void isActivityInProjectWithErrorTest() {
        System.out.println("Testing isActivityInProject() with error");

        ArrayList<Project> projectList = new ArrayList<>();
        projectList.add(project);

        boolean result = Project.isActivityInProject("name","partying",projectList);
        assertFalse(result);
    }


    @Test
    void editProjectTest() {
        System.out.println("Testing editProject()");

        ArrayList<Project> projectList = new ArrayList<>();
        projectList.add(project);

        Scanner scanner = new Scanner("name\nname\nnewLeader\n2\n3\nedited project");
        Project.editProject(scanner,projectList);
        Project editedProject = projectList.get(0);
        assertEquals(editedProject.getProjectLeader(),"newLeader");
        assertEquals(editedProject.getStartWeek(),2);
        assertEquals(editedProject.getEndWeek(),3);
    }

    @Test
    void projectExistsTest() {
        System.out.println("Testing projectExists()");

        ArrayList<Project> projectList = new ArrayList<>();
        projectList.add(project);
        assertTrue(Project.projectExists(project,projectList));

    }

    @Test
    void setProjectLeaderTest() {
        System.out.println("Testing setProjectLeader()");

        project.setProjectLeader("tony");
        assertEquals(project.getProjectLeader(),"tony");

    }








}
