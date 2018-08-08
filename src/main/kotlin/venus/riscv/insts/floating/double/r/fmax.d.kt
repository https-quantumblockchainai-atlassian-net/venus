package venus.riscv.insts.floating.double.r

import venus.riscv.insts.dsl.floating.F3RTypeInstruction
import venus.riscv.insts.floating.Decimal

val fmaxd = F3RTypeInstruction(
        name = "fmax.d",
        opcode = 0b1010011,
        funct3 = 0b001,
        funct7 = 0b0010101,
        eval32 = { a, b -> Decimal(d = maxOf(a.getDouble(), b.getDouble()), isF = false) }
)