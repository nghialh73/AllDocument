/*
 * Copyright 2001-2005 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * This software is open source.
 * See the bottom of this file for the licence.
 */

package com.example.alldocument.library.fc.dom4j.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

import com.example.alldocument.library.fc.dom4j.Element;
import com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper;
import com.example.alldocument.library.fc.dom4j.tree.DefaultEntity;


/**
 * <p>
 * <code>DOMEntity</code> implements a Entity node which supports the W3C DOM
 * API.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.12 $
 */
public class DOMEntityReference extends DefaultEntity implements org.w3c.dom.EntityReference
{
    public DOMEntityReference(String name)
    {
        super(name);
    }

    public DOMEntityReference(String name, String text)
    {
        super(name, text);
    }

    public DOMEntityReference(Element parent, String name, String text)
    {
        super(parent, name, text);
    }

    // org.w3c.dom.Node interface
    // -------------------------------------------------------------------------
    public boolean supports(String feature, String version)
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.supports(this, feature, version);
    }

    public String getNamespaceURI()
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.getNamespaceURI(this);
    }

    public String getPrefix()
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.getPrefix(this);
    }

    public void setPrefix(String prefix) throws DOMException
    {
        com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.setPrefix(this, prefix);
    }

    public String getLocalName()
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.getLocalName(this);
    }

    public String getNodeName()
    {
        return getName();
    }

    // already part of API
    //
    // public short getNodeType();
    public String getNodeValue() throws DOMException
    {
        return null;
    }

    public void setNodeValue(String nodeValue) throws DOMException
    {
    }

    public Node getParentNode()
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.getParentNode(this);
    }

    public NodeList getChildNodes()
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.getChildNodes(this);
    }

    public Node getFirstChild()
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.getFirstChild(this);
    }

    public Node getLastChild()
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.getLastChild(this);
    }

    public Node getPreviousSibling()
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.getPreviousSibling(this);
    }

    public Node getNextSibling()
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.getNextSibling(this);
    }

    public NamedNodeMap getAttributes()
    {
        return null;
    }

    public Document getOwnerDocument()
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.getOwnerDocument(this);
    }

    public Node insertBefore(Node newChild, Node refChild)
        throws DOMException
    {
        checkNewChildNode(newChild);

        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.insertBefore(this, newChild, refChild);
    }

    public Node replaceChild(Node newChild, Node oldChild)
        throws DOMException
    {
        checkNewChildNode(newChild);

        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.replaceChild(this, newChild, oldChild);
    }

    public Node removeChild(Node oldChild) throws DOMException
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.removeChild(this, oldChild);
    }

    public Node appendChild(Node newChild) throws DOMException
    {
        checkNewChildNode(newChild);

        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.appendChild(this, newChild);
    }

    private void checkNewChildNode(Node newChild) throws DOMException
    {
        final int nodeType = newChild.getNodeType();

        if (!((nodeType == Node.ELEMENT_NODE)
            || (nodeType == Node.TEXT_NODE)
            || (nodeType == Node.COMMENT_NODE)
            || (nodeType == Node.PROCESSING_INSTRUCTION_NODE)
            || (nodeType == Node.CDATA_SECTION_NODE) || (nodeType == Node.ENTITY_REFERENCE_NODE)))
        {
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
                "Given node cannot be a child of an entity " + "reference");
        }
    }

    public boolean hasChildNodes()
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.hasChildNodes(this);
    }

    public Node cloneNode(boolean deep)
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.cloneNode(this, deep);
    }

    public void normalize()
    {
        com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.normalize(this);
    }

    public boolean isSupported(String feature, String version)
    {
        return com.example.alldocument.library.fc.dom4j.dom.DOMNodeHelper.isSupported(this, feature, version);
    }

    public boolean hasAttributes()
    {
        return DOMNodeHelper.hasAttributes(this);
    }

    @ Override
    public String getBaseURI()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @ Override
    public short compareDocumentPosition(Node other) throws DOMException
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @ Override
    public String getTextContent() throws DOMException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @ Override
    public void setTextContent(String textContent) throws DOMException
    {
        // TODO Auto-generated method stub

    }

    @ Override
    public boolean isSameNode(Node other)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @ Override
    public String lookupPrefix(String namespaceURI)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @ Override
    public boolean isDefaultNamespace(String namespaceURI)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @ Override
    public String lookupNamespaceURI(String prefix)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @ Override
    public boolean isEqualNode(Node arg)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @ Override
    public Object getFeature(String feature, String version)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @ Override
    public Object setUserData(String key, Object data, UserDataHandler handler)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @ Override
    public Object getUserData(String key)
    {
        // TODO Auto-generated method stub
        return null;
    }
}

/*
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided that the
 * following conditions are met:
 * 
 * 1. Redistributions of source code must retain copyright statements and
 * notices. Redistributions must also contain a copy of this document.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The name "DOM4J" must not be used to endorse or promote products derived
 * from this Software without prior written permission of MetaStuff, Ltd. For
 * written permission, please contact dom4j-info@metastuff.com.
 * 
 * 4. Products derived from this Software may not be called "DOM4J" nor may
 * "DOM4J" appear in their names without prior written permission of MetaStuff,
 * Ltd. DOM4J is a registered trademark of MetaStuff, Ltd.
 * 
 * 5. Due credit should be given to the DOM4J Project - http://www.dom4j.org
 * 
 * THIS SOFTWARE IS PROVIDED BY METASTUFF, LTD. AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL METASTUFF, LTD. OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * Copyright 2001-2005 (C) MetaStuff, Ltd. All Rights Reserved.
 */