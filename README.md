SparseBitSet
============

### TL;DR
Basically, if you need to set a large number of bits, or bits at extremely high offsets, you probably want to use this 
Sparse BitSet.  All other alternatives are essentialy off the table; the Java BitSet class is a non-starter.

### Preface
You know how the internets are; a link that exists today is gone tomorrow.  A while ago I had a need for an efficient
Sparse BitSet in Java, and found a presentation and code by Dr. Bruce K. Haddon.  Going back later, I found the links
I had used to find it were dead.  Some internet sleuthing later, I found and contacted Dr. Haddon and he was kind enough
to send me the presentation again.  I have created this project to capture the code for others, as well as the
presentation.  I can take credit for neither.

### The Problem and Alternatives
The standard Java BitSet is terribly memory inefficient.  To store a single bit using BitSet at bit 2<sup>32</sup>-1 takes
2<sup>27</sup> 32-bit words (2<sup>26</sup> 64bit “words”), not counting any Java object overhead.

Using a HashSet of Integers results in (for each bit), 7 32-bit words overhead, or for 64 bits ~448 32-bit words overhead.

Using a HashMap, where the key = bitvalue / 64, and the value is a Long of packed bits, results in (for 64 bits)
~8 32-bit words overhead.

Using a custom hash table, where the key is an int = bitvalue / 64, and the value is a packed long, results in (for 64 bits)
~4 32-bit words overhead.

### The Solution: SparseBitSet
Using a virtual-memory like structure, the SparseBitSet overhead is ~0.03 32-bit words overhead per 64 bits.

For a full analysis, read Dr. Haddon's [slide stack](https://github.com/brettwooldridge/SparseBitSet/blob/master/SparseBitSet.pdf).
