package venus.vfs

interface VFSObject {
    val type: VFSType
    var label: String
    var contents: HashMap<String, Any>
    var permissions: VFSPermissions
    var parent: VFSObject
    var mountedHandler: VFSMountedDriveHandler?
    var mountedChildrenHelper: MutableList<VFSObject>

    companion object {
        fun isValidName(name: String): Boolean {
            return !name.contains(Regex("[$separator:\"><]"))
        }
        const val separator = "/"
    }

    fun isMounted(): Boolean {
        return mountedHandler != null
    }

    fun getPath(html_color_code: Boolean = false): String {
        var path = ""
        var node: VFSObject? = this
        while (node != null && node.parent != node && node.parent !is VFSDummy) {
            val nodelabel = if (html_color_code && node.type == VFSType.Drive) {
                var styleclass = "terminal-drive"
                if (node.isMounted()) {
                    styleclass = if (node.mountedHandler!!.validate_connection() == "") {
                        "terminal-drive-mounted-connected"
                    } else {
                        "terminal-drive-mounted-disconnected"
                    }
                }
                "<font class='" + styleclass + "'>" + node.label + "</font>"
            } else {
                node.label
            }
            path = separator + nodelabel + path
            node = node.parent
        }
        return (path)
    }

    fun getMountedPath(): String {
        var path = ""
        var node: VFSObject? = this
        while (node != null && node !is VFSDrive && node.parent != node && node.parent !is VFSDummy && node.isMounted()) {
            path = separator + node.label + path
            node = node.parent
        }
        return (path)
    }

    fun addChild(child: VFSObject, data: String? = null): Boolean {
        if (this.containsChild(child.label) || !isValidName(child.label)) {
            return false
        } else {
            if (this.isMounted()) {
                child.mountedHandler = this.mountedHandler
                if (child.type == VFSType.Folder) {
                    val r = this.mountedHandler!!.CMDmkdir(child.getMountedPath())
                    return if (r == "") {
                        true
                    } else {
                        val emsg = "Failed to add folder: $r"
                        console.error(emsg)
                        false
                    }
                } else {
                    var r = this.mountedHandler!!.CMDtouch(child.getMountedPath())
                    return if (r == "") {
                        if (child.type == VFSType.File) {
                            r = this.mountedHandler!!.CMDfilewrite(child.getMountedPath(), data = data ?: "")
                            if (r == "") {
                                true
                            } else {
                                val emsg = "Failed to write file: $r"
                                console.error(emsg)
                                false
                            }
                        }
                        true
                    } else {
                        val emsg = "Failed to add file: $r"
                        console.error(emsg)
                        false
                    }
                }
            } else {
                if (data != null) {
                    (child as VFSFile).setText(data)
                }
                this.contents.put(child.label, child)
                return true
            }
        }
    }

    fun removeChild(name: String): Boolean {
        if (!this.containsChild(name) || name == "" || name == "") {
            return false
        }
        if (this.isMounted()) {
            val r = this.mountedHandler!!.CMDrm((this.getChild(name) as VFSObject).getMountedPath())
            return if (r == "") {
                true
            } else {
                val emsg = "Failed to add file: $r"
                console.error(emsg)
                false
            }
        } else {
            contents.remove(name)
            return true
        }
    }

    fun childrenNames(full_load: Boolean = false): MutableSet<String> {
        /**
         * Make sure to call .childrenNamesFinish() after you do this to reset the mount data otherwise there will be
         * stale data.
         */
        return if (this.isMounted()) {
            this@VFSObject.mountedChildrenHelper.clear()
            val set = this.mountedHandler!!.CMDls(this.getMountedPath())?.run {
                val mutableset = mutableSetOf<String>()
                for (l in this) {
                    if ((l as Any) is String) {
                        mutableset.add(l as String)
                        continue
                    }
                    val name = l[0]
                    val type = l[1]
                    mutableset.add(name)

                    this@VFSObject.mountedChildrenHelper.add(if (type == "file") {
                        VFSFile(name, parent = this@VFSObject, mountedHandler = this@VFSObject.mountedHandler)
                    } else {
                        VFSFolder(name, parent = this@VFSObject, mountedHandler = this@VFSObject.mountedHandler)
                    })
                }
                mutableset
            } ?: mutableSetOf()
            val orig = mutableSetOf<String>(".", "..")
            orig.union(set).toMutableSet()
        } else {
            this.contents.keys
        }
    }

    fun childrenNamesFinish() {
        this.mountedChildrenHelper.clear()
    }

    fun children(): MutableCollection<Any> {
        return this.contents.values // FIXME
    }

    fun getChild(name: String, force_get: Boolean = false): Any? {
        return if (this.isMounted()) {
            if (name in this.contents) {
                return this.contents[name]
            }
            if (!force_get) {
                for (obj in this.mountedChildrenHelper) {
                    if (obj.label == name) {
                        return obj
                    }
                }
            }
            val mpath = this.getMountedPath()
            val info = this.mountedHandler!!.CMDfileinfo(mpath, name)
            if (info["type"] == "file") {
                VFSFile(info["name"] ?: "UNKNOWN", parent = this, mountedHandler = this.mountedHandler)
            } else {
                VFSFolder(info["name"] ?: "UNKNOWN", parent = this, mountedHandler = this.mountedHandler)
            }
        } else {
            this.contents[name]
        }
    }

    fun containsChild(child: String): Boolean {
        val res = this.childrenNames().contains(child)
        this.childrenNamesFinish()
        return res
//        return this.contents.containsKey(child)
    }

    fun stringify(): JsonContainer {
        val me = JsonContainer()
        me.label = this.label
        me.permissions = this.permissions
        me.type = this.type.toString()
        return me
    }
}