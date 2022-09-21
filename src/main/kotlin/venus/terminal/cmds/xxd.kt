package venus.terminal.cmds

import venus.Renderer
import venus.terminal.Command
import venus.terminal.Command.Companion.fileTabComplete
import venus.terminal.Terminal
import venus.vfs.VFSFile

@ExperimentalStdlibApi
var xxd = Command(
        name = "xxd",
        execute = fun(args: MutableList<String>, t: Terminal, sudo: Boolean): String {
            if (args.size < 1) {
                return Command["xxd"].help
            }
            var littleEndian = false
            var fileName = args[0]
            var result = ""
            var charchunk = 2
            val charsperline = 16

            if (args[0] == "-e") {
                littleEndian = true
                fileName = args[1]
                charchunk = 4
            }

            var f = t.vfs.getObjectFromPath(fileName) ?: return "xxd: could not find the file!"
            if (f is VFSFile) {
                val text = f.readText()
                var i = 0
                var curline = ""
                var curchunk = ""
                if (text.length > 0) {
                    result += Renderer.toHex(0, add_prefix = false) + ":"
                }
                for (chr: Char in text.toCharArray()) {
                    if (curline.length % charchunk == 0) {
                        if (littleEndian && curchunk.isNotEmpty()) curchunk = curchunk.padStart(charchunk * 2, ' ')
                        result += "$curchunk "
                        curchunk = ""
                    }
                    if (curline.length == charsperline) {
                        result += " $curline\n"
                        result += Renderer.toHex(i, add_prefix = false) + ": "
                        curline = ""
                    }
                    val cv = chr.toInt()
                    if (littleEndian) {
                        curchunk = Renderer.toHex(cv, 2, false, true) + curchunk
                    } else {
                        curchunk += Renderer.toHex(cv, 2, false, true)
                    }
                    curline += when (cv) {
                        !in 32..126 -> "."
                        else -> chr.toString()
                    }
                    i++
                }
                if (curchunk.isNotEmpty()) {
                    curchunk = if (littleEndian) curchunk.padStart(charchunk * 2, ' ')
                    else curchunk.padEnd(charchunk * 2, ' ')
                    result += "$curchunk "
                }
                if (curline.isNotEmpty()) {
                    var charsLeft = charsperline - curline.length
                    charsLeft /= charchunk
                    for (j in 0 until charsLeft) {
                        result += " " + "  ".repeat(charchunk)
                    }
                    result += " $curline"
                }
            } else {
                result = "xxd: only works on files!"
            }
            return result
        },
        tab = ::fileTabComplete,
        help = """Takes a file and prints it out in hex.
            |Usage: xxd file
            |NOTE: This is a very dumb xxd and does not have full features.
        """.trimMargin()
)
