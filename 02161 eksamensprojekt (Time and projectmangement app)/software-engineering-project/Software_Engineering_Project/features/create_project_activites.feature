Feature: Creating activies in a project
  Description: The project leader adds a new activity to a project
  Actors: Project leader

  Scenario: Adding an activity to a project
    Given A project with the name "Project" exists
    When "QWER" adds an activity "Task" to "Project"
    Then The activity "Task" is added to "Project"

    Scenario: Adding an activity to a wrong project
      Given A project with the name "Project" exists
      When "Projectleader1" adds an activity "Task" to "Project2"
      Then An errormessage displays "Project does not exist"