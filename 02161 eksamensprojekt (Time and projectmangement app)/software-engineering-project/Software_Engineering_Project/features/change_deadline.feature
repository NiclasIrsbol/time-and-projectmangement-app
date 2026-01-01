#Scenarios for changing deadline of project.
  #Written by s211424

Feature: Change deadline
  Description: Project Leader changes the due date of a project.
  Actors: Project Leader

  Scenario: Project Leader changes deadline of "Project 1"
    Given "Project 1" exists with start in week 20 and deadline in week 25
    When the Project Leader sets the new deadline to week 27
    Then the deadline for "Project 1" is week 27


  Scenario: Project Leader chooses empty project
    Given "Project x1" is empty
    When the Project Leader sets the new deadline to week 27
    Then the error-message "Project does not exist" is given
