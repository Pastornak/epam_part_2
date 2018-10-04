## new feature
## Tags: optional
#
#Feature: Login into google, go to gmail, write email, check if sent, delete email (in one scenario)
#
#Scenario Outline: A full scenario
#    Given I have registered user credentials("<username>", "<login>", "<password>")
#    And I have email credentials("<to>", "<subject>", "<text>")
#    When I navigate to google login form
#    And I enter my credentials
#    And I can see right URL and my username
#    And I navigate to gmail
#    And I write and send email
#    And I wait for email to be sent
#    And I navigate to sent emails page
#    And I can see sent email in the list
#    And I delete last sent email
#    Then I can see delete notification pop-up
#
#    Examples:
#        | username | login | password | to | subject | text |
#        | Yurii Test | yurii.test.email@gmail.com | CreatedByYurii_22.09.18 | yurapaster@gmail.com | Test 1 | Sent by Selenium test 1 |
#        | Yourii Test | yourii.test.email@gmail.com | CreatedByYourii_27.09.18 | yurapaster@gmail.com | Test 2 | Sent by Selenium test 2 |
#        | Youree Test | youree.test.email@gmail.com | CreatedByYouree_27.09.18 | yurapaster@gmail.com | Test 3 | Sent by Selenium test 3 |
#        | Yuree Test | yuree.test.email@gmail.com | CreatedByYuree_27.09.18 | yurapaster@gmail.com | Test 4 | Sent by Selenium test 4 |
#        | Youreey Test | youreey.test.email@gmail.com | CreatedByYoureey_27.09.18 | yurapaster@gmail.com | Test 5 | Sent by Selenium test 5 |