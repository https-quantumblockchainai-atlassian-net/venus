package venus

class CookieJar() {
    init {
        js("/*!\n" +
                " * JavaScript Cookie v2.2.0\n" +
                " * https://github.com/js-cookie/js-cookie\n" +
                " *\n" +
                " * Copyright 2006, 2015 Klaus Hartl & Fagner Brack\n" +
                " * Released under the MIT license\n" +
                " */\n" +
                ";(function (factory) {\n" +
                "\tvar registeredInModuleLoader = false;\n" +
                "\tif (typeof define === 'function' && define.amd) {\n" +
                "\t\tdefine(factory);\n" +
                "\t\tregisteredInModuleLoader = true;\n" +
                "\t}\n" +
                "\tif (typeof exports === 'object') {\n" +
                "\t\tmodule.exports = factory();\n" +
                "\t\tregisteredInModuleLoader = true;\n" +
                "\t}\n" +
                "\tif (!registeredInModuleLoader) {\n" +
                "\t\tvar OldCookies = window.Cookies;\n" +
                "\t\tvar api = window.Cookies = factory();\n" +
                "\t\tapi.noConflict = function () {\n" +
                "\t\t\twindow.Cookies = OldCookies;\n" +
                "\t\t\treturn api;\n" +
                "\t\t};\n" +
                "\t}\n" +
                "}(function () {\n" +
                "\tfunction extend () {\n" +
                "\t\tvar i = 0;\n" +
                "\t\tvar result = {};\n" +
                "\t\tfor (; i < arguments.length; i++) {\n" +
                "\t\t\tvar attributes = arguments[ i ];\n" +
                "\t\t\tfor (var key in attributes) {\n" +
                "\t\t\t\tresult[key] = attributes[key];\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t\treturn result;\n" +
                "\t}\n" +
                "\n" +
                "\tfunction init (converter) {\n" +
                "\t\tfunction api (key, value, attributes) {\n" +
                "\t\t\tvar result;\n" +
                "\t\t\tif (typeof document === 'undefined') {\n" +
                "\t\t\t\treturn;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t// Write\n" +
                "\n" +
                "\t\t\tif (arguments.length > 1) {\n" +
                "\t\t\t\tattributes = extend({\n" +
                "\t\t\t\t\tpath: '/'\n" +
                "\t\t\t\t}, api.defaults, attributes);\n" +
                "\n" +
                "\t\t\t\tif (typeof attributes.expires === 'number') {\n" +
                "\t\t\t\t\tvar expires = new Date();\n" +
                "\t\t\t\t\texpires.setMilliseconds(expires.getMilliseconds() + attributes.expires * 864e+5);\n" +
                "\t\t\t\t\tattributes.expires = expires;\n" +
                "\t\t\t\t}\n" +
                "\n" +
                "\t\t\t\t// We're using \"expires\" because \"max-age\" is not supported by IE\n" +
                "\t\t\t\tattributes.expires = attributes.expires ? attributes.expires.toUTCString() : '';\n" +
                "\n" +
                "\t\t\t\ttry {\n" +
                "\t\t\t\t\tresult = JSON.stringify(value);\n" +
                "\t\t\t\t\tif (/^[\\{\\[]/.test(result)) {\n" +
                "\t\t\t\t\t\tvalue = result;\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t} catch (e) {}\n" +
                "\n" +
                "\t\t\t\tif (!converter.write) {\n" +
                "\t\t\t\t\tvalue = encodeURIComponent(String(value))\n" +
                "\t\t\t\t\t\t.replace(/%(23|24|26|2B|3A|3C|3E|3D|2F|3F|40|5B|5D|5E|60|7B|7D|7C)/g, decodeURIComponent);\n" +
                "\t\t\t\t} else {\n" +
                "\t\t\t\t\tvalue = converter.write(value, key);\n" +
                "\t\t\t\t}\n" +
                "\n" +
                "\t\t\t\tkey = encodeURIComponent(String(key));\n" +
                "\t\t\t\tkey = key.replace(/%(23|24|26|2B|5E|60|7C)/g, decodeURIComponent);\n" +
                "\t\t\t\tkey = key.replace(/[\\(\\)]/g, escape);\n" +
                "\n" +
                "\t\t\t\tvar stringifiedAttributes = '';\n" +
                "\n" +
                "\t\t\t\tfor (var attributeName in attributes) {\n" +
                "\t\t\t\t\tif (!attributes[attributeName]) {\n" +
                "\t\t\t\t\t\tcontinue;\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t\tstringifiedAttributes += '; ' + attributeName;\n" +
                "\t\t\t\t\tif (attributes[attributeName] === true) {\n" +
                "\t\t\t\t\t\tcontinue;\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t\tstringifiedAttributes += '=' + attributes[attributeName];\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\treturn (document.cookie = key + '=' + value + stringifiedAttributes);\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t// Read\n" +
                "\n" +
                "\t\t\tif (!key) {\n" +
                "\t\t\t\tresult = {};\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t// To prevent the for loop in the first place assign an empty array\n" +
                "\t\t\t// in case there are no cookies at all. Also prevents odd result when\n" +
                "\t\t\t// calling \"get()\"\n" +
                "\t\t\tvar cookies = document.cookie ? document.cookie.split('; ') : [];\n" +
                "\t\t\tvar rdecode = /(%[0-9A-Z]{2})+/g;\n" +
                "\t\t\tvar i = 0;\n" +
                "\n" +
                "\t\t\tfor (; i < cookies.length; i++) {\n" +
                "\t\t\t\tvar parts = cookies[i].split('=');\n" +
                "\t\t\t\tvar cookie = parts.slice(1).join('=');\n" +
                "\n" +
                "\t\t\t\tif (!this.json && cookie.charAt(0) === '\"') {\n" +
                "\t\t\t\t\tcookie = cookie.slice(1, -1);\n" +
                "\t\t\t\t}\n" +
                "\n" +
                "\t\t\t\ttry {\n" +
                "\t\t\t\t\tvar name = parts[0].replace(rdecode, decodeURIComponent);\n" +
                "\t\t\t\t\tcookie = converter.read ?\n" +
                "\t\t\t\t\t\tconverter.read(cookie, name) : converter(cookie, name) ||\n" +
                "\t\t\t\t\t\tcookie.replace(rdecode, decodeURIComponent);\n" +
                "\n" +
                "\t\t\t\t\tif (this.json) {\n" +
                "\t\t\t\t\t\ttry {\n" +
                "\t\t\t\t\t\t\tcookie = JSON.parse(cookie);\n" +
                "\t\t\t\t\t\t} catch (e) {}\n" +
                "\t\t\t\t\t}\n" +
                "\n" +
                "\t\t\t\t\tif (key === name) {\n" +
                "\t\t\t\t\t\tresult = cookie;\n" +
                "\t\t\t\t\t\tbreak;\n" +
                "\t\t\t\t\t}\n" +
                "\n" +
                "\t\t\t\t\tif (!key) {\n" +
                "\t\t\t\t\t\tresult[name] = cookie;\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t} catch (e) {}\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\treturn result;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\tapi.set = api;\n" +
                "\t\tapi.get = function (key) {\n" +
                "\t\t\treturn api.call(api, key);\n" +
                "\t\t};\n" +
                "\t\tapi.getJSON = function () {\n" +
                "\t\t\treturn api.apply({\n" +
                "\t\t\t\tjson: true\n" +
                "\t\t\t}, [].slice.call(arguments));\n" +
                "\t\t};\n" +
                "\t\tapi.defaults = {};\n" +
                "\n" +
                "\t\tapi.remove = function (key, attributes) {\n" +
                "\t\t\tapi(key, '', extend(attributes, {\n" +
                "\t\t\t\texpires: -1\n" +
                "\t\t\t}));\n" +
                "\t\t};\n" +
                "\n" +
                "\t\tapi.withConverter = init;\n" +
                "\n" +
                "\t\treturn api;\n" +
                "\t}\n" +
                "\n" +
                "\treturn init(function () {});\n" +
                "}));")
    }
}

external class Cookies {
    companion object {
        fun set(key: String, value: String): String
        fun get(key: String): String
        fun remove(key: String)
    }
}