package venus.riscv.insts.dsl.disasms.extensions

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.insts.dsl.Instruction
import venus.riscv.insts.dsl.disasms.InstructionDisassembler
import venus.riscv.insts.dsl.impls.base.constructStoreImmediate

/**
 * Created by thaum on 8/6/2018.
 */

object FSTypeDisassembler : InstructionDisassembler {
    override fun invoke(mcode: MachineCode): String {
        val name = Instruction[mcode].name
        val rs1 = mcode[InstructionField.RS1]
        val rs2 = mcode[InstructionField.RS2]
        val imm = constructStoreImmediate(mcode)
        return "$name f$rs2 $imm(x$rs1)"
    }
}