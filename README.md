# GoBibleCore
Java ME Source Code for Go Bible Core

# History
Jolon Faichney originally developed **Go Bible** as a free Bible Text viewer for Java ME enabled feature phones.

In 2008, the **CrossWire Bible Society** formally adopted the Go Bible project by agreement with Jolon.

For further details see https://crosswire.org/wiki/Projects:Go_Bible

Jolon's original source code was hosted by Google.

The program was developed further be several volunteer programmers in succession, ending with Daniel Sim.

During most of that time, the source code was hosted by **CrossWire** using SVN.

Until 1st March 2014, this was the most up to date source code version for **Go Bible** and **Go Bible Creator**.

See https://crosswire.org/svn/gobible/

However, Daniel's **SymScroll** branch of **Go Bible Creator** was developed on GitHub rather than on CrossWire.

See https://github.com/xkjyeah/gobible-creator

**GoBibleCore** was not included in Daniel Sim's repository, hence the putative need for this archive.

### UPDATE: 2017-11-17

This repository was started as a copy of Jolon's legacy code from **March 2008**. 

It is now been updated with the latest code as hosted by CrossWire within the SVN repository.

David Haslam

Go Bible project leader, CrossWire Bible Society


----------

NB. Go Bible is no longer being actively developed by CrossWire.

This is due to the popularity of smart-phones having largely overtaken feature phones in many countries.

Even so, there are parts of the world where even Android phones are too expensive for many people, so there's still a good opportunity for Go Bible applications to bring God's word to them. 


## Introduction

GoBibleCore will create the core of an app for MIDP 2.0 cmpatible phones. It does not the Bible information per se, which is what you have to add using [GoBibleCreator](https://github.com/xkjyeah/gobible-creator).
GoBibleCreator will take GoBibleCore's output jar file and add the selected Bible books to that file. To do this both of them follow a specific format which is explained below.
The latest update includes section headings whih were not present before.

## Data format

In order for the GoBibleCore to correctly interpret the information, a strict format is used. GoBibleCreator will add the following entries to the core jar file created by GoBibleCore.

> The names used here are merely illustrative, they aren't actually used. The entries include only the actual values 

#### Book Index 
(entry named "Bible Data/<short_book_name>/Index") 

* *For each chapter*:

	- **heading_count**: unsigned short (number of headings in chapter)
	- *For each verse*:
	
		+ **length**: int (number of characters)
	- *For each heading*:
	
		+ **after_verse**: unsigned short (number of the verse after which it should be displayed. If it is at the start of a chapter this number should be 0)
		+ **length**: unsigned short (character count of the heading)
			

#### Book Data:
This contains all the chapters of the book concatenated. It could possibly come separated in multiple files because of size limitations on certain phones.

Entry named: "Bible Data/<short_book_name>/<short_book_name><file_number>"

* **byte_count**: int (number of bytes in *text*)
* **text**: [byte] (Bytes for all verses of this book, possibly separated in several files. Will then be converted to UTF-8)
	
#### Collection Index:
Entry named: "Bible Data/Index"

* **number_of_books**: int
* *For each book:*
	- **name**: String
	- **short_name**: String (Used as identifier in other entry names. Must be 7-bit ASCII)
	- **start_chapter**: unsigned short (The chapter number that the book starts with, usually 1 but may be a larger chapter if the book has been split)
	- **number_of_chapters**: unsigned short
	- *For each chapter:*
		+ **filenumber**: int (In which book data entry file it is as a book can be separated in multiple files)
		+ **all_verses_length**: int (total byte count)
		+ **verse_count**: int (how many verses)
			
#### Heading Data
(Entry named: "Bible Data/<short_book_name>/Headings")

It contains all the headings of the book concatenated.

	byte_count: int (How many bytes are in *text*)
	text: [byte] (All the headings in the book. Will be converted to UTF-8)		
			
			
### Section headings

Section headings support has been added recently (2017) by Mathias Claassen (@mats-claassen). Feel free to contact with any questions.

If you have previously used GoBibleCreator without section headings and want to support them you should use the latest version which includes support for USFM files.

The data format changes to support this were to add the "*Heading Data*" entry and modify the Book Index to include the heading information.

Currently the font size used for the section headings depends on the body size:

| Body size | Heading size |
| --- | --- |
| small | medium | 
| medium | large |
| large | large |
