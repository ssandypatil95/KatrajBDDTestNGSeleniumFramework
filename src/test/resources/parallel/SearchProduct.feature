Feature: search product
@functional
Scenario: search functionality
Given user is at the landing page
When user the text "mobile phone" and search
Then mobile phone should get display