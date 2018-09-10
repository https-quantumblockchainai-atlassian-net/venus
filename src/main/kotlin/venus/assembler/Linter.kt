package venus.assembler

@JsName("LintError") data class LintError(val lineNumber: Int, val message: String, val isError: Boolean = true)

/**
 * Linter for RISC-V code
 *
 * At the moment this implemented by just running the assembler outright, unless the text is too long.
 * A more intelligent approach would run the linter on each line incrementally.
 */
@JsName("Linter") object Linter {
    /**
     * Lints the given text, which is expected to be an entire assembly file
     *
     * @param text the text to lint
     * @fixme this relies on Kotlin using JS array for Array, but it will probably remain that way
     */
    @JsName("lint") fun lint(text: String): Array<LintError> {
        val (_, errors, warnings) = Assembler.assemble(text)
        val linterErrors = ArrayList<LintError>()
        for (error in errors) {
            linterErrors.add(LintError(error.line ?: -1, error.message ?: ""))
        }
        for (warning in warnings) {
            linterErrors.add(LintError(warning.line ?: -1, warning.message ?: "", false))
        }
        return linterErrors.toTypedArray()
    }
}
