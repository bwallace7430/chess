# Class Notes
## Week 1
### Exam 1 Prep
Finish up Phase 0 of Chess project, write down ways to do it better. Set it aside, try again.
If it takes more than 3 hours, repeat the process.
### Java Fundamentals
#### Files
Every file has to have at least and at most one overarching class. The name of the file must
match the name of the class. Any class can be the start of your code.
A file is a class, a directory is a package.
#### Classes
The keyword "static" means that you don't have to make an instance of the head class in 
order to call the function with keyword "static". String is a class. As such, it has a bunch
 of methods that you can use on it. Look it up if you need to.
#### Types
- **Strings**: String variables don't exist, but there is a String class built into the Java 
library. var keyword tells the computer to figure out what type the variable is based on 
what you set the variable equal to. new String initializes automatically to NULL
- **Arrays**: brackets go after the type. (ie String[] myString = new String[10]).
You can declare an empty array to allow it to grow as you go, like in the above.
## Week 2
### Java Conventions
Functions are lowercase and typed without spaces or dashes likeThis. Classes are one word and
capitalized.
### Package
Everything in Java is in a package. If you don't specify the package (by using the format 
package thisPackage) then it won't do anything.
### Object Class
All classes are also object classes. This is the parent class. toString() returns a fancy memory object.
You will want to override this. equals() returns a boolean. hashCode() gives you a unique int for each object.
wait() and notify() do NOT overrun these. clone() gives you a duplicate object. It's kinda useless. To 
override a function, write @Override above it
### Record
Overrides the object equal() method and getters. Records are immutable, but are essentially just like classes.
- Syntax:
record CowRecord(String name){}
### Chess
softwareconstruction/chess/0-chess-moves/rodham-notes/ChessPhase0Design.png
Movement or Rules class?
HashMap with piece type and that piece's movement rule
### Good Software Design
Modularity - a place for everything and everything in its place
### Polymorphism
Allows an object to change certain aspects of itself according to context.You want to limit exposure to as many 
elements as possible. Allows for more flexibility.
#### Interface
Declares the functions that are available, but doesn't implement any of them. A class that implements the interface 
implements all the functions, but can also implement other functions that aren't in the interface.
#### Inheritance
When you extend an object, it's literally as if you made a carbon copy of the code of that object. You are ALWAYS
extending from the object class.
#### Abstract Class
Has certain functions that are implemented in a specific way. Has abstract functions that are not implemented 
that would be implemented by extending that abstract class.
### Copy Constructor
Overload your constructor with a constructor that takes an object and assigns each property to the values held by that
object. Shallow copy = the data for both objects is shared even though there is a different shell, as it were. Deep 
copy. Look into it, deep copies are IMPORTANT.
## Week 3
### JDK Collections
There's lots of inheritance and classes with these. Important: List, Set, Queue, Map, Iterator
#### Aggregation
You are made up of different organs. A class is made up of member variables. This is an alternative to 
extension/inheritance. The same thing as Encapsulation.
#### MultiSelect
A keyboard shortcut that creates multiple cursors. Something with Alt? Look it up.
#### Comparables
Returns -1, 0, or 1 depending on which item is greater.
### Exceptions
When an exception is thrown, the program skips the entire call stack up to whatever function catches the exception.
All the intermittent code is skipped, and the code continues from where it is caught. This is for when bad things 
happen. Exceptionally bad things. If an exception could be thrown by a function, you have to say that in your function.
In try{} finally{}, finally block will always run, even if there's an exception thrown and not caught. Try with 
resources will take care of the resources that you would otherwise need a finally block.
## Week 4
### Domain Driven Design
Focuses the design around the problem that is being solved/the audience that is using the product. Focus on: actors,
tasks, objects, and interactions. Simplify your domain to represent what the customer actually requires.
### Object Oriented Design
Creates separations and constructs that everyone is sorted into. Objects have two things: properties and methods
#### Abstraction
More efficient, more security, more adaptable, more reuse.
#### Relationships
is-a: Inheritance; has-a: Aggregation (field property); uses-a: Transient Association (often a parameter for 
a method)
#### Simplicity
DRY (Don't Repeat Yourself); YAGNI(You Aren't Gonna Need It) - with this one, be careful not to decompose things
TOO much or over-optimize;
## Week 5
### Different Types of Classes
#### Static Inner Class
They are generally private or protected, and are only used within the class they are declared.
#### Inner Class
Can access everything that its parent class has, including (and most importantly, the "this" pointer). I'm not really
sure about this one; look it up. Has access to the stack of variables from the scope where it was created (this is 
called closure). This is good for "screenshotting" the environment of the program at one specific time. JavaScript 
does this.
#### Anonymous Class
An interface class is declared. The methods of the interface are implemented inline on the fly. Lambda functions have 
the form: () -> 0 (if the function has no parameters and returns 0) OR (a, b) -> a+b (if the function has two parameters
a and b, and returns a+b)
#### Functional Interface
An interface that only declares one method function.
## Week 6
### Server
A server is the computer. Most of the time the services are some sort of a product or something being provided, while
the server mainly handles network requests and endpoints.
### Endpoints
Clear, register, login, logout, list games, create game, join game; Can be thought of as methods.
### Good design
Domain driven, encapsulation, inheritance, cohesive, uncoupled, decomposition, YAGNI, POLA, KISS, single responsibility,
open/closed, liskov, interface segregation, dependency inversion
### Test-Driven Development/Error Handling
Write your code and your tests at the same time. Your tests should inform your code and your code should inform your 
tests. Exception class with error code that matches HTTP codes. Research this one a bit more. I'm not sure I get it.
### HTTP
Client to server communication. Domain name/host name is kinda like a general location. Port is a specific location on 
the computer. Spark Java helps you to write end handlers.
## Week 7
### Error Handling Part 2
Use Spark.notFound in place of an HTTP endpoint such as Spark.get or Spark.post . You can also have a Spark.exception . 
Talk to Jonah about this one again.
### Setting up the client
Allow multiple instances in order to have play chess against yourself.
### Phase 3 Web API
Mark resources folder as resources root, mark test folder as resources root. Your DataAccessLayer has to have a memory 
version and a SQL version
## Week 8
### Phase 3 Tips
If you're running on a time constraint, look at Pet Shop. Do not create a global object for your data access. Create
a data access object, and pass in that object to your service and then pass that into your server. DataAccessInterface 
has two implementations (Memory and SQL). The memory implementation is hardcoded data.
### Test Driven Development (Part 2)
You want a middle ground between over-testing and under-testing. Test: can you call a function? Can you pass in the 
expected parameters? Does it return the expected value? Don't test independent features that will naturally be tested 
by other features (ie register user is tested by log in user). Use the library junit (import org.junit.jupiter.things)
for testing. Before your test write "@Test"
## Week 9
### Writing Tests
When you write tests, run them with coverage to see what they cover.
### Database
Text fields are for large groups of text. JSON files can be blob or text.
## Week 10
### MySQL Phase 4
DO NOT PUT YOUR CREDENTIALS ON GITHUB. USE THE GITIGNORE FILE. Things left to do: gitignore file, password hashing, 
chess game jsonifying and unjsonifying, database tests. 
## Week 11
### Phase 5 - Clients
Logging: persistent, immutable, aggregated, accessible, performant. Debugging: code review, reproduce, logging, 
debugger, verify. ALWAYS test-driven development. Look up how to do logging in intellij.