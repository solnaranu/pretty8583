package com.solnaranu.pretty8583

import java.nio.charset.StandardCharsets

import com.solab.iso8583.IsoMessage
import com.solab.iso8583.parse.ConfigParser

object Pretty8583 {

  private val fieldNames = Seq(
    2 -> "PAN",
    3 -> "Processing code",
    4 -> "Transaction amount",
    5 -> "Settlement amount",
    7 -> "Transmission date & time",
    11 -> "STAN",
    12 -> "Local time",
    13 -> "Local date",
    14 -> "Expiration date",
    15 -> "Settlement date",
    17 -> "Capture date",
    18 -> "Merchant type",
    19 -> "AICC",
    22 -> "POS entry mode",
    24 -> "NII",
    25 -> "POS condition",
    28 -> "Transaction fee",
    29 -> "Settlement fee",
    30 -> "Transaction processing fee",
    31 -> "Settlement processing fee",
    32 -> "AIIC",
    33 -> "FIIC",
    34 -> "PAN extended",
    35 -> "Track 2 data",
    37 -> "Retrieval reference number",
    38 -> "Authorization identification response",
    39 -> "Response code",
    41 -> "Card acceptor terminal ID",
    42 -> "Card acceptor ID",
    43 -> "CA acceptor name/location",
    47 -> "Additional data (national)",
    48 -> "Additional data (private)",
    49 -> "Currency code, transaction",
    50 -> "Currency code, settlement",
    52 -> "PIN block",
    54 -> "Additional amounts",
    59 -> "Reserved",
    60 -> "Reserved",
    62 -> "Reserved",
    70 -> "NMIC",
    84 -> "Acquirer fee",
    90 -> "Original data elements",
    95 -> "Replacement amount",
    100 -> "Receiving institution ID",
    102 -> "Account ID 1",
    103 -> "Account ID 2",
    113 -> "Customer Name",
    114 -> "Customer Address",
    115 -> "Customer Other",
    116 -> "Customer Accounts",
    127 -> "Mini statement details"
  ).toMap

  implicit class IsoMessageOps(val isoMessage: IsoMessage) extends AnyVal {
    def getOptionalValue(field: Int): Option[String] = Option(isoMessage.getObjectValue[String](field))

    def primaryBitmap: String = isoMessage.debugString.slice(4, 20)

    def secondaryBitmap: Option[String] = if (primaryBitmap.head > '7') Some(isoMessage.debugString.slice(20, 36)) else None

    def pretty: String = {
      val fields = for {
        field <- 2 to 128
        name <- fieldNames.get(field)
        value <- getOptionalValue(field)
      } yield {
        val fieldNo = f"$field%03d"
        s"$fieldNo. $name:" -> value
      }

      val labelColumnWidth = fields.map(_._1.length).max

      val headers = Seq(
        Some("     Message type:" -> s"${isoMessage.getType.toHexString}"),
        Some("     Primary bitmap:" -> s"$primaryBitmap"),
        secondaryBitmap.map(f => "     Secondary bitmap:" -> s"$f")
      ).flatten

      val lines = (headers ++ fields) map { case (label, value) =>
        val paddedLabel = label.padTo(labelColumnWidth, ' ')
        s"$paddedLabel [$value]"
      }
      lines.mkString("\n")
    }
  }

  private val MessageFactory = {
    val factory = ConfigParser.createDefault()
    factory.setBinaryFields(false)
    factory.setCharacterEncoding(StandardCharsets.US_ASCII.name)
    factory
  }

  def main(args: Array[String]): Unit = {
    require(args.length > 0, "Missing argument (ASCII ISO 8583 message)")

    val asciiMessage = args(0)
    val message = MessageFactory.parseMessage(asciiMessage.getBytes(StandardCharsets.US_ASCII), 0, false)
    println(message.pretty)
  }
}
