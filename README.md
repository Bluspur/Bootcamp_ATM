# Bootcamp_ATM
  Gateshead College Software Development final project. A prototype ATM/Account management app written in Java.

## Requirements
- [x] Should be able to manage logging in and registration of new users.
- [x] Users should be prompted to create new accounts.
- [x] New Accounts should come in the following types: Client, Community and Small Business.
  - [x] Account type should determine the overdraft limit for a given account. (£1,500;£2,500;£1,000).
- [x] Users should be able to manage any existing accounts.
- [x] Users should be able to perform the following transactions.
  - [x] Adding Funds to an account.
  - [x] Withdrawing Funds from an account.
  - [x] Transferring Funds from one account to another.
- [x] Restrictions should be placed on all accounts that require a signatory.

## Implementation
For my solution I opted to keep things as simple as possible, as to demonstrate an understanding of Object Oriented Design and control flow. While I did toy with the idea of creating a GUI or TUI application, I chose to go with a pure terminal i/o approach as I felt it be a better demonstration of my knowledge of programming than the alternative, which might simply show an understanding of a single library.

Internally, balance values are stored as BigDecimal values. I chose to use this type, due its reputation as the preferred Java type for representing money, and also due to the very accurate methods it provides for performing mathematical operations and comparisons while avoiding floating-point errors.

Early on in development, I was faced with several functions that might fail to return a value, and thus a choice over whether to return null or to throw exceptions. I ultimately chose the latter, as I found it to be more explicit than returning null and it also made it easier to test for specific types of failure. So you will find several custom exceptions in the project.

The app is in a CLI style, and works via a loop that captures terminal input from the user and parses it into a command for the application. The user is able to list all available commands at a given time with the help[h] command.

One addition I made to the requirements was a basic form of database persistence. It uses simple Java object serialization and deserialization. It is *NOT* intended as an example of how I feel a database should be saved, but rather as a simple way to demonstrate logging into an existing database and retrieving data from it. User data is not encrypted. Note that by default the database is saved to the User's home directory as **database.ser**, and that unit tests will create an additional file at the same directory called **test_database.ser**.

## Documentation
I have included a copy of a Obsidian markdown book that I was using throughout the project as a form of planning. I used it mainly to just jot down ideas about what a class should be doing and as a bit of a brain dump on certain problems regaring implementation. I became less judicious about it towards the end of the project, when I had a clearer understanding of what the outcome was going to look like, but I wrote quite a bit about my most foundational types.
I also spent a good deal of time at the end providing JavaDoc comments for all of the public API on my classes and also for any private methods that I felt needed some extra explanation. 
