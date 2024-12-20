Meta:

Narrative:
As a user
I want to perform registration
So that I can login with the new credential

Feature: User Registration

Scenario: Successful registration with a new username
Given the user is on the registration page
When the user enters a new username and password
Then the user is redirected to the login page after registration

Scenario: Unsuccessful registration with an existing username
Given the user is on the registration page
When the user enters an existing username and password
Then the user stays on the registration page