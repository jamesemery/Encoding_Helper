/**
 * Demo program for bitwise operations in Java.
 * 
 * By Jadrian Miles, 2015-04-07.
 */
public class BitDemo {
    public static void main(String[] args) {
        // To directly create a byte with a given value, we need to express that
        // value with a numerical literal.  We use a leading "0x" in the literal
        // to signal that we're expressing the value in hexadecimal.  We also
        // need to explicitly cast this value as a byte, because by default
        // Java assumes we're expressing a 32-bit int.
        byte b = (byte) 0x6B;
        
        // See the comment in byteToHexString() that explains how we properly
        // format the value for display to a person.
        System.out.println("Here's your byte: " + byteToHexString(b));
        
        // Let's take a moment an look at our byte in binary.
        //    HEX DIGIT    DECIMAL DIGIT   SUM OF POWERS OF 2   BINARY
        //    ---------    -------------   ------------------   ------
        //        6              6                   4 + 2       0110
        //        B             11               8 + 2 + 1       1011
        // So we have:
        //  Hex:          0x   |-----6-----|   |-----B-----|
        //  Binary:            0   1   1   0   1   0   1   1
        //                  -----------------------------------
        //  Bit index:         7   6   5   4   3   2   1   0
        //  Place value:     128  64  32  16   8   4   2   1
        // Bits in a byte are numbered (indexed) starting at 0 from the right
        // end.  So in this particular byte, we say that bits 0, 1, 3, 5, and 6
        // are "on" (that is, they're 1s).  The rest are implicitly "off" (0).
        
        // To pick out various bits of interest in our example byte, we need to
        // use "byte masks": special bytes whose bits are a pattern of 0s or 1s
        // that are useful to us.  In particular, here are three bytes that have
        // only one bit that's a 1 (the other 8 bits are all 0):
        byte low_bit_mask = (byte) 0x01;   // Bit 0 is on.
        byte mid_bit_mask = (byte) 0x20;   // Bit 5 is on.
        byte high_bit_mask = (byte) 0x80;  // Bit 7 is on.
        // ... and here's a byte that has a few bits on in the middle.
        byte mid_range_mask = (byte) 0x3C; // Bits 2, 3, 4, and 5 are on.
        
        // =====================================================================
        // Java (like most other programming languages) defines a set of
        // "bitwise" operators for working with bits.  Let's learn about them.
        
        // First up is the "bitwise AND" operator, &:
        byte low_bit = (byte) (b & low_bit_mask);
        System.out.println("low bit:          " + byteToHexString(low_bit));
        // The result here is 0x01.  Why?  Let's look at the original value and
        // the mask in binary:
        //   Original byte (0x6B):     0 1 1 0  1 0 1 1
        //   Low-bit mask  (0x01):     0 0 0 0  0 0 0 1
        //                             ================
        //   Bitwise AND         :     0 0 0 0  0 0 0 1
        // The two four-bit sequences in the result correspond to the hex digits
        // 0 and , respectively, so that's what we see.
        //   Why did we have to explicitly cast the result of the & as a byte?
        // Try deleting the cast and see what happens.
        //   Java will constantly insist on converting the output of any
        // operation involving bytes into an "equivalent" signed int.  This is
        // usually fine for intermediate values, but before we store the final
        // result as a byte, we have to explicitly cast it.
        
        //   Here are some more bits pulled out by AND-ing with a mask:
        
        byte mid_bit = (byte) (b & mid_bit_mask);
        System.out.println("mid bit:          " + byteToHexString(mid_bit));
        // Again, why do we get 0x20 here?  Look at it in binary:
        //   Original byte (0x6B):     0 1 1 0  1 0 1 1
        //   Mid-bit mask  (0x20):     0 0 1 0  0 0 0 0
        //                             ================
        //   Bitwise AND         :     0 0 1 0  0 0 0 0
        // The two four-bit sequences in the result correspond to the hex digits
        // 2 and 0, respectively, so the result, in hex, is 0x20.
        
        byte high_bit = (byte) (b & high_bit_mask);
        System.out.println("high bit:         " + byteToHexString(high_bit));
        // Now why do we get 0x00?
        //   Original byte (0x6B):     0 1 1 0  1 0 1 1
        //   High-bit mask  (0x20):    1 0 0 0  0 0 0 0
        //                             ================
        //   Bitwise AND         :     0 0 0 0  0 0 0 0
        // Here both four-bit sequences in the result are just 0, hence 0x00.
        
        byte mid_bits = (byte) (b & mid_range_mask);
        System.out.println("middle 4 bits:    " + byteToHexString(mid_bits));
        // Whoa, why do we get 0x28?
        //   Original byte (0x6B):     0 1 1 0  1 0 1 1
        //   Mid-bits mask (0x3C):     0 0 1 1  1 1 0 0
        //                             ================
        //   Bitwise AND         :     0 0 1 0  1 0 0 0
        // The two four-bit sequences in the result correspond to the hex digits
        // 2 and 8, respectively.
        
        // Let's move on to the bitwise OR operator, |.
        // It does exactly what you'd expect:
        byte mid_bits_OR = (byte) (b | mid_range_mask);
        System.out.println("OR-ed with 0x3C:  " + byteToHexString(mid_bits_OR));
        // Our result here is 0x7F.  Again, let's examine why:
        //   Original byte (0x6B):     0 1 1 0  1 0 1 1
        //   Mid-bits mask (0x3C):     0 0 1 1  1 1 0 0
        //                             ================
        //   Bitwise OR         :      0 1 1 1  1 1 1 1
        
        // You can XOR too, with ^:
        byte mid_bits_XOR = (byte) (b ^ mid_range_mask);
        System.out.println("XOR-ed with 0x3C: " + byteToHexString(mid_bits_XOR));
        // Our result here is 0x57.  Why?
        //   Original byte (0x6B):     0 1 1 0  1 0 1 1
        //   Mid-bits mask (0x3C):     0 0 1 1  1 1 0 0
        //                             ================
        //   Bitwise XOR        :      0 1 0 1  0 1 1 1
        
        // You can compare bytes using == and the comparison will work as you
        // expect (since the "byte" type is a primitive type in Java):
        System.out.print("Is that result 0x57? ");
        boolean yn = (mid_bits_XOR == ((byte) 0x57));
        System.out.println(yn);
        
        // Most commonly, though, you just want to ask whether any bits are "on"
        // in the result of some operation.  Sadly, Java still forces you to
        // explicitly cast the result of the operation, and to test it against
        // zero.  Here I use a loop to look at a bunch of bytes in sequence,
        // testing to see if bit 1 is on or not in each of them.
        byte[] test_bytes = { (byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03,
                              (byte)0x04, (byte)0x05, (byte)0x06, (byte)0x07 };
        for(int i = 0; i < 8; i++) {
            byte test = test_bytes[i];
            if((byte)(test & ((byte) 0x02)) != 0) {
                System.out.println(byteToHexString(test) + " has bit 1 ON.");
            } else {
                System.out.println(byteToHexString(test) + " has bit 1 OFF.");
            }
        }
        
        // Note that the ONLY time that I EVER converted anything into a string
        // was to display it.  Strings are not an appropriate intermediate
        // format for computation with bits and bytes; they're only meant for
        // display.  Use bitwise operations to pull out information about
        // individual bits in a byte.
    }
    
    public static String byteToHexString(byte b) {
        // A "format string" is a technique, originally used in the language
        // BCPL in the 1960s, for specifying how to represent a non-string value
        // as a string.  This way we can mix together integers, floats, and
        // bools in a single human-readable string for output to the screen or a
        // file.
        //   A format string can contain "format specifiers", special escape
        // sequences that begin with the % sign and indicate a place where a
        // value should be inserted in a particular format.
        //   Here we use the format specifier "%02X".  Working backwards:
        //    - The X means "integer value, displayed in hexadecimal, with
        //      capitalized digits".
        //    - The 2 means we want it to take up at least two character
        //      positions in the resulting string.  We use this because a byte,
        //      in hexadecimal, is at most 2 hex digits long.
        //    - The 0 means that we want to pad any extra space at the beginning
        //      of the representation of the number with 0s, rather than blanks.
        return String.format("0x%02X", b);
    }
}
