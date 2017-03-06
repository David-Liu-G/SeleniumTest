Feature: As a consumer I would like to remove product "product name" from the shopping cart so that I don't need to purchase the product "product name" as
  part of a future order.

#normal flow
  Scenario: Remove the item "Ice Ages: Solving the mystery" from the shopping cart by using "delete"
  Given I have open the browser
  And I have open the Amazon website
  And I have put the book "Ice Ages: Solving the Mystery" into the shopping cart
  And I have put the book "Think Java: How to Think Like a Computer Scientist" into the shopping cart
  When I go to the shopping cart page
  And I remove the book "Ice Ages: Solving the Mystery" from the shopping cart
  Then The book "Ice Ages: Solving the Mystery" should be removed from the shopping cart
  And There should be one item "Think Java: How to Think Like a Computer Scientist" left in the shopping cart

#alternate flow
Scenario: Remove the item "Ice Ages: Solving the mystery" from the shopping cart by using "save for later"
  Given I have open the browser
  And I have open the Amazon website
  And I have put the book "Ice Ages: Solving the Mystery" into the shopping cart
  And I have put the book "Think Java: How to Think Like a Computer Scientist" into the shopping cart
  When I go to the shopping cart page
  And I remove the book "Ice Ages: Solving the Mystery" from the shopping cart by save for later feature
  Then The book "Ice Ages: Solving the Mystery" should be removed from the shopping cart
  And There should be one item "Think Java: How to Think Like a Computer Scientist" left in the shopping cart

#error flow
Scenario: Unable to remove anything if the shopping cart is empty
  Given I have open the browser
  And I have open the Amazon website
  When I go to the shopping cart page
  Then Nothing can be removed from the shopping cart