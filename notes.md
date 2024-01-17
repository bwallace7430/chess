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
