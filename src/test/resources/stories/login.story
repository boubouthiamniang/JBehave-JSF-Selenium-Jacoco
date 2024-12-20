Feature: User Login and Logout

Scenario: Successful login with valid credentials

Given the user is on the login page
When the user enters valid credentials
Then the user is redirected to the welcome page

Scenario: Unsuccessful login with invalid credentials

Given the user is on the login page
When the user enters invalid credentials
Then the user stays on the login page


Scenario: Successful logout

Given the user is logged in
When the user clicks the logout button
Then the user is redirected to the login page



