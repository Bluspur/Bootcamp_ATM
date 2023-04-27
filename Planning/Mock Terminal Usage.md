_Note that this isn't an exhaustive mock-up. I just needed something to help me imagine what kind of information I needed to feed to the user._

- Welcome to Craig's ATM! 
- Type "help" at any time to see a list of available commands.
- You are not currently logged in.
- Please login\[L\] or register\[R\] to continue: `userinput`
	-  ___NOTE: Input characters are NOT case-sensitive and if the user puts in an invalid command, then the prompt should repeat___
- `registration`
	-  Please enter a Username: `userinput`
		-  ___NOTE: If the user-input is an invalid username (too short, illegal characters etc.) then an error message will appear and the prompt will repeat.___
	-  Please enter a Password: `userinput`
		-  ___NOTE: If the user-input is an invalid password (too short, illegal characters etc.) then an error message will appear and the prompt will repeat.___
	- Registration Succeeded/Failed.
		- ___NOTE: At this point registration might fail if a user with the given username already exists. In the case of failure, we return to the login or register prompt. If it is successful, then we log that user in and move on to the next stage.___
- `login`
	-  Please enter a Username: `userinput`
		-  ___NOTE: If the user-input is an invalid username (too short, illegal characters etc.) then an error message will appear and the prompt will repeat.___
	-  Please enter a Password: `userinput`
		-  ___NOTE: If the user-input is an invalid password (too short, illegal characters etc.) then an error message will appear and the prompt will repeat.___
	- Login Succeeded/Failed.
		- ___NOTE: Login might fail for a couple of reasons: the user might not exist or the password might be incorrect. In either case, we want to return to the login/register prompt.___
- Welcome `username`!
- Some prompt that the user has no accounts and should open one, or automatically print a list of their accounts if they have any.
- ___NOTE: Formatted table of accounts should include: account number (from 1), name, type, and balance. Probably going to be made of pipes and dashes.___

| Number |  Name | Type | Balance |
| -- | -- | -- | -- |
| 1 | My First Account | Client | £1234.56 |

- Now that the user is logged in, they should be able to perform a number of account actions:
	- Opening a new account.
	- Viewing detailed information about an account, including the overdraft limit.
	- Adding funds to an account.
	- Withdrawing funds from an account.
	- Transferring funds from one account to another.
- All these actions should prompt the user to enter  