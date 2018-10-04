Feature: Login into google, go to gmail, write email, check if sent, delete email

    Scenario Outline: Login into google
        Given I have registered user credentials("<username>", "<login>", "<password>")
        When I navigate to google login form
        And I enter my credentials
        Then I can see right URL and my username

         Examples:
                | username | login | password |
                | Yurii Test | yurii.test.email@gmail.com | CreatedByYurii_22.09.18 |
                | Yourii Test | yourii.test.email@gmail.com | CreatedByYourii_27.09.18 |
                | Youree Test | youree.test.email@gmail.com | CreatedByYouree_27.09.18 |
                | Yuree Test | yuree.test.email@gmail.com | CreatedByYuree_27.09.18 |
                | Youreey Test | youreey.test.email@gmail.com | CreatedByYoureey_27.09.18 |
