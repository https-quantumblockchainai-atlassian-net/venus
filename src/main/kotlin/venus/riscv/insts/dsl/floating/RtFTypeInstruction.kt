package venus.riscv.insts.dsl.floating

import venus.riscv.insts.dsl.Instruction
import venus.riscv.insts.dsl.disasms.extensions.RFRTypeDisassembler
import venus.riscv.insts.dsl.formats.extensions.FSRS2TypeFormat
import venus.riscv.insts.dsl.impls.NoImplementation
import venus.riscv.insts.dsl.impls.extensions.RtFTypeImplementation32
import venus.riscv.insts.dsl.parsers.extensions.RFRTypeParser
import venus.riscv.insts.floating.Decimal

class RtFTypeInstruction(
    name: String,
    opcode: Int,
    funct3: Int,
    funct7: Int,
    rs2: Int,
    // eval16: (Short, Short) -> Short = { _, _ -> throw NotImplementedError("no rv16") },
    eval32: (Int) -> Decimal // ,
    // eval64: (Long, Long) -> Long = { _, _ -> throw NotImplementedError("no rv64") },
    // eval128: (Long, Long) -> Long = { _, _ -> throw NotImplementedError("no rv128") }
) : Instruction(
        name = name,
        format = FSRS2TypeFormat(opcode, funct7, rs2),
        parser = RFRTypeParser,
        impl16 = NoImplementation,
        impl32 = RtFTypeImplementation32(eval32),
        impl64 = NoImplementation,
        impl128 = NoImplementation,
        disasm = RFRTypeDisassembler
)