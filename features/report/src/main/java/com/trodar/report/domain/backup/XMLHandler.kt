package com.trodar.report.domain.backup

import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler


class XMLHandler(private val mElementName: String) : DefaultHandler() {
    private var mTempVal: StringBuilder? = null
    private var mItems: MutableList<Map<String, String>> = mutableListOf()
    private var mItem: MutableMap<String, String> = mutableMapOf()

    @Throws(SAXException::class)
    override fun startElement(
        uri: String, localName: String, qName: String,
        attributes: Attributes
    ) {
        mTempVal = StringBuilder()
//        if (qName.equals("document", ignoreCase = true)) {
//            mItems = ArrayList()
//        }
//        if (qName.equals(mElementName, ignoreCase = true)) {
//            mItem = HashMap()
//        }
    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray, start: Int, length: Int) {
        mTempVal!!.append(String(ch, start, length))
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, localName: String, qName: String) {
        if (qName.equals(mElementName, ignoreCase = true)) {
            mItem.let { mItems.add(it.toMap()) }
        } else {
            mItem[qName] = mTempVal.toString()
        }
    }

    val items: List<Map<String, String>>
        get() = mItems
}
