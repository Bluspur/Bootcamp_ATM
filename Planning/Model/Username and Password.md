Two very simple classes over a string that are guaranteed to meet the requirements for a valid username or password. 
At the end of the project, this concept *does* work, but presents challenges when we need to decide when to use a username/password or a string. It might have been better to simply run a string through a validation method on some helper class, or maybe to combine username with password in some new ValidatedString class. 
The current interpretation does have the advantage of allowing the internal validation to be changed in isolation, so could be used for some kind of encryption.

In particular though, the password class has an issue with exposing its value, which was neccessary to be able to test for equality. Given more time, I'd probably need the value visible, but probably immediately scramble the password upon validation, so that the "raw" password is never visible, and comparison is run upon the encrypted values.

Definitely a candidate for refactoring.