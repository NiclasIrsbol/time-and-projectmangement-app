#Scenarios for register vacation time with main and fail scenario
  #Written by s211424

Feature: Register time off
  Description: An employee registers time off work
  Actors: Employee

Scenario: Employee registers vacation successfully
  Given the employee selects vacation activity
  And the employee selects a start date 2024 12 19
  And the employee selects an end date 2024 12 25
  When the choices are confirmed
  Then the employee is declared unavailable in the time period

Scenario: Employee exceeds vacation limit
  Given the employee selects vacation activity
  And the employee selects a start date 2025 2 2
  And the employee selects an end date 2025 10 2
  When the selected time exceeds the vacation limit the message "Time off exceeds allowed leave, please select fewer days" is given
