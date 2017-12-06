//
//  BibleSource.java
//  GoBible
//
//	Go Bible is a Free Bible viewer application for Java mobile phones (J2ME MIDP 1.0 and MIDP 2.0).
//	Copyright © 2003-2008 Jolon Faichney.
//	Copyright © 2008-2009 CrossWire Bible Society.
//
//	This program is free software; you can redistribute it and/or
//	modify it under the terms of the GNU General Public License
//	as published by the Free Software Foundation; either version 2
//	of the License, or (at your option) any later version.
//
//	This program is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//	GNU General Public License for more details.
//
//	You should have received a copy of the GNU General Public License
//	along with this program; if not, write to the Free Software
//	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//

import java.io.*;

public abstract class BibleSource 
{
	/**
	 * Returns the entire contents of the chapter as one string.
	 * Use getChapterIndex to get the indices of each verse in the chapter string.
	 */
	abstract public char[] getChapter(int bookIndex, int chapterIndex) throws IOException;

	/**
	 * Returns all the headings of the chapter as one string.
	 * Use getChapterHeadingIndex to get the indices of each heading in the chapter string,
	 * as well as after which verse each heading goes
	 */
	abstract public char[] getChapterHeadings(int bookIndex, int chapterIndex) throws IOException;

	/**
	 * Returns the size of the current verse data in chars. This should be used
	 * instead of the length of the array returned by getChapter() because the
	 * array returned by getChapter() may be larger than the actual verse data.
	 */
	abstract public int getVerseDataSize();
	
	/**
	 * Returns an index into the specified chapter. Two integers are
	 * used for every verse in the chapter. The first integer is the
	 * offset in characters to the verse and the second integer
	 * is the offset to the end of the verse in characters.
	 */
	abstract public int[] getChapterIndex(int bookIndex, int chapterIndex) throws IOException;

	/**
	 * Returns an index for the headings of the specified chapter.
	 * ChapterHeadingInfo contains all the necessary information about a heading (but not the heading itself).
	 */
	abstract public ChapterHeadingInfo getChapterHeadingIndex(int bookIndex, int chapterIndex) throws IOException;
	
	/**
	 * Most books will start at Chapter 1 but some may have been split up
	 * so their chapters may start at larger numbers. Either way this
	 * method can be used to convert a chapter index that starts at
	 * zero to the proper chapter number.
	 */
	abstract public int getStartChapter(int bookIndex);
	
	abstract public String[] getBookNames();
	
	abstract public String getBookName(int bookIndex);
	
	abstract public int getNumberOfBooks();
	
	abstract public int getNumberOfChapters(int bookIndex);
	
	abstract public int getNumberOfVerses(int bookIndex, int chapterIndex);
	
	public String getReferenceString(int bookIndex, int chapterIndex, int verseIndex)
	{
		GoBible goBible = GoBible.getInstance();
		return
			goBible.localizeDigits( "" + (chapterIndex + getStartChapter(bookIndex)) )
				+ ":"
				+ goBible.localizeDigits("" + (verseIndex + 1));
	}
}
