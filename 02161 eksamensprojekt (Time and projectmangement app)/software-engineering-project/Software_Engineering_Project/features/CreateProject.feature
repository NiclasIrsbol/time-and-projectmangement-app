Feature: Create project
  Description: User creates a new, empty event
  Actors: User
  #Written by s223946

  Scenario: User creates event with due date in the future
    Given a valid user "QWER"
    And a current week 28
    When the user creates an empty project with due-date in week 29
    Then an empty project is initialized

  Scenario: User creates event with invalid due date
    Given a valid user "QWER"
    And a current week 28
    When the user creates an empty project with due-date in week 27
    Then an error "invalid due-date" will be thrown

  Scenario: User adds other user with valid project count to project
    Given a valid user "QWER"
    And another user "User2" with activity count at 9
    When the user creates an empty project with due-date in week 29
    And "QWER" adds "User2" to a project
    Then "User2" will be added to the project, and the project count will increase

  Scenario: User tries to add a user with an invalid project count
    Given a valid user "QWER"
    And another user "User2" with activity count at 10
    When the user creates an empty project with due-date in week 29
    And "QWER" adds "User2" to a project
    Then "User2" will not be added to the project, and an error will be thrown