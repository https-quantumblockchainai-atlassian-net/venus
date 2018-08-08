package venus.riscv.insts.floating.double.i

import venus.riscv.insts.dsl.floating.FITypeInstruction
import venus.riscv.insts.floating.Decimal

val fld = FITypeInstruction(
        name = "fld",
        opcode = 0b0000111,
        funct3 = 0b011,
        eval32 = { addr, sim ->
            Decimal(d = Double.fromBits(sim.loadWordwCache(addr).toLong() or (sim.loadWordwCache(addr + 4).toLong() shl 32)), isF = false)
        }
)