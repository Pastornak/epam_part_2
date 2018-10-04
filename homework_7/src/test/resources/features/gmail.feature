#Feature: Login into google, go to gmail, write email, check if sent, delete email
#
#    Scenario: Login into google
#        Given I have registered user credentials
#        When I navigate to google login form
#        And I enter my credentials
#        Then I can see right URL and my username
#
#    Scenario: Go to gmail, write email, confirm it is in the sent list
#        Given I have navigated to gmail
#        And I have email credentials
#        When I write and send email
#        And I wait for email to be sent
#        And I navigate to sent emails page
#        Then I can see sent email in the list
#
#    Scenario: Go to sent email, delete sent email
#        Given I have navigated to sent emails page
#        When I delete last sent email
#        Then I can see delete notification pop-up