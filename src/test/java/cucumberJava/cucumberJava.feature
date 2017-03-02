Feature: CucumberJava
Scenario: As a consumer I would like to remove product "product name" from the shopping cart so that I don't need to purchase the product "product name" as part of a future order.
  Given I have open the browser
  And I have open the Amazon website
  And I have put book "ice age" into the shopping cart
  And I have put book "introduction to java" into the shopping cart
  When I go to the shopping cart page
  And I remove the book "ice age" from the shopping cart
  Then The book "ice age" should be removed from the shopping cart
  And There should be one item "introduction to java" left in the shopping cart