package me.ezeh.nirct.protocol

class IrcResponse internal constructor(internal var raw: String, internal var source: String, internal var command: String, internal var args: Array<String>)