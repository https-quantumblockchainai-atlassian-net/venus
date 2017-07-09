package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.assembler.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

object UTypeWriter : InstructionWriter() {
    const val MAX_U_VALUE = 524287
    const val MIN_U_VALUE = -524288

    override operator fun invoke(prog: Program, inst: Instruction, args: List<String>) {
        checkArgsLength(args, 2)

        val rd = regNameToNumber(args[0])

        val imm = getImmediate(args[1], MIN_U_VALUE, MAX_U_VALUE)

        inst.setField(InstructionField.IMM_31_12, imm)
        inst.setField(InstructionField.RD, rd)
        prog.add(inst)
    }
}