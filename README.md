#  The Good™ Bank API

## About
The founders had a wish that their bank would be really Good™. The aim of the bank is to deliver a Good™ experience to the user.


## Tech Stack
- ### Backend
  - Java
    - The core programming language of our application. The Object-Oriented nature of Java allowed us to modularize our application and keep track of the logic in a concise and readable manner.
  - Spring Boot
    - Built to jump start the development process, Boot allowed us to start development of core functionality much sooner with its opinionated approach to Java Application development.
  - Junit
    - A Robust testing framework for Java applications. We utilized JUnit to ensure the functionality of our service layer, with the help of Mockito to mock outside functionality.
- ### Frontend
  - TypeScript
    - The core language of our frontend. TypeScript helped us stay organized with its strict type system, and gave us tools such as Interfaces and Enums which we could use as contracts to further enforce Typesafety.
  - React
    - A Library for creating User Interfaces. Like TS, React helped us stay organized by providing structure to our application. It helped achieve this by breaking down our user interface into reusable components that encapsulate views, styles and behavior.
  - MUI
    - A Component Library and Style System for React. MUI gave us a unified styling framework to work by, which helped us to achieve consistent styling throughout our application.
  - Redux
    - A State Management system for JavaScript/TypeScript applications. Redux allowed us to decouple our frontend state from our component tree, allowing for different components in the tree to subscribe to the state when needed, making our frontend code much cleaner.
  - Jest
    - A Tried and true testing framework for JavaScript applications. Jest helped us to ensure that our React components were functioning as intended.

- ### DevOps
  - Maven
    - Our Java build system: Maven allowed us to package our application for development, testing and finally deployment.
  - NodeJS
    - A JavaScript runtime that is decoupled from the browser. We used NodeJS to build and test our application, and we used its package manager, NPM to manage our external JS dependencies.
  - AWS
    - Our cloud infrastructure, AWS provided many tools to help us with hosting our backend and frontend, such as:
      - CodePipeline/CodeBuild
        - CodePipeline provides a pathway from our git repositories to deployment, with CodeBuild allowing us to configure the build step. We used these tools to facilitate continuous integration and delivery.
      - Elastic Beanstalk
        - An Environment built around EC2 with many features that help with management and deployment of web applications. We used beanstalk to host our Java API.
      - S3
        - Standing for 'Simple Storage Service', S3 was used to host our React UI after it was built.

## Product Features (MVP)
- ### Team 1
  - Send Money
  - Recent Transactions
- ### Team 2
  - Track Income
  - Track Expenses
  - Loan Applications
- ### Team 3
  - Transfer money between accounts
  - Track multiple Accounts
  - Credit Card Payments
  - Credit Card Account
- ### Team 4
  - Reset Password
  - Notifications
  - User Profile


## Run:
- Clone the repo
- if using Intellij
  - Load up the project and navigate to `src.main.java.com.revature`
  - Run the `main()` method of `BankingApplication`

## Build:
- Clone the repo
- run `mvn clean package -Pprod`
### Run the build:
- run `cd target`
- run `java -jar curated-banking-spring-1.0-SNAPSHOT.jar`