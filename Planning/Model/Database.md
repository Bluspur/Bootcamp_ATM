Given there were no requirements related to data security or query features, I decided for this project that my best bet for keeping things simple was to use simple map data structures to store my account data.

I opted to use two map layers during the project. 
1. Key: `Username` - Value: `User`. 
	- This is especially helpful during the login phase as it means we can check if a `Username` exists, and if it does, attempt to login, and return the `User` object.
2. Key: `User` - Value: `List<Account>`.
	- This is the "main" database. It allows us to store a collection of `Account` against a `User`. This way, the `User` object does not need to know about their accounts, as that is the responsibility of the database. Plus we can quickly hand the database a `User` object and retrieve a **Reference** to their accounts.
There are only a few methods required for the database, so we can keep both maps private and not have to worry about users performing unneccesary modifications to them.

As a final addition to the project I also added a pair of methods that allow the `Database` object to be saved and loaded to/from a file on the system. This allows some permanence, despite being an unideal solution.