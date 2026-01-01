package tests.cucumber;

import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import business.*;
import io.cucumber.java.en_old.Ac;
import javafx.util.converter.LocalDateStringConverter;

import java.time.*;
import java.util.*;
import java.util.logging.ErrorManager;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTimeOffSteps {
    private Employee employee;

    private LocalDate startDate;
    private LocalDate endDate;
    private Activity activity;

    @Given("the employee selects vacation activity")
    public void the_employee_selects_vacation_activity() throws Exception{
        this.employee = new Employee("Bob", "Software Engineer", "Available", 0);
        Employee.addEmployeeToList(employee, employee.getEmployeeList());
    }

    @Given("the employee selects a start date {int} {int} {int}")
    public void the_employee_selects_a_start_date(int year, int month, int day) {
        this.startDate = LocalDate.of(year, month, day);
        assertEquals(LocalDate.of(year, month, day), startDate);
    }

    @Given("the employee selects an end date {int} {int} {int}")
    public void the_employee_selects_an_end_date(int year, int month, int day) {
        this.endDate = LocalDate.of(year, month, day);
        assertEquals(LocalDate.of(year, month, day), endDate);
    }

    @When("the choices are confirmed")
    public void the_choices_are_confirmed() throws Exception {
        this.activity = new Activity(employee, startDate, endDate, "");
        assertTrue(activity.validateTimeOff(employee, startDate, endDate));
    }

    @When("the selected time exceeds the vacation limit the message {string} is given")
    public void the_selected_time_exceeds_the_vacation_limit_the_message_is_given(String errorMessage) throws Exception {
        try {
            this.activity = new Activity(employee, startDate, endDate, "");
        } catch (Exception E) {
            String message = E.getMessage();
            assertEquals(errorMessage, message);
        }

    }

    @Then("the employee is declared unavailable in the time period")
    public void the_employee_is_declared_unavailable_in_the_time_period() {
        employee.changeAvailability(false);
        assertFalse(employee.getAvailability());
    }
}
