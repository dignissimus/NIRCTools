package me.ezeh.nirct.tools

class Strings {
    companion object tools {
        fun countChar(str: String, toFind: Char): Int {
            val toFindString = toFind.toString()
            val count = str.toCharArray()
                    .asSequence()
                    .map { it.toString() }
                    .count { it.equals(toFindString, ignoreCase = true) }
            return count
        }
    }
}
