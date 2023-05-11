LZ78 Compression Assignment
This is an implementation of the LZ78 Compression algorithm in Java. 
The implementation includes an encoder and a decoder, as well as bit-packer and bit-unpacker programs.

Usage
To compile the programs, run the following command:

javac LZencode.java LZdecode.java LZpack.java LZunpack.java
javac BitPacker.java BitUnpacker.java

To run the programs, use the following commands:

Encoder
java LZencode <input_file> <output_file>

Decoder
java LZdecode <input_file> <output_file>

Bit-Packer
java LZpack<input_file> <output_file>

Bit-Unpacker

java LZunpack <input_file> <output_file>