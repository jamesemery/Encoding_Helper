Jamie Emery and Kai Pei
CS 257
Spring 15

For the challenge feature, we allowed more flexible input into our program.
Our EncodingHelper accepts Unicode codepoints with "u+", "U+", "\u", "\U",
"u", "U", or any amount of spaces preceeding the hexidecimal value.

Ex:
> java EncodingHelper -i codepoint -o string u+0041 U+0041   \u0041 \U0041
	u0041 U0041 0041
AAAAAAA

Also, our program accepts UTF-8 byte input if each byte is preceeded by a "\x",
"x", " 0x" (notice that we do not support bytes in the form "0xE00xEE" because
that's a bad format anyways), or any amount of spaces preceeding the byte.

Ex:
> java EncodingHelper -i utf8 -o string \x41x41x41   0x41 4141
AAAAAA
