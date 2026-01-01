package tests.junit;

import business.Activity;
import business.Employee;
import business.Project;
import io.cucumber.java.en_old.Ac;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static business.Activity.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

class ActivityTests {

    LocalDate startDate;
    LocalDate endDate;
    Activity activity;
    Employee employee;
    Project project;
    ArrayList<Project> projectList;

    @BeforeEach
    void setUp() {
        activity = new Activity("bowling",0,"name");
        project = new Project("name","leader",1,2,"test");
        employee = new Employee("bob","programmer","available",20);
        Employee.employeesList.add(employee);
        projectList = new ArrayList<>();
        projectList.add(project);
    }

    @Test
    void createActivityTest() {
        Scanner scanner = new Scanner("skiing\nname");
        Activity newActivity = createActivity(scanner,projectList,false);
        assertTrue(project.getActivities().contains(newActivity));

    }

    @Test
    void deleteActivityTest() {
        Scanner scanner = new Scanner("name\nbowling");
        project.addActivity(activity);
        deleteActivity(scanner,projectList);
        assertFalse(project.getActivities().contains(activity));

    }

    @Test
    void findActivityByName() {
        ArrayList<Activity> activityList = new ArrayList<>();
        activityList.add(activity);
        assertEquals(Activity.findActivityByName("bowling",activityList),activity);
    }

    @Test
    void validateTimeOffTest() {
        employee.setTimeOff(3,"Test");
        assert employee.getTimeOff() < 24 : "pre-condition";

        startDate = LocalDate.of(2024,10,28);
        endDate = LocalDate.of(2024,10,31);

        employee.setTimeOff(3,"Test");
        assertTrue(activity.validateTimeOff(employee,startDate,endDate));
        assert activity.validateTimeOff(employee,startDate,endDate) : "post-condition";
    }

    @Test
    void getVacationInfoTest() throws Exception {
        Scanner scanner = new Scanner("2024\n6\n1\n2024\n6\n2\nNote\nNote");
        Activity.getVacationInfo(scanner, employee, false);
        assertFalse(employee.getTimeOffActivities().isEmpty());
    }
}
