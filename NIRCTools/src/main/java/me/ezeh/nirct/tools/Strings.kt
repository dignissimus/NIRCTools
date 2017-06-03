package me.ezeh.nirct.tools

class Strings {
    companion object tools {
        fun countChar(str: String, sub: Char): Int {
            val subs = sub.toString()
            val i = str.toCharArray()
                    .asSequence()
                    .map { it.toString() }
                    .count { it.equals(subs, ignoreCase = true) }
            return i
        }
    }
}
