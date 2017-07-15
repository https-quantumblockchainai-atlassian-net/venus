package venus.simulator

import org.junit.Test
import kotlin.test.assertEquals
import venus.assembler.Assembler
import venus.linker.Linker

class UndoTest {
    @Test
    fun undoRegisterSet() {
        val prog = Assembler.assemble("""
        addi x8 x0 7
        addi x8 x0 9
        """)
        val linked = Linker.link(listOf(prog))
        val sim = Simulator(linked)
        sim.step()
        assertEquals(7, sim.getReg(8))
        sim.step()
        assertEquals(9, sim.getReg(8))
        sim.undo()
        assertEquals(7, sim.getReg(8))
        sim.step()
        assertEquals(9, sim.getReg(8))
    }

    @Test
    fun undoMemorySet() {
        val prog = Assembler.assemble("""
        addi x8 x0 100
        addi x9 x0 42
        sw 0(x8) x9
        """)
        val linked = Linker.link(listOf(prog))
        val sim = Simulator(linked)
        sim.step()
        sim.step()
        sim.step()
        assertEquals(42, sim.loadByte(100))
        sim.undo()
        assertEquals(0, sim.loadByte(100))
        sim.step()
        assertEquals(42, sim.loadByte(100))
    }

    @Test
    fun undoJump() {
        val prog = Assembler.assemble("""
        up: addi x8 x8 1
        j up
        """)
        val linked = Linker.link(listOf(prog))
        val sim = Simulator(linked)
        sim.step()
        assertEquals(1, sim.getReg(8))
        sim.step()
        assertEquals(1, sim.getReg(8))
        sim.undo()
        sim.undo()
        assertEquals(0, sim.getReg(8))
    }
}