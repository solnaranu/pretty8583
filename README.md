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
sbt run "0200FA3A4591A880C0000000000006000000"
```

## Configuration
ISO 8583 is a flexible standard and different vendors adapt it to their own needs. Since pretty8583 uses []() to parse messages, a configuration XML with parsing guides must exist. One such is already provided in this repo: [j8583.xml](../blob/master/src/main/resources/j8583.xml). It contains a parsing guide for message type 0x200 (financial ISO 8583 message) and a few others derived from it. You **will** need to adapt this to your needs.
