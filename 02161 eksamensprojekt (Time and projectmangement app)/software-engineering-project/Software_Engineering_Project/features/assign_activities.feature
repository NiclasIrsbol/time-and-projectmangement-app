Feature: Assigning an activity to an employee
  Description: The project leader assigns an activity to an employee
  Actors: Project leader

  Scenario: Assigning an activity to an employee with less than 10 activities
    Given A project with name "project" and activity with name "Task", and employee with name "employee" exists
    And Employee has less activities assigned than 10
    When Project leader with name "QWER" tries to assign activity with name "Task" to project "project" to employee "employee"
    Then Activity with name "Task" is assigned to employee "employee" in project "Project"

    Scenario: Assigning an activity to an employee with more than 10 activities
      Given A project with name "project" and activity with name "Task", and employee with name "employee" exists
      And Employee has more activities assigned than 10
      When Project leader with name "QWER" tries to assign activity with name "Task" to project "project" to employee "employee"
      Then System gives errormessage "Employee has exceeded activity limit."

