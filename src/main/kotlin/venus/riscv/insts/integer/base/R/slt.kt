package venus.riscv.insts.integer.base.R

import venus.riscv.insts.dsl.RTypeInstruction

val slt = RTypeInstruction(
        name = "slt",
        opcode = 0b0110011,
        funct3 = 0b010,
        funct7 = 0b0000000,
        eval16 = { a, b -> if (a < b) 1 else 0 },
        eval32 = { a, b -> if (a < b) 1 else 0 },
        eval64 = { a, b -> if (a < b) 1 else 0 },
        eval128 = { a, b -> if (a < b) 1 else 0 }
)