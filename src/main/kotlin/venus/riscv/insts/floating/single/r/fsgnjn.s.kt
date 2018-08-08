package venus.riscv.insts.floating.single.r

import venus.riscv.insts.dsl.floating.F3RTypeInstruction
import venus.riscv.insts.floating.Decimal
import kotlin.math.sign
import kotlin.math.withSign

/*Single-Precision*/
val fsgnjns = F3RTypeInstruction(
        name = "fsgnjn.s",
        opcode = 0b1010011,
        funct7 = 0b0010000,
        funct3 = 0b001,
        eval32 = { a, b -> Decimal(f = a.getFloat().withSign((b.getFloat().toRawBits() xor 0x80000000.toInt()).sign)) }
)