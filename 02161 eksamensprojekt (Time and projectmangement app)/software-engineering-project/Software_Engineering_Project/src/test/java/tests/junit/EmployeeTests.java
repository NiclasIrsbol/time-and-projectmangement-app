package tests.junit;

import business.Activity;
import business.Employee;
import business.Project;
import io.cucumber.java.en_old.Ac;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import static business.Employee.assignEmployeeToActivityInProject;
import static business.Employee.userInputRegisterWorkTime;
import static org.junit.jupiter.api.Assertions.*;
class EmployeeTests {

    Employee employee;
    Activity activity;
    ArrayList<Employee> employeesList;
    @BeforeEach
    void setUp(){
        employee = new Employee("bob","programmer","available",20);
        employeesList = new ArrayList<>();
    }

    @Test
    void testCreateEmployee() {
        Scanner scanner = new Scanner("emp\nprogrammer\navailable");
        Employee newEmployee = Employee.createEmployee(scanner);
        assertEquals(newEmployee.getName(),"emp");
        assertEquals(newEmployee.getJobTitle(),"programmer");
        assertEquals(newEmployee.getStatus(),"available");

    }

    //Ved ikke hvorfor denne her ikke virker
    @Test
    void addEmployeeToListTest() {
       Employee.addEmployeeToList(employee,employeesList);

        assertTrue(employeesList.contains(employee));
    }

    @Test
    void findEmployeeByNameTest() {
        employeesList.add(employee);
        Employee foundEmployee = Employee.findEmployeeByName("bob",employeesList);
        assertEquals(foundEmployee,employee);
    }

    @Test
    void deleteEmployeeTest() {
        Scanner scanner = new Scanner("bob");
        Project project = new Project("name","leader",1,2,"projectnew");
        ArrayList<Project> projectList = new ArrayList<>();
        projectList.add(project);
        Activity activity = new Activity("bowling",0,"name");
        project.addActivity(activity);
        activity.assignEmployee(employee);
        employeesList.add(employee);
        Employee.deleteEmployee(scanner,employeesList,projectList);
        assertFalse(employeesList.contains(employee));
        assertFalse(activity.getEmployees().contains(employee));

    }

    @Test
    void registerWorkTimeTest() {
        employee.registerWorkTime(10);
        assertEquals(employee.getWorkTime(),30);
    }

    @Test
    void userInputRegisterWorkTimeTest() {
        Scanner scanner = new Scanner("bob\n20");
        employeesList.add(employee);
        userInputRegisterWorkTime(scanner,employeesList);
        assertEquals(employee.getWorkTime(),40);
    }

    @Test
    void assignEmployeeToActivityInProjectTest() throws Exception {
        Project project = new Project("name","leader",1,2,"test");
        Activity activity = new Activity("bowling",0,"name");
        ArrayList<Project> projectList = new ArrayList<>();
        employeesList.add(employee);
        projectList.add(project);
        project.addActivity(activity);
        Scanner scanner = new Scanner("name\nbowling\nbob");

        assignEmployeeToActivityInProject(scanner,projectList,employeesList);
        assertTrue(activity.getEmployees().contains(employee));
    }

    @Test
    void projectLeaderAssignsEmployeeToActivityTest() throws Exception {
        ArrayList<Project> projectList = new ArrayList<>();
        Project project = new Project("name","leader",1,2,"test");
        Activity activity = new Activity("bowling",0,"name");
        project.addActivity(activity);
        projectList.add(project);

        employee.projectLeaderAssignsEmployeeToActivity("QWER",true,"bowling",
                                                                        projectList,project,employee);
        assertTrue(activity.getEmployees().contains(employee));
    }

    @Test
    void existsTest(){
        employeesList.add(employee);
        assertTrue(Employee.exists(employee,employeesList));
    }

    @Test
    void validateWorkTimeTest() {
        assertTrue(Employee.validateWorkTime(12,13,53,57));
    }

    @Test
    void addCurrentActivitiesTest() throws Exception {
        activity = new Activity("Activity", 0, "");
        Employee.addCurrentActivities(1, employee, activity);
        assertTrue(activity.getArrayList().contains(employee));
    }


}
