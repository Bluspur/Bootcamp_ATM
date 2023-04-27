A type which represents a single user of the app. They have their own accounts and access to their information needs to be protected with some kind of login system.
___
### <u>Initial Ideas</u>
Storage of a list of accounts.
An ID that can be referenced for database queries.
A username, for logging in and also for "display" inside the accounts.
A password, for logging in.

### <u>27.3.2023</u>
I've had a couple of thoughts since the initial plan regarding the User class:
Firstly, there is currently no need to store an ID at all. This is something that can be generated by the "database" via the `HashCode()` method. It saves me having to work out something unique, and for a prototype, it is unlikely I am going to run into the limits of integer storage.
Secondly, there is no need to store a collection of [[Account]] inside the User object itself. It isn't relevant to the user, so would be better off being handled by the "database".
Thirdly, because there is some validation to do on both the username and password (null, length, strength, etc), it would be worth wrapping them inside their own separate classes. That way, they can manage their own individual validation. As such I will be implementing those classes next. I have thought about refactoring them to both inherit from something like a `ValidatedString` class, but they don't have that much shared behaviour, and they are never used interchangeably, so I have decided that it wouldn't make any reasonable savings for the time being.

### <u>Rough Structure</u>
- `Username userName
	- For login
		- "Uniqueness" to be checked externally by the database.
		- Must not be null!
		- Wrapper over `String` helps to contain the validation behaviour to a single piece of code.
	- Needs to have some kind of getter. For referencing in GUI/Console etc.
- `Password password
	- For login
		- Doesn't need to be unique.
		- Must not be null!
	- Later could be used to introduce some kind of encryption.
- `verifyUser(username, password) -> boolean`
	- A method that takes a Username and Password.
	- It then checks those arguments against its own values and if they are <b>BOTH</b> the same then it returns true, otherwise it returns false.
	- Originally this method took string values and not Username/Password, but I opted eventually to switch. This was mostly a point of convenience and to make it clear exactly what kind of values should be passed in.