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
The app is in a CLI style, and works via a loop that captures terminal input from the user and parses it into a command for the application. The user is able to list all available commands at a given time with the help[h] command.
