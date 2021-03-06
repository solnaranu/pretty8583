# pretty8583
Pretty printer for ISO 8583 messages

## Building
Create a fat jar with:
```bash
sbt assembly
```

## Usage examples
Accepts one argument: a textual representation of an [ISO 8583 message](https://en.wikipedia.org/wiki/ISO_8583).

```bash
java -jar pretty8583-assembly-0.1.jar "0200FA3A4591A880C0000000000006000000..."
```
or
```bash
sbt run "0200FA3A4591A880C0000000000006000000..."
```

## Configuration
ISO 8583 is a flexible standard and different vendors adapt it to their own needs. Since pretty8583 uses [j8583](http://j8583.sourceforge.net/) to parse the messages, a configuration XML with parsing guides must exist. One such is already provided in this repo: [j8583.xml](src/main/resources/j8583.xml). It contains a parsing guide for message type 0x200 (financial ISO 8583 message) and a few others derived from it. It's not yet complete, but even if it were, you would probably have to adapt it to your needs.
