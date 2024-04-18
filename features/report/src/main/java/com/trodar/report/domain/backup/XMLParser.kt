package com.trodar.report.domain.backup

import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.IOException
import java.io.InputStream
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParserFactory


object XMLParser {
    @Throws(
        ParserConfigurationException::class,
        SAXException::class,
        IOException::class
    )
    fun parse(elementName: String, inputStream: InputStream): List<Map<String, String>> {
        val list: List<Map<String, String>>
        val xmlReader = SAXParserFactory.newInstance().newSAXParser().xmlReader
        val saxHandler = XMLHandler(elementName)
        xmlReader.contentHandler = saxHandler
        xmlReader.parse(InputSource(inputStream))
        list = saxHandler.items
        return list
    }
}

