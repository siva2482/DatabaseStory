#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
Feature: Testing the database
 Background:
 Given user navigates to the url

  
  Scenario: vaerifying and validating database
  When user selects the dropdown
  Then takes data from the database
  And assetts it with dropdowwn  
  
  When user select the department from cities
  Then user takes data from database
  And assertit with the table
  
  When user select employee with thrird largest salary
  Then user takes employee from database
  And assert it with the web table