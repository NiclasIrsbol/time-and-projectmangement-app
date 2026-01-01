package tests.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import business.*;

public class RegisterWorkTimeSteps {
    private Employee employee;
    private String name;
    private Project project;
    private int startHour;
    private int endHour;
    private int startMin;
    private int endMin;
    private double workTime;

    private ArrayList<Employee> employees;

    //Creates new employee and adds to list of employees. Then calls method 'exists' from the employee class.
    //The method checks if the employee exists within the given list - returns true or false.
    @Given("that the employee exists")
    public void that_the_employee_exists() throws Exception {
        employee = new Employee("Bob", "Programmer", "Available", 10);
        this.employees = new ArrayList<Employee>();
        Employee.addEmployeeToList(employee, employees);
        assertTrue(Employee.exists(employee, employees));
    }

    //Sets fields 'startHour' and 'startMin' to the given values.
    @When("the employee selects a start time {int} {int}")
    public void the_employee_selects_a_start_time(int hour, int minute) {
        this.startHour = hour;
        this.startMin = minute;
    }

    //^^
    @When("the employee selects an end time {int} {int}")
    public void the_employee_selects_an_end_time(int hour, int minute) {
        this.endHour = hour;
        this.endMin = minute;
    }

    // Calls 'validateWorkTime' from Employee class. The method checks if the chosen times are within the boundaries of
    //minutes and hours in a day.
    @Then("the working time is confirmed successfully")
    public void the_working_time_is_confirmed_successfully() {
        assertTrue(Employee.validateWorkTime(startHour, endHour, startMin, endMin));
        this.workTime = (endHour - startHour) + ((double) (endMin - startMin) / 10);
        employee.registerWorkTime(2);
    }

    @When("the employee selects an invalid end time {int} {int}")
    public void the_employee_selects_an_invalid_end_time(int hour, int minute) {
        this.endHour = hour;
        this.endMin = minute;
    }

    //^^
    @Then("the working time is not confirmed")
    public void the_working_time_is_not_confirmed() {
        assertFalse(Employee.validateWorkTime(startHour, endHour, startMin, endMin));
    }
}
