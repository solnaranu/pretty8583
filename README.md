# pretty8583
Pretty printer for ISO 8583 messages.

## Building
```bash
sbt assembly
```
to create fat jar

## Usage examples
Accepts one argument: a textual representation of an [ISO 8583 message](https://en.wikipedia.org/wiki/ISO_8583).

```bash
java -jar pretty8583-assembly-0.1.jar "0200FA3A4591A880C0000000000006000000..."
```
or
```bash
sbt run "0200FA3A4591A880C0000000000006000000"
```
