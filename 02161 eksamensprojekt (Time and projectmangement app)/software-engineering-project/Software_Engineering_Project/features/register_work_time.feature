#Scenarios for register working time with main and fail scenario
  #Written by s211424

Feature: Register working time
  Description: An employee registers the time they've spent on an activity
  Actors: Employee

Scenario: Register working time successfully
  Given that the employee exists
  When the employee selects a start time 18 30
  And the employee selects an end time 21 00
  Then the working time is confirmed successfully

Scenario: Employee inputs invalid working time
  Given that the employee exists
  When the employee selects a start time 13 30
  And the employee selects an invalid end time 25 30
  Then the working time is not confirmed