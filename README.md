MusCode
=======

MusCode converts XOR-encrypted .mus files for Minecraft into ordinary Ogg Vorbis files and vice versa.

Usage
-----

Basic usage:

    java -jar muscode.jar <file/dir>
	
Decode music.mus in the current working directory:

    java -jar muscode.jar music.mus
	
Encode music.ogg in the current working directory:

    java -jar muscode.jar music.ogg

Convert all files in the Minecraft music directory under Windows:

    java -jar muscode.jar %APPDATA\.minecraft\resources\streaming

Convert all files in the Minecraft music directory under Linux/MacOS X:

    java -jar muscode.jar ~/.minecraft/resources/streaming