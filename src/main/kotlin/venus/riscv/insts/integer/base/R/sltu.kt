package venus.riscv.insts.integer.base.R

import venus.riscv.insts.dsl.RTypeInstruction
import venus.riscv.insts.dsl.compareUnsigned
import venus.riscv.insts.dsl.compareUnsignedLong
import venus.riscv.insts.dsl.compareUnsignedShort

val sltu = RTypeInstruction(
        name = "sltu",
        opcode = 0b0110011,
        funct3 = 0b011,
        funct7 = 0b0000000,
        eval16 = { a, b -> if (compareUnsignedShort(a, b) < 0) 1 else 0 },
        eval32 = { a, b -> if (compareUnsigned(a, b) < 0) 1 else 0 },
        eval64 = { a, b -> if (compareUnsignedLong(a, b) < 0) 1 else 0 },
        eval128 = { a, b -> if (compareUnsignedLong(a, b) < 0) 1 else 0 }
)