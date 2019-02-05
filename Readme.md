# Calculator
Reverse Polish notation calculator is developed using functional programming aspects of Java-8, deque (represents stack) and BigDecimal 

`sqrt` over BigDecimal is support from Java 9 onwards
   

# Usage
```Bash
java -jar build/libs/Airwallex-1.0-SNAPSHOT.jar
```

Use Ctrl + C to exit

```Command line
1 2 3 4 5
*
>> Stack: 1 2 3 20
```

### Available operators
operator | operation                    | example
:-------:|------------------------------|------------
`+`      | addition                     | `1 2 +` = 3
`-`      | subtraction                  | `1 2 -` = -1
`*`      | multiplication               | `2 3 *` = 6
`/`      | division                     | `7 2 /` = 3.5
`sqrt`  | square root                   | `9 sqrt` = 3
`clear` | clear stack values (no undo) | `7 2 clear` = <empty stack>
`undo`  | undo last operation          | `1 2 undo` = 1
`uclear`| clear stack values (with undo) | `7 2 uclear undo` = 7 2

Currently the 'clear' operation is implemented such that, it will throw an exception if performed on a empty stack.

## Development Environment
* Java 1.8+ version
* Gradle build tool   
* Junit 4.12

## Building Jar from source
```Bash
gradle assemble
```

## Tests
```Bash
gradle test
```

All example tests scenarios are covered in 'ExampleTest.java' file.

## Todos
ArithmeticOperations class can be made public if arithmetic operations are needed without the `undo` support.
