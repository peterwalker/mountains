package org.gradleware

import org.gradle.api.*
import org.gradle.api.tasks.*

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
	public void generate() {
		inputDir.eachFile{ file->
			def mountains = new XmlSlurper().parse(file)
			mountains.mountain.each { mountain ->
				def mountainFile = new File(outputDir, mountain.name.text() + ".txt")
				mountainFile.text = "feet$separator${mountain.height.text()}"
			}
		}
	}
}

