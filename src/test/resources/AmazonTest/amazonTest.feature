Feature: CucumberJava
Scenario: As a consumer I would like to remove product "product name" from the shopping cart so that I don't need to purchase the product "product name" as part of a future order.
  Given I have open the browser
  And I have open the Amazon website
  And I have put the first book "Ice Ages: Solving the Mystery" into the shopping cart
  And I have put the second book "Think Java: How to Think Like a Computer Scientist" into the shopping cart
  When I go to the shopping cart page
  And I remove the first book from the shopping cart
  Then The book "Ice Ages: Solving the Mystery" should be removed from the shopping cart
  And There should be one item "Think Java: How to Think Like a Computer Scientist" left in the shopping cart