/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package com.example.alldocument.library.fc.hpsf;

import com.example.alldocument.library.fc.hpsf.DocumentSummaryInformation;
import com.example.alldocument.library.fc.hpsf.MutableSection;
import com.example.alldocument.library.fc.hpsf.PropertyIDMap;
import com.example.alldocument.library.fc.hpsf.PropertySet;
import com.example.alldocument.library.fc.hpsf.SpecialPropertySet;
import com.example.alldocument.library.fc.hpsf.UnexpectedPropertySetTypeException;
import com.example.alldocument.library.fc.hpsf.Util;
import com.example.alldocument.library.fc.hpsf.Variant;

import java.util.Date;


/**
 * <p>Convenience class representing a Summary Information stream in a
 * Microsoft Office document.</p>
 *
 * @author Rainer Klute <a
 *         href="mailto:klute@rainer-klute.de">&lt;klute@rainer-klute.de&gt;</a>
 * @see DocumentSummaryInformation
 */
public final class SummaryInformation extends SpecialPropertySet {

    /**
     * <p>The document name a summary information stream usually has in a POIFS
     * filesystem.</p>
     */
    public static final String DEFAULT_STREAM_NAME = "\005SummaryInformation";

    public com.example.alldocument.library.fc.hpsf.PropertyIDMap getPropertySetIDMap() {
    	return com.example.alldocument.library.fc.hpsf.PropertyIDMap.getSummaryInformationProperties();
    }


    /**
     * <p>Creates a {@link SummaryInformation} from a given {@link
     * com.example.alldocument.library.fc.hpsf.PropertySet}.</p>
     *
     * @param ps A property set which should be created from a summary
     *        information stream.
     * @throws UnexpectedPropertySetTypeException if <var>ps</var> does not
     *         contain a summary information stream.
     */
    public SummaryInformation(final com.example.alldocument.library.fc.hpsf.PropertySet ps)
            throws UnexpectedPropertySetTypeException
    {
        super(ps);
        if (!isSummaryInformation())
            throw new UnexpectedPropertySetTypeException("Not a "
                    + getClass().getName());
    }



    /**
     * <p>Returns the title (or <code>null</code>).</p>
     *
     * @return The title or <code>null</code>
     */
    public String getTitle()
    {
        return (String) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_TITLE);
    }



    /**
     * <p>Sets the title.</p>
     *
     * @param title The title to set.
     */
    public void setTitle(final String title)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_TITLE, title);
    }



    /**
     * <p>Removes the title.</p>
     */
    public void removeTitle()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_TITLE);
    }



    /**
     * <p>Returns the subject (or <code>null</code>).</p>
     *
     * @return The subject or <code>null</code>
     */
    public String getSubject()
    {
        return (String) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_SUBJECT);
    }



    /**
     * <p>Sets the subject.</p>
     *
     * @param subject The subject to set.
     */
    public void setSubject(final String subject)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_SUBJECT, subject);
    }



    /**
     * <p>Removes the subject.</p>
     */
    public void removeSubject()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_SUBJECT);
    }



    /**
     * <p>Returns the author (or <code>null</code>).</p>
     *
     * @return The author or <code>null</code>
     */
    public String getAuthor()
    {
        return (String) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_AUTHOR);
    }



    /**
     * <p>Sets the author.</p>
     *
     * @param author The author to set.
     */
    public void setAuthor(final String author)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_AUTHOR, author);
    }



    /**
     * <p>Removes the author.</p>
     */
    public void removeAuthor()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_AUTHOR);
    }



    /**
     * <p>Returns the keywords (or <code>null</code>).</p>
     *
     * @return The keywords or <code>null</code>
     */
    public String getKeywords()
    {
        return (String) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_KEYWORDS);
    }



    /**
     * <p>Sets the keywords.</p>
     *
     * @param keywords The keywords to set.
     */
    public void setKeywords(final String keywords)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_KEYWORDS, keywords);
    }



    /**
     * <p>Removes the keywords.</p>
     */
    public void removeKeywords()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_KEYWORDS);
    }



    /**
     * <p>Returns the comments (or <code>null</code>).</p>
     *
     * @return The comments or <code>null</code>
     */
    public String getComments()
    {
        return (String) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_COMMENTS);
    }



    /**
     * <p>Sets the comments.</p>
     *
     * @param comments The comments to set.
     */
    public void setComments(final String comments)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_COMMENTS, comments);
    }



    /**
     * <p>Removes the comments.</p>
     */
    public void removeComments()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_COMMENTS);
    }



    /**
     * <p>Returns the template (or <code>null</code>).</p>
     *
     * @return The template or <code>null</code>
     */
    public String getTemplate()
    {
        return (String) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_TEMPLATE);
    }



    /**
     * <p>Sets the template.</p>
     *
     * @param template The template to set.
     */
    public void setTemplate(final String template)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_TEMPLATE, template);
    }



    /**
     * <p>Removes the template.</p>
     */
    public void removeTemplate()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_TEMPLATE);
    }



    /**
     * <p>Returns the last author (or <code>null</code>).</p>
     *
     * @return The last author or <code>null</code>
     */
    public String getLastAuthor()
    {
        return (String) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_LASTAUTHOR);
    }



    /**
     * <p>Sets the last author.</p>
     *
     * @param lastAuthor The last author to set.
     */
    public void setLastAuthor(final String lastAuthor)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_LASTAUTHOR, lastAuthor);
    }



    /**
     * <p>Removes the last author.</p>
     */
    public void removeLastAuthor()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_LASTAUTHOR);
    }



    /**
     * <p>Returns the revision number (or <code>null</code>). </p>
     *
     * @return The revision number or <code>null</code>
     */
    public String getRevNumber()
    {
        return (String) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_REVNUMBER);
    }



    /**
     * <p>Sets the revision number.</p>
     *
     * @param revNumber The revision number to set.
     */
    public void setRevNumber(final String revNumber)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_REVNUMBER, revNumber);
    }



    /**
     * <p>Removes the revision number.</p>
     */
    public void removeRevNumber()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_REVNUMBER);
    }



    /**
     * <p>Returns the total time spent in editing the document (or
     * <code>0</code>).</p>
     *
     * @return The total time spent in editing the document or 0 if the {@link
     *         SummaryInformation} does not contain this information.
     */
    public long getEditTime()
    {
        final Date d = (Date) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_EDITTIME);
        if (d == null) {
            return 0;
        }
        return com.example.alldocument.library.fc.hpsf.Util.dateToFileTime(d);
    }



    /**
     * <p>Sets the total time spent in editing the document.</p>
     *
     * @param time The time to set.
     */
    public void setEditTime(final long time)
    {
        final Date d = Util.filetimeToDate(time);
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_EDITTIME, com.example.alldocument.library.fc.hpsf.Variant.VT_FILETIME, d);
    }



    /**
     * <p>Remove the total time spent in editing the document.</p>
     */
    public void removeEditTime()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_EDITTIME);
    }



    /**
     * <p>Returns the last printed time (or <code>null</code>).</p>
     *
     * @return The last printed time or <code>null</code>
     */
    public Date getLastPrinted()
    {
        return (Date) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_LASTPRINTED);
    }



    /**
     * <p>Sets the lastPrinted.</p>
     *
     * @param lastPrinted The lastPrinted to set.
     */
    public void setLastPrinted(final Date lastPrinted)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_LASTPRINTED, com.example.alldocument.library.fc.hpsf.Variant.VT_FILETIME,
                lastPrinted);
    }



    /**
     * <p>Removes the lastPrinted.</p>
     */
    public void removeLastPrinted()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_LASTPRINTED);
    }



    /**
     * <p>Returns the creation time (or <code>null</code>).</p>
     *
     * @return The creation time or <code>null</code>
     */
    public Date getCreateDateTime()
    {
        return (Date) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_CREATE_DTM);
    }



    /**
     * <p>Sets the creation time.</p>
     *
     * @param createDateTime The creation time to set.
     */
    public void setCreateDateTime(final Date createDateTime)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_CREATE_DTM, com.example.alldocument.library.fc.hpsf.Variant.VT_FILETIME,
                createDateTime);
    }



    /**
     * <p>Removes the creation time.</p>
     */
    public void removeCreateDateTime()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_CREATE_DTM);
    }



    /**
     * <p>Returns the last save time (or <code>null</code>).</p>
     *
     * @return The last save time or <code>null</code>
     */
    public Date getLastSaveDateTime()
    {
        return (Date) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_LASTSAVE_DTM);
    }



    /**
     * <p>Sets the total time spent in editing the document.</p>
     *
     * @param time The time to set.
     */
    public void setLastSaveDateTime(final Date time)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s
                .setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_LASTSAVE_DTM,
                        com.example.alldocument.library.fc.hpsf.Variant.VT_FILETIME, time);
    }



    /**
     * <p>Remove the total time spent in editing the document.</p>
     */
    public void removeLastSaveDateTime()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_LASTSAVE_DTM);
    }



    /**
     * <p>Returns the page count or 0 if the {@link SummaryInformation} does
     * not contain a page count.</p>
     *
     * @return The page count or 0 if the {@link SummaryInformation} does not
     *         contain a page count.
     */
    public int getPageCount()
    {
        return getPropertyIntValue(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_PAGECOUNT);
    }



    /**
     * <p>Sets the page count.</p>
     *
     * @param pageCount The page count to set.
     */
    public void setPageCount(final int pageCount)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_PAGECOUNT, pageCount);
    }



    /**
     * <p>Removes the page count.</p>
     */
    public void removePageCount()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_PAGECOUNT);
    }



    /**
     * <p>Returns the word count or 0 if the {@link SummaryInformation} does
     * not contain a word count.</p>
     *
     * @return The word count or <code>null</code>
     */
    public int getWordCount()
    {
        return getPropertyIntValue(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_WORDCOUNT);
    }



    /**
     * <p>Sets the word count.</p>
     *
     * @param wordCount The word count to set.
     */
    public void setWordCount(final int wordCount)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_WORDCOUNT, wordCount);
    }



    /**
     * <p>Removes the word count.</p>
     */
    public void removeWordCount()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_WORDCOUNT);
    }



    /**
     * <p>Returns the character count or 0 if the {@link SummaryInformation}
     * does not contain a char count.</p>
     *
     * @return The character count or <code>null</code>
     */
    public int getCharCount()
    {
        return getPropertyIntValue(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_CHARCOUNT);
    }



    /**
     * <p>Sets the character count.</p>
     *
     * @param charCount The character count to set.
     */
    public void setCharCount(final int charCount)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_CHARCOUNT, charCount);
    }



    /**
     * <p>Removes the character count.</p>
     */
    public void removeCharCount()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_CHARCOUNT);
    }



    /**
     * <p>Returns the thumbnail (or <code>null</code>) <strong>when this
     * method is implemented. Please note that the return type is likely to
     * change!</strong></p>
     *
     * <p><strong>Hint to developers:</strong> Drew Varner &lt;Drew.Varner
     * -at- sc.edu&gt; said that this is an image in WMF or Clipboard (BMP?)
     * format. However, we won't do any conversion into any image type but
     * instead just return a byte array.</p>
     *
     * @return The thumbnail or <code>null</code>
     */
    public byte[] getThumbnail()
    {
        return (byte[]) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_THUMBNAIL);
    }



    /**
     * <p>Sets the thumbnail.</p>
     *
     * @param thumbnail The thumbnail to set.
     */
    public void setThumbnail(final byte[] thumbnail)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_THUMBNAIL, /* FIXME: */
                Variant.VT_LPSTR, thumbnail);
    }



    /**
     * <p>Removes the thumbnail.</p>
     */
    public void removeThumbnail()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_THUMBNAIL);
    }



    /**
     * <p>Returns the application name (or <code>null</code>).</p>
     *
     * @return The application name or <code>null</code>
     */
    public String getApplicationName()
    {
        return (String) getProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_APPNAME);
    }



    /**
     * <p>Sets the application name.</p>
     *
     * @param applicationName The application name to set.
     */
    public void setApplicationName(final String applicationName)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_APPNAME, applicationName);
    }



    /**
     * <p>Removes the application name.</p>
     */
    public void removeApplicationName()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.removeProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_APPNAME);
    }



    /**
     * <p>Returns a security code which is one of the following values:</p>
     *
     * <ul>
     *
     * <li><p>0 if the {@link SummaryInformation} does not contain a
     * security field or if there is no security on the document. Use
     * {@link PropertySet#wasNull()} to distinguish between the two
     * cases!</p></li>
     *
     * <li><p>1 if the document is password protected</p></li>
     *
     * <li><p>2 if the document is read-only recommended</p></li>
     *
     * <li><p>4 if the document is read-only enforced</p></li>
     *
     * <li><p>8 if the document is locked for annotations</p></li>
     *
     * </ul>
     *
     * @return The security code or <code>null</code>
     */
    public int getSecurity()
    {
        return getPropertyIntValue(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_SECURITY);
    }



    /**
     * <p>Sets the security code.</p>
     *
     * @param security The security code to set.
     */
    public void setSecurity(final int security)
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (com.example.alldocument.library.fc.hpsf.MutableSection) getFirstSection();
        s.setProperty(com.example.alldocument.library.fc.hpsf.PropertyIDMap.PID_SECURITY, security);
    }



    /**
     * <p>Removes the security code.</p>
     */
    public void removeSecurity()
    {
        final com.example.alldocument.library.fc.hpsf.MutableSection s = (MutableSection) getFirstSection();
        s.removeProperty(PropertyIDMap.PID_SECURITY);
    }

}
