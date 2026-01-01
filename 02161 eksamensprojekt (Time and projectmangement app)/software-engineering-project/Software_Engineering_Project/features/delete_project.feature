Feature: Delete project
  Description: User deletes a project
  Actors: User


  Scenario: Delete project before the start date
    Given the user has a project titled "Project" and the project is scheduled to start in week 28 and end in week 30
    And the current week is 21
    When The user attempts to delete "Project"
    Then Display message saying "Project is successfully deleted"
    Then The project is deleted

  Scenario: Delete project after start week (delete active project)
    Given the user has a project titled "Current project" and the project is scheduled to start in week 21 and end in week 28
    And the current week is 27
    When The user attempts to delete "Current Project"
    Then Display message saying "Cannot delete active project"
    And The project is not deleted
