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

package com.example.alldocument.library.fc.poifs.property;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.example.alldocument.library.fc.poifs.filesystem.BATManaged;
import com.example.alldocument.library.fc.poifs.property.DirectoryProperty;
import com.example.alldocument.library.fc.poifs.property.Property;
import com.example.alldocument.library.fc.poifs.property.RootProperty;
import com.example.alldocument.library.fc.poifs.storage.HeaderBlock;


/**
 * This class embodies the Property Table for the filesystem,
 *  which looks up entries in the filesystem to their
 *  chain of blocks.
 * This is the core support, there are implementations
 *  for the different block schemes as needed.
 */
public abstract class PropertyTableBase implements BATManaged
{
    private final HeaderBlock _header_block;
    protected final List<com.example.alldocument.library.fc.poifs.property.Property> _properties;

    public PropertyTableBase(final HeaderBlock header_block)
    {
        _header_block = header_block;
        _properties = new ArrayList<com.example.alldocument.library.fc.poifs.property.Property>();
        addProperty(new com.example.alldocument.library.fc.poifs.property.RootProperty());
    }

    /**
     * Reading constructor (used when we've read in a file and we want
     * to extract the property table from it). Populates the
     * properties thoroughly
     *
     * @param header_block the first block to read from
     * @param properties the list to populate
     *
     * @exception IOException if anything goes wrong (which should be
     *            a result of the input being NFG)
     */
    public PropertyTableBase(final HeaderBlock header_block, final List<com.example.alldocument.library.fc.poifs.property.Property> properties)
        throws IOException
    {
        _header_block = header_block;
        _properties = properties;
        populatePropertyTree((com.example.alldocument.library.fc.poifs.property.DirectoryProperty)_properties.get(0));
    }

    /**
     * Add a property to the list of properties we manage
     *
     * @param property the new Property to manage
     */
    public void addProperty(com.example.alldocument.library.fc.poifs.property.Property property)
    {
        _properties.add(property);
    }

    /**
     * Remove a property from the list of properties we manage
     *
     * @param property the Property to be removed
     */
    public void removeProperty(final com.example.alldocument.library.fc.poifs.property.Property property)
    {
        _properties.remove(property);
    }

    /**
     * Get the root property
     *
     * @return the root property
     */
    public com.example.alldocument.library.fc.poifs.property.RootProperty getRoot()
    {
        // it's always the first element in the List
        return (RootProperty)_properties.get(0);
    }

    private void populatePropertyTree(com.example.alldocument.library.fc.poifs.property.DirectoryProperty root) throws IOException
    {
        int index = root.getChildIndex();

        if (!com.example.alldocument.library.fc.poifs.property.Property.isValidIndex(index))
        {

            // property has no children
            return;
        }
        Stack<com.example.alldocument.library.fc.poifs.property.Property> children = new Stack<com.example.alldocument.library.fc.poifs.property.Property>();

        children.push(_properties.get(index));
        while (!children.empty())
        {
            com.example.alldocument.library.fc.poifs.property.Property property = children.pop();

            root.addChild(property);
            if (property.isDirectory())
            {
                populatePropertyTree((DirectoryProperty)property);
            }
            index = property.getPreviousChildIndex();
            if (com.example.alldocument.library.fc.poifs.property.Property.isValidIndex(index))
            {
                children.push(_properties.get(index));
            }
            index = property.getNextChildIndex();
            if (Property.isValidIndex(index))
            {
                children.push(_properties.get(index));
            }
        }
    }

    /**
     * Get the start block for the property table
     *
     * @return start block index
     */
    public int getStartBlock()
    {
        return _header_block.getPropertyStart();
    }

    /**
     * Set the start block for this instance
     *
     * @param index index into the array of BigBlock instances making
     *              up the the filesystem
     */
    public void setStartBlock(final int index)
    {
        _header_block.setPropertyStart(index);
    }
}
