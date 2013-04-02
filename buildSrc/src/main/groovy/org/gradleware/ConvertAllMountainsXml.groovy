package org.gradleware

import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.api.execution.*
import org.apache.commons.io.*

class ConvertAllMountainsXml extends DefaultTask {
	@InputDirectory
	File inputDir

	@Input
	String separator

	@OutputDirectory
	File outputDir

	ConvertAllMountainsXml(){
		description = "Converts all mountains files"
		group = "Mountain"
	}

	@TaskAction
	public void generate(TaskInputChanges inputs) {
		inputs.outOfDate({ change ->
			logger.lifecycle ("$change.file is out of date")
			inputDir.eachFile { file-> processFile(change.file) }
		} as Action)
	}

	def processFile(file){
		def mountains = new XmlSlurper().parse(file)
		mountains.mountain.each { mountain ->
			def mountainFile = new File(outputDir, mountain.name.text() + ".txt")
			mountainFile.text = "feet$separator${mountain.height.text()}"
		}
	}
}