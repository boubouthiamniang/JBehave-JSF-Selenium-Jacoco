Scenario: Successful login with valid credentials

Given the user is on the login page
When the user enters valid credentials
Then the user is redirected to the welcome page

Scenario: Unsuccessful login with invalid credentials

Given the user is on the login page
When the user enters invalid credentials
Then the user stays on the login page
