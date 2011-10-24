package uk.co.flamingpenguin.jewel.cli.examples;

import uk.co.flamingpenguin.jewel.cli.Option;

public interface PrimitiveExample
{
   @Option
   boolean getBoolean();

   @Option
   byte getByte();

   @Option
   short getShort();

   @Option
   int getInt();

   @Option
   long getLong();

   @Option
   float getFloat();

   @Option
   double getDouble();

   @Option
   char getChar();

   @Option
   Boolean getBooleanObject();

   @Option
   Byte getByteObject();

   @Option
   Short getShortObject();

   @Option
   Integer getIntObject();

   @Option
   Long getLongObject();

   @Option
   Float getFloatObject();

   @Option
   Double getDoubleObject();

   @Option
   Character getCharObject();
}
