#Scenarios for assigning new employee to project.
  #Written by s211424

Feature: Assign free employee to a project
  Description: Project Leader assigns a free employee to a project.
  Actors: Project Leader

  Scenario: Project Leader assigns a free employee to a project
    Given the employee with initials "TYUI" is free
    And "activity" is an existing activity in a project
    Then the Project Leader assigns "TYUI" to "activity"


  Scenario: Project Leader selects invalid "Project x1" and free employee
    Given "Project x1" does not exist
    And the employee with initials "TYUI" is free
    When the Project Leader assigns "TYUI" to project "Project x1"
    Then the message "Project does not exist..." returns

  Scenario: Project Leader selects "Project 1" and invalid employee
    Given "Project 1" does exist
    And employee "TYUI" is not free
    When the Project Leader assigns "TYUI" to project "Project x1"
    Then the message "Employee is not available to work on this project" returns
