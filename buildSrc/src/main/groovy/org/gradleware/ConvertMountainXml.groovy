package org.gradleware

import org.gradle.api.*
import org.gradle.api.tasks.*

class ConvertMountainXml extends DefaultTask {
	@InputFile
	File xmlFile

	@Input
	String separator

	@OutputDirectory
	File outputDir

	ConvertMountainXml(){
		description = "Converts mountains files"
		group = "Mountain"
	}
	
	@TaskAction
	public void generate() {
		def mountains = new XmlSlurper().parse(xmlFile)
		mountains.mountain.each { mountain ->
			def mountainFile = new File(outputDir, mountain.name.text() + ".txt")
			mountainFile.text = "feet$separator${mountain.height.text()}"
		}
	}
}